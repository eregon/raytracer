package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.ParseError;
import uclouvain.ingi2325.math.Tuple3;

/**
 * Represents a vector of three float.
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Sébastien Doeraene <sjrdoeraene@gmail.com>
 */
public class Vector3D extends Tuple3 {

	public final static Vector3D
			UP = new Vector3D(0, 0, 1),
			DOWN = new Vector3D(0, 0, -1),
			RIGHT = new Vector3D(0, 1, 0),
			LEFT = new Vector3D(0, -1, 0),
			NEAR = new Vector3D(1, 0, 0),
			FAR = new Vector3D(-1, 0, 0);

	public Vector3D() {
	}

	public Vector3D(float x, float y, float z) {
		super(x, y, z);
	}

	public Vector3D(float v) {
		super(v, v, v);
	}

	/**
	 * Parse a Vector3D from a string
	 * @param string   String representation
	 * @return The Vector3D represented by string
	 * @throws ParseError string is not a valid Vector3D
	 * @see Tuple3#valueOf(String, Tuple3)
	 */
	public static Vector3D valueOf(String string) throws ParseError {
		return valueOf(string, new Vector3D());
	}

	public float norm() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3D normalized() {
		float norm = norm();
		if (norm != 1) {
			x /= norm;
			y /= norm;
			z /= norm;
		}
		return this;
	}

	public Vector3D normalize() {
		float norm = norm();
		if (norm == 1)
			return this;
		else
			return new Vector3D(x / norm, y / norm, z / norm);
	}

	public Vector3D opposite() {
		return new Vector3D(-x, -y, -z);
	}

	public Vector3D inverse() {
		return new Vector3D(1f / x, 1f / y, 1f / z);
	}

	public Vector3D crossProduct(Vector3D b) {
		return new Vector3D(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x);
	}

	public float dotProduct(Vector3D v) {
		return x * v.x + y * v.y + z * v.z;
	}

	public Vector3D mul(float n) {
		return new Vector3D(n * x, n * y, n * z);
	}

	public Vector3D add(Vector3D v) {
		return new Vector3D(x + v.x, y + v.y, z + v.z);
	}
}
