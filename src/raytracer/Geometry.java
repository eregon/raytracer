package raytracer;

import uclouvain.ingi2325.utils.Point3D;

public interface Geometry {
	public Point3D intersection(Ray ray);
}
