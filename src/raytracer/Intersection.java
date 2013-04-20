package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Intersection {
	public Shape shape;
	public float distance;
	/** The normal, in object coordinates, must be normalized */
	public Vector3D normal;

	public Vector3D normal() {
		if (shape.transformation.isDefault())
			return normal;
		return shape.transformation.m_1t.mul(normal).normalized();
	}

	public Point3D point(Ray ray) {
		return ray.origin.add(ray.direction.mul(distance));
	}

	@Override
	public String toString() {
		return "Intersection at " + distance;
	}
}
