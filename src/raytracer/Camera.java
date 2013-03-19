package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Camera {
	Point3D position;
	Vector3D direction;
	Vector3D up;
	float fovy;
	String name;

	public Camera(Point3D position, Vector3D direction, Vector3D up, float fovy, String name) {
		this.position = position;
		this.direction = direction.normalize();
		this.up = up.normalize();
		this.fovy = fovy;
		this.name = name;
	}

}
