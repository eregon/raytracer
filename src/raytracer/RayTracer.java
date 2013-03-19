package raytracer;

import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Scene;

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
				renderPixel(x, y);
			}
		}
	}

	private void renderPixel(int x, int y) {
		panel.drawPixel(x, y, 1f - (float) (y) / height, 0, (float) (x) / width);
		panel.repaint();
	}
}
