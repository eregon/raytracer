package uclouvain.ingi2325.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	 */
	public void saveImage(String file) {
		//Graphics2D g2;
		//g2 = createGraphics();
		//g2.drawImage(this, null, null);
		try {
			ImageIO.write(this, "png", new File(file));
		} catch (IOException e) {
			throw new Error(e);
		}
	}
}
