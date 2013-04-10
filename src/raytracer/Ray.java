package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Ray {
	Point3D origin;
	Vector3D direction;
	/** The inverse direction */
	Vector3D direction_1;

	public Ray(Point3D origin) {
		this.origin = origin;
	}

	public void setDirection(Vector3D direction) {
		this.direction = direction;
		direction_1 = direction.inverse();
	}
}
