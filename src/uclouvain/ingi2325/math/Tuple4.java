package uclouvain.ingi2325.math;

import uclouvain.ingi2325.exception.ParseError;
import uclouvain.ingi2325.parser.Splitter;

/**
 * Represents a tuple of four float.
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Sébastien Doeraene <sjrdoeraene@gmail.com>
 */
public abstract class Tuple4 {

	/**
	 * The x component.
	 */
	public float x;

	/**
	 * The y component.
	 */
	public float y;

	/**
	 * The z component.
	 */
	public float z;

	/**
	 * The w component.
	 */
	public float w;

	/**
	 * Constructs and initializes a new tuple to (0,0,0,0).
	 */
	public Tuple4() {
		x = 0.0F;
		y = 0.0F;
		z = 0.0F;
		w = 0.0F;
	}

	/**
	 * Constructs and initializes a Tuple4f from the specified xyzw coordinates.
	 * 
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @param z
	 *            the z-coordinate
	 * @param w
	 *            the w-coordinate
	 */
	public Tuple4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Constructs and initializes a tuple from the specified tuple.
	 * 
	 * @param tuple
	 *            the tuple to copy
	 */
	public Tuple4(Tuple4 tuple) {
		x = tuple.x;
		y = tuple.y;
		z = tuple.z;
		w = tuple.w;
	}

	/**
	 * Sets the value of this tuple to the specified x, y, z, w coordinates.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param z
	 *            the z coordinate
	 * @param w
	 *            the w coordinate
	 */
	public final void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Sets the value of this tuple to the value of tuple t1.
	 * 
	 * @param tuple
	 *            the tuple
	 */
	public final void set(Tuple4 tuple) {
		set(tuple.x, tuple.y, tuple.z, tuple.w);
	}

	/**
	 * Copies the values of this tuple into the tuple t.
	 * 
	 * @param tuple
	 *            the tuple
	 */
	public final void get(Tuple4 tuple) {
		tuple.x = x;
		tuple.y = y;
		tuple.z = z;
		tuple.w = w;
	}

	/**
	 * Returns a string that contains the values of this Tuple4f.
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}

	/**
	 * Parse a Tuple4 from a string
	 * <p>A valid example is:
	 * <pre>5.3 -4.2 2 1.0</pre>
	 * @param string   String representation
	 * @param tuple    Tuple to be filled
	 * @return The tuple
	 * @throws ParseError string does not represent a valid Tuple4
	 */
	public static <T extends Tuple4> T valueOf(String string, T tuple) throws ParseError {
		Splitter s = new Splitter(string);

		s.eatSpaces();
		tuple.x = s.getFloat();
		s.eatSpaces();
		tuple.y = s.getFloat();
		s.eatSpaces();
		tuple.z = s.getFloat();
		s.eatSpaces();
		tuple.w = s.getFloat();

		if (s.more())
			throwParseException(string, tuple);

		return tuple;
	}

	/**
	 * Throw a parse exception
	 * <p>This method never returns normally.
	 * @param string   String that should have been parsed
	 * @param tuple    Tuple that would have been filled
	 * @throws ParseError
	 */
	private static void throwParseException(String string,
			Tuple4 tuple) throws ParseError {
		throw new ParseError(String.format(
				"Cannot convert '%s' into a %s", string,
				tuple.getClass().getSimpleName()));
	}
}
