package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Intersection {
	public Shape shape;
	public float distance;
	public Point3D point;
	/** The normal, in object coordinates */
	public Vector3D normal;

	public Vector3D normal() {
		return shape.transformation_t.mul(normal);
	}
}
