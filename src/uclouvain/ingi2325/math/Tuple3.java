package uclouvain.ingi2325.math;

import uclouvain.ingi2325.exception.ParseError;
import uclouvain.ingi2325.parser.Splitter;

/**
 * Represents a tuple of three float.
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Sébastien Doeraene <sjrdoeraene@gmail.com>
 */
public abstract class Tuple3 {

	/**
	 * The x coordinate.
	 */
	public float x;

	/**
	 * The y coordinate.
	 */
	public float y;

	/**
	 * The z coordinate.
	 */
	public float z;

	/**
	 * Constructs and initializes a new tuple to (0,0,0).
	 */
	public Tuple3() {
		x = 0.0F;
		y = 0.0F;
		z = 0.0F;
	}

	/**
	 * Constructs and initializes a Tuple3f from the specified xyz coordinates.
	 * 
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @param z
	 *            the z-coordinate
	 */
	public Tuple3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructs and initializes a tuple from the specified tuple.
	 * 
	 * @param tuple
	 *            a tuple representing the tuple to copy
	 */
	public Tuple3(Tuple3 tuple) {
		x = tuple.x;
		y = tuple.y;
		z = tuple.z;
	}

	/**
	 * Returns a string that contains the values of this Tuple3f
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	/**
	 * Parse a Tuple3 from a string
	 * <p>A valid example is:
	 * <pre>5.3 -4.2 2</pre>
	 * @param string   String representation
	 * @param tuple    Tuple to be filled
	 * @return The tuple
	 * @throws ParseError string does not represent a valid Tuple3
	 */
	public static <T extends Tuple3> T valueOf(String string, T tuple) throws ParseError {
		Splitter s = new Splitter(string);

		s.eatSpaces();
		tuple.x = s.getFloat();
		s.eatSpaces();
		tuple.y = s.getFloat();
		s.eatSpaces();
		tuple.z = s.getFloat();

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
			Tuple3 tuple) throws ParseError {
		throw new ParseError(String.format(
				"Cannot convert '%s' into a %s", string,
				tuple.getClass().getSimpleName()));
	}

	/**
	 * Sets the value of this tuple to the specified xyz coordinates.
	 * 
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @param z
	 *            the z-coordinate
	 */
	public final void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Sets the value of this tuple to the value of tuple t1.
	 * 
	 * @param tuple
	 *            the tuple to copy
	 */
	public final void set(Tuple3 tuple) {
		set(tuple.x, tuple.y, tuple.z);
	}

	/**
	 * Gets the value of this tuple and copies the values into t.
	 * 
	 * @param t
	 *            the tuple
	 */
	public final void get(Tuple3 t) {
		t.x = x;
		t.y = y;
		t.z = z;
	}

}
