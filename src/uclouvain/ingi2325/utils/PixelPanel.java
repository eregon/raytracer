package uclouvain.ingi2325.utils;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
public class PixelPanel extends Canvas implements ComponentListener {

	private static final long serialVersionUID = 1L;

	/**
	 * The image displayed in this canvas
	 */
	public Image image;

	/**
	 * Construct a new CgPanel.
	 */
	public PixelPanel(int width, int height) {
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		image = new Image(width, height);
		addComponentListener(this);
		componentResized(new ComponentEvent(this,
				ComponentEvent.COMPONENT_RESIZED));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.
	 * ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("Resized");
		// TODO: Should recall RayTracer?
		// image = new Image(getWidth(), getHeight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.
	 * ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent
	 * )
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent
	 * )
	 */
	@Override
	public void componentShown(ComponentEvent e) {
	}

}
