package raytracer;

import java.lang.management.ManagementFactory;
import java.util.List;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.Vector3D;

public class RayTracer {
	public final static boolean TRACE_BOUNDING_BOXES = false;

	public final static float LIGHT_EPSILON = 0.0001f;

	final Scene scene;
	final Image image;
	final int height, width;
	final BVH bvh;

	public RayTracer(Scene scene, Image image) {
		this.scene = scene;
		this.image = image;
		height = image.getHeight();
		width = image.getWidth();

		List<Shape> shapes = scene.objects;
		scene.objects = null; // no more needed, the BVH replaces it
		long beforeBVH = System.currentTimeMillis();
		bvh = new BVH(shapes);
		long afterBVH = System.currentTimeMillis();
		System.out.println("BVH built in " + formatTime((afterBVH - beforeBVH) / 1e3));
	}

	private int numberOfThreads() {
		if (System.getenv("THREADS") != null)
			return Math.max(1, Integer.parseInt(System.getenv("THREADS")));
		else
			return Runtime.getRuntime().availableProcessors();
	}

	public void render() {
		// Camera coordinate system induced from direction and up
		final Vector3D w = scene.camera.direction.opposite();
		final Vector3D u = scene.camera.up.crossProduct(w).normalize();
		final Vector3D v = u.crossProduct(w);
		// projection distance
		final float d = (float) (width / 2 / Math.tan(Math.PI / 180 * scene.camera.fovy / 2));

		final int nThreads = numberOfThreads();
		Thread[] threads = new Thread[nThreads];
		final double[] times = new double[nThreads];

		for (int i = 0; i < nThreads; i++) {
			final int offset = i;
			final Ray ray = new Ray(scene.camera.position);
			final Enumerator iter = new SkippingEnumerator(
					new CircleEnumerator(height, width), offset, nThreads);

			Runnable task = new Runnable() {
				@Override
				public void run() {
					long t0 = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
					for (int xy : iter) {
						int x = xy % width, y = xy / width;
						float a = x + 0.5f - width / 2f;
						float b = y + 0.5f - height / 2f;
						ray.setDirection(w.mul(d).opposite().add(u.mul(a)).add(v.mul(b))); // −dW + aU + bV
						Color color = renderPixel(x, y, ray);
						image.drawPixel(x, y, color);
					}
					long t1 = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
					times[offset] = (t1 - t0) / 1e9;
				}
			};

			threads[i] = new Thread(task, "Renderer " + (i + 1));
		}

		long t0 = System.currentTimeMillis();
		for (Thread thread : threads)
			thread.start();
		try {
			for (Thread thread : threads)
				thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
		System.out.print("Rendered in " + formatTime((t1 - t0) / 1e3) + " real ");
		double cpuTime = 0;
		for (double t : times)
			cpuTime += t;
		System.out.println(formatTime(cpuTime) + " total CPU");

	}

	private Color renderPixel(int x, int y, Ray ray) {
		Intersection inter = bvh.intersection(ray, 0, Float.POSITIVE_INFINITY);

		if (inter == null)
			return scene.background;

		Point3D hit = inter.point(ray);
		Vector3D n = inter.normal();
		Vector3D maxLightVector = new Vector3D();
		Ray shadowRay = new Ray(hit);
		Material material = inter.shape.material;
		Color color = Color.BLACK;

		for (Light light : scene.lights) {
			Vector3D l = light.l(hit);
			maxLightVector = maxLightVector.add(l.mul(light.intensity));
			float d = n.dotProduct(l); // diffuse factor
			if (d > 0) { // First check if light is not in opposite direction
				shadowRay.setDirection(l);
				Intersection i = bvh.intersection(shadowRay, LIGHT_EPSILON, light.distanceTo(hit));
				if (i == null)
					color = color.add(material.addLight(light, n, l, d, ray));
			}
		}
		float c_l = maxLightVector.norm();

		return color.div(c_l).validate();
	}

	private String formatTime(double time) {
		return String.format("%.3fs", time);
	}
}
