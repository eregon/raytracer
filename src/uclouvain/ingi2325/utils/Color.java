package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.ParseError;
import uclouvain.ingi2325.math.Tuple3;

/**
 * Represents a color by its three RGB components
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Sébastien Doeraene <sjrdoeraene@gmail.com>
 */
public class Color extends Tuple3 {

	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color WHITE = new Color(1, 1, 1);

	/**
	 * Constructs and initializes a color from the three given values.
	 * 
	 * @param r
	 *            the red component
	 * @param g
	 *            the green component
	 * @param b
	 *            the blue component
	 */
	public Color(float r, float g, float b) {
		super(r, g, b);
	}

	/**
	 * Constructs and initializes a color from the specified AWT Color object.
	 * 
	 * @param color
	 *            the color
	 */
	public Color(java.awt.Color color) {
		super(color.getRed() / 255F, color.getGreen() / 255F,
				color.getBlue() / 255F);
	}

	/**
	 * Constructs a black color
	 */
	public Color() {
		super(0, 0, 0);
	}

	public Color validate() {
		return new Color(clamp(x), clamp(y), clamp(z));
	}

	private float clamp(float f) {
		return (f > 1f) ? 1f : (f < 0f) ? 0f : f;
	}

	public int intValue() {
		return (int) (255 * x) << 16 | (int) (255 * y) << 8 | (int) (255 * z);
	}

	/**
	 * Sets the r,g,b values of this color object to those of the specified AWT
	 * Color object.
	 * 
	 * @param color
	 */
	public final void set(java.awt.Color color) {
		x = color.getRed() / 255F;
		y = color.getGreen() / 255F;
		z = color.getBlue() / 255F;
	}

	/**
	 * Returns a new AWT color object initialized with the r,g,b values of this
	 * color object.
	 * 
	 * @return the color
	 */
	public final java.awt.Color get() {
		int i = Math.round(x * 255F);
		int j = Math.round(y * 255F);
		int k = Math.round(z * 255F);
		return new java.awt.Color(i, j, k);
	}

	/**
	 * Parse a Color from a string
	 * @param string   String representation
	 * @return The Color represented by string
	 * @throws ParseError string is not a valid Color
	 * @see Tuple3#valueOf(String, Tuple3)
	 */
	public static Color valueOf(String string) throws ParseError {
		return valueOf(string, new Color());
	}

	public Color add(Color c) {
		return new Color(x + c.x, y + c.y, z + c.z);
	}

	public Color mul(Color c) {
		return new Color(x * c.x, y * c.y, z * c.z);
	}

	public Color mul(float n) {
		return new Color(x * n, y * n, z * n);
	}

	public Color div(float n) {
		return new Color(x / n, y / n, z / n);
	}
}
