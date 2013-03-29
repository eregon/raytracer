package raytracer;

import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.Vector3D;

public class RayTracer {
	Scene scene;
	Image image;
	int height, width;
	Runnable onPixelRendered;

	public RayTracer(Scene scene, Image image, Runnable onPixelRendered) {
		this.scene = scene;
		this.image = image;
		height = image.getHeight();
		width = image.getWidth();
		this.onPixelRendered = onPixelRendered;
	}

	public void render() {
		// Camera coordinate system induced from direction and up
		final Vector3D w = scene.camera.direction.opposite();
		final Vector3D u = scene.camera.up.crossProduct(w).normalize();
		final Vector3D v = u.crossProduct(w);
		// projection distance
		final float d = (float) (width / 2 / Math.tan(Math.PI / 180 * scene.camera.fovy / 2));

		final int nThreads = Runtime.getRuntime().availableProcessors();
		Thread[] threads = new Thread[nThreads];
		for (int i = 0; i < nThreads; i++) {
			final int offset = i;

			Runnable task = new Runnable() {
				@Override
				public void run() {
					Ray ray = new Ray(scene.camera.position);
					Enumerator iter = new RadialEnumerator(
							new CircleEnumerator(height, width), offset, nThreads, height, width);
					for (int xy : iter) {
						int x = xy % width, y = xy / width;
						float a = x + 0.5f - width / 2f;
						float b = y + 0.5f - height / 2f;
						ray.direction = w.mul(d).opposite().add(u.mul(a)).add(v.mul(b)); // −dW + aU + bV
						renderPixel(x, y, ray);
					}
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
		System.out.println("Rendered in " + String.format("%.3f", (t1 - t0) / 1e3) + "s");

	}

	private Pair<Shape, Intersection> findClosestShape(Ray ray) {
		float min_dist = Float.MAX_VALUE;
		Shape closest = null;
		Intersection inter = null;
		for (Shape shape : scene.objects) {
			Intersection i = shape.geometry.intersection(ray);
			if (i != null && i.distance > 0f && i.distance < min_dist) {
				min_dist = i.distance;
				inter = i;
				closest = shape;
			}
		}
		return new Pair<Shape, Intersection>(closest, inter);
	}

	private void renderPixel(int x, int y, Ray ray) {
		Pair<Shape, Intersection> pair = findClosestShape(ray);
		Shape closest = pair.left;
		Intersection inter = pair.right;

		if (closest != null) {
			Point3D hit = inter.point;
			Vector3D n = inter.normal;
			float diffuse = 0f;
			for (Light light : scene.lights) {
				Vector3D l = light.l(hit);
				diffuse += n.dotProduct(l) * light.intensity;
			}
			if (diffuse < 0f)
				diffuse = 0f;
			if (diffuse > 1f)
				diffuse = 1f;

			image.drawPixel(x, y, closest.material.color.mul(diffuse));
		} else {
			image.drawPixel(x, y, scene.background);
		}
		if (onPixelRendered != null)
			onPixelRendered.run();
	}

}
