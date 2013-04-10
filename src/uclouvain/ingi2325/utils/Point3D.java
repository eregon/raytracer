package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.ParseException;
import uclouvain.ingi2325.math.Tuple3;

/**
 * Represents a 3D point by its three x, y, z components
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Sébastien Doeraene <sjrdoeraene@gmail.com>
 */
public class Point3D extends Tuple3 {

	public static final Point3D ORIGIN = new Point3D(0, 0, 0);

	public Point3D() {
		super();
	}

	public Point3D(float x, float y, float z) {
		super(x, y, z);
	}

	public Point3D(float v) {
		super(v, v, v);
	}

	@Override
	public Point3D clone() {
		return new Point3D(x, y, z);
	}

	/**
	 * Parse a Point3D from a string
	 * @param string   String representation
	 * @return The Point3D represented by string
	 * @throws ParseException string is not a valid Point3D
	 * @see Tuple3#valueOf(String, Tuple3)
	 */
	public static Point3D valueOf(String string) throws ParseException {
		return valueOf(string, new Point3D());
	}

	public Vector3D toVector() {
		return new Vector3D(x, y, z);
	}

	public Point3D add(Vector3D p) {
		return new Point3D(x + p.x, y + p.y, z + p.z);
	}

	public Vector3D sub(Point3D p) {
		return new Vector3D(x - p.x, y - p.y, z - p.z);
	}
}
