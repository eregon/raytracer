package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Intersection {
	public Shape shape;
	public float distance;
	public Point3D point;
	/** The normal, in object coordinates, must be normalized */
	public Vector3D normal;

	public Vector3D normal() {
		return shape.transformation.m_1t.mul(normal).normalized();
	}

	@Override
	public String toString() {
		return "Intersection at " + point + " (" + distance + ")";
	}
}
