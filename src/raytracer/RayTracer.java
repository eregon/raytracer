package raytracer;

import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.Vector3D;

public class RayTracer {
	Scene scene;
	PixelPanel panel;
	int height, width;

	public RayTracer(Scene scene, PixelPanel panel) {
		this.scene = scene;
		this.panel = panel;
		height = panel.getHeight();
		width = panel.getWidth();
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

	private Pair<Shape, Float> findClosestShape(Ray ray) {
		float min_dist = Float.MAX_VALUE;
		Shape closest = null;
		for (Shape shape : scene.objects) {
			float dist = shape.geometry.intersection(ray);
			if (dist > 0f && dist < min_dist) {
				min_dist = dist;
				closest = shape;
			}
		}
		return new Pair<Shape, Float>(closest, min_dist);
	}

	private void renderPixel(int x, int y, Ray ray) {
		Pair<Shape, Float> pair = findClosestShape(ray);
		Shape closest = pair.left;
		float t = pair.right;

		if (closest != null) {
			Point3D hit = ray.origin.add(ray.direction.mul(t));
			Vector3D n = closest.geometry.normalAt(hit);
			float diffuse = 0f;
			for (Light light : scene.lights) {
				Vector3D l = light.l(hit);
				diffuse += n.dotProduct(l);
			}
			diffuse /= 10; // TODO: hack
			if (diffuse < 0f)
				diffuse = 0f;
			if (diffuse > 1f)
				diffuse = 1f;

			panel.drawPixel(x, y, closest.material.color.mul(diffuse));
		} else {
			panel.drawPixel(x, y, scene.background);
		}
		panel.repaint();
	}

}
