package raytracer;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import raytracer.enumerator.CircleEnumerator;
import raytracer.enumerator.Enumerator;
import raytracer.enumerator.SkippingEnumerator;
import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.Scene;

public class RayTracer {
	public final static float LIGHT_EPSILON = 0.0001f;

	final Scene scene;
	final Image image;
	final int height, width;
	final boolean shadows;
	final int super_sampling;
	final BVH bvh;

	public RayTracer(Scene scene, Image image, Options options) {
		this.scene = scene;
		this.image = image;
		height = options.height;
		width = options.width;
		shadows = options.shadows;
		super_sampling = options.super_sampling;

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
		scene.camera.focus(width);

		final int n = numberOfThreads();
		ExecutorService pool = Executors.newFixedThreadPool(n);
		List<Renderer> renderers = new ArrayList<Renderer>(n);

		for (int i = 0; i < n; i++) {
			final Enumerator iter = new SkippingEnumerator(
					new CircleEnumerator(height, width), i, n);
			renderers.add(new Renderer(iter));
		}

		double cpuTime = 0;
		long t0 = System.currentTimeMillis();
		try {
			for (Future<Double> future : pool.invokeAll(renderers)) {
				try {
					cpuTime += future.get();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
		pool.shutdown();
		System.out.println("Rendered in " + formatTime((t1 - t0) / 1e3)
				+ " real " + formatTime(cpuTime) + " total CPU");

	}

	private Color shading(Ray ray) {
		Intersection inter = shoot(ray, 0, Float.POSITIVE_INFINITY);

		if (inter == null)
			return scene.background;

		if (shadows)
			return inter.shape.material.shading(this, inter, ray);
		else
			return inter.shape.material.shadingDoubleSidedNoShadows(this, inter, ray);
	}

	public Intersection shoot(Ray ray, float t0, float t1) {
		return bvh.intersection(ray, t0, t1);
	}

	private String formatTime(double time) {
		return String.format("%.3fs", time);
	}

	class Renderer implements Callable<Double> {
		final ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		final Enumerator iter;

		public Renderer(Enumerator iter) {
			this.iter = iter;
		}

		@Override
		public Double call() {
			long t0 = bean.getCurrentThreadCpuTime();
			Ray ray = new Ray(scene.camera.position);
			for (int xy : iter) {
				int x = xy % width, y = xy / width;
				Color color = render(ray, x, y);
				// y min at top, opposite of v
				image.drawPixel(x, (height - 1 - y), color);
			}
			long t1 = bean.getCurrentThreadCpuTime();
			return (t1 - t0) / 1e9;
		}

		Color render(Ray ray, int x, int y) {
			// pixel goes from [a,b] to [a+1,b+1]
			float a = x - width / 2f;
			float b = y - height / 2f;

			// start at first bottom left pixel center
			a += 1f / (2 * super_sampling);
			b += 1f / (2 * super_sampling);

			Color color = Color.NONE;
			for (int i = 0; i < super_sampling; i++) {
				for (int j = 0; j < super_sampling; j++) {
					ray.setDirection(scene.camera.toScreen(
							a + i / (float) super_sampling,
							b + j / (float) super_sampling));
					color = color.add(shading(ray));
				}
			}
			color = color.div(super_sampling * super_sampling);
			return color;
		}
	}
}
