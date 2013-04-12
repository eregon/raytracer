package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Camera {
	final Point3D position;
	final Vector3D direction;
	final Vector3D up;
	final float fovy;

	public Camera(Point3D position, Vector3D direction, Vector3D up, float fovy) {
		this.position = position;
		this.direction = direction.normalize();
		this.up = up.normalize();
		this.fovy = fovy;
	}

}
