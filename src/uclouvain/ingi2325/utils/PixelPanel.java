package uclouvain.ingi2325.utils;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * Represents a canvas you can draw on.
 * 
 * Allows the drawing of single pixels with a given color. Typical usage in
 * render loop (assume object is called panel): panel.clear(); for each pixel to
 * be drawn: panel.drawPixel(...); panel.repaint(); panel.flush();
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class PixelPanel extends Canvas {

	/**
	 * The image displayed in this canvas
	 */
	public final Image image;

	/**
	 * Construct a new CgPanel.
	 */
	public PixelPanel(int width, int height) {
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		image = new Image(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		update(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Canvas#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}
}
