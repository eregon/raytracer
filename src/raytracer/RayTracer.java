package raytracer;

import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.Vector3D;

public class RayTracer {
	Scene scene;
	PixelPanel panel;
	int height, width;

	Ray ray;

	public RayTracer(Scene scene, PixelPanel panel) {
		this.scene = scene;
		this.panel = panel;
		height = panel.getHeight();
		width = panel.getWidth();
	}

	public void render() {
		ray = new Ray(scene.camera.position);
		// Camera coordinate system induced from direction and up
		Vector3D w = scene.camera.direction.opposite();
		Vector3D v = scene.camera.up.crossProduct(w).normalize();
		Vector3D u = v.crossProduct(w);
		// projection distance
		float d = (float) (height / 2 / Math.tan(Math.PI / 180 * scene.camera.fovy / 2));

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float a = x + 0.5f - width / 2f;
				float b = y + 0.5f - height / 2f;
				ray.direction = w.mul(d).opposite().add(u.mul(a)).add(v.mul(b)); // −dW + aU + bV
				renderPixel(x, y);
			}
		}
	}

	private Shape findClosestShape() {
		float min_dist = Float.MAX_VALUE;
		Shape closest = null;
		for (Shape shape : scene.objects) {
			float dist = shape.geometry.intersection(ray);
			if (dist > 0f && dist < min_dist) {
				min_dist = dist;
				closest = shape;
			}
		}
		return closest;
	}

	private void renderPixel(int x, int y) {
		Shape closest = findClosestShape();

		if (closest != null) {
			panel.drawPixel(x, y, closest.material.color);
		} else {
			panel.drawPixel(x, y, scene.background);
		}
		panel.repaint();
	}
}
