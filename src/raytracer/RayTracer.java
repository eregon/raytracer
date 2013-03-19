package raytracer;

import uclouvain.ingi2325.utils.Color;
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

	private void renderPixel(int x, int y) {
		// panel.drawPixel(x, y, 1f - (float) (y) / height, 0, (float) (x) / width);
		Color color = Color.BLACK;

		for (Shape shape : scene.objects) {
			if (shape.geometry.intersection(ray)) {
				color = shape.material.color;
			}
		}
		panel.drawPixel(x, y, color);
		panel.repaint();
	}
}
