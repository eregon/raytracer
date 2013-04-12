package raytracer;

import java.lang.management.ManagementFactory;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.Vector3D;

public class RayTracer {
	public final static boolean TRACE_BOUNDING_BOXES = false;

	final Scene scene;
	final Image image;
	final int height, width;
	final BVH bvh;

	public RayTracer(Scene scene, Image image) {
		this.scene = scene;
		this.image = image;
		height = image.getHeight();
		width = image.getWidth();

		long beforeBVH = System.currentTimeMillis();
		bvh = new BVH(scene.objects);
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
						renderPixel(x, y, ray);
					}
					long t1 = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
					times[offset] = (t1 - t0) / 1e9;
				}
			};

			threads[i] = new Thread(task);
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

	private void renderPixel(int x, int y, Ray ray) {
		Intersection inter = bvh.root.intersection(ray);
		Color color;

		if (inter != null) {
			Point3D hit = inter.point;
			Vector3D n = inter.normal();
			color = Color.BLACK;

			for (Light light : scene.lights) {
				Vector3D l = light.l(hit);
				// absolute value for two-sided lighting
				float diffuse = Math.abs(n.dotProduct(l)) * light.intensity;
				color = color.add(light.color.mul(diffuse));
			}

			// TODO: multiply by a constant factor (c_l) to avoid too much white with many lights
			color = inter.shape.material.color.mul(color).normalize();
		} else {
			color = scene.background;
		}
		image.drawPixel(x, y, color);
	}

	private String formatTime(double time) {
		return String.format("%.3fs", time);
	}
}
