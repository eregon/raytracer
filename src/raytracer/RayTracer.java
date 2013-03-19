package raytracer;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Point3D;
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
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ray.origin = new Point3D(x - width / 2, y - height / 2, -3);
				ray.direction = new Vector3D(0, 0.5f, 1);
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
