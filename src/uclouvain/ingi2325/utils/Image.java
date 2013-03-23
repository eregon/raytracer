package uclouvain.ingi2325.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Image extends BufferedImage {
	public Image(int width, int height) {
		super(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public void drawPixel(int x, int y, Color color) {
		if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
			setRGB(x, y, color.intValue());
	}

	/**
	 * Save the buffer to a file.
	 * 
	 * @param file
	 *            the filename used to save the file.
	 * @return true if image was successfully written, false otherwise
	 */
	public boolean saveImage(String file) {
		try {
			//Graphics2D g2;
			//g2 = createGraphics();
			//g2.drawImage(this, null, null);
			ImageIO.write(this, "png", new File(file));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
