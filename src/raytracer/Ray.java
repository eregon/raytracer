package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Ray {
	public final Point3D origin;
	public Vector3D direction;
	float direction_norm;
	/** The inverse direction */
	Vector3D direction_1;
	boolean signx, signy, signz;

	public Ray(Point3D origin) {
		this.origin = origin;
	}

	public void setDirection(Vector3D direction) {
		this.direction = direction;
		direction_norm = direction.norm();
		direction_1 = direction.inverse();
		signx = (direction_1.x < 0);
		signy = (direction_1.y < 0);
		signz = (direction_1.z < 0);
	}
}
