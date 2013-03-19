package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Ray {
	Point3D origin;
	Vector3D direction;

	public Ray(Point3D origin) {
		this.origin = origin;
	}
}
