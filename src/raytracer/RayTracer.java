package raytracer;

import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Scene;

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
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				renderPixel(i, j);
			}
		}
	}

	private void renderPixel(int i, int j) {
		int x = j, y = i;
		if ((x / 20) % 2 == 0) {
			if ((y / 20) % 2 == 0)
				panel.drawPixel(x, y, 1, 0, 0);
			else
				panel.drawPixel(x, y, 0.5f, 0, 0);
		} else {
			if ((y / 20) % 2 == 0)
				panel.drawPixel(x, y, 1, 0.5f, 0);
			else
				panel.drawPixel(x, y, 0.5f, 0.25f, 0);
		}
		panel.repaint();
	}
}
