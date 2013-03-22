package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public interface Geometry {
	public Vector3D normalAt(Point3D p);

	public float intersection(Ray ray);
}
