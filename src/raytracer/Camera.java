package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Camera {
	final Point3D position;
	final Vector3D direction;
	final Vector3D up;
	final float fovy;

	final Vector3D u, v, w;
	Vector3D toPlan;

	public Camera(Point3D position, Vector3D direction, Vector3D up, float fovy) {
		direction = direction.normalize();
		up = up.normalize();

		this.position = position;
		this.direction = direction;
		this.up = up;
		this.fovy = fovy;

		// Camera coordinate system induced from direction and up
		w = direction.opposite();
		u = direction.crossProduct(up).normalize();
		v = w.crossProduct(u);
	}

	public void focus(int width) {
		// projection distance
		float d = (float) (width / 2 / Math.tan(fovy / 2));
		toPlan = direction.mul(d);
	}

	public Vector3D toScreen(float x, float y) {
		return toPlan.add(u.mul(x)).add(v.mul(y)).normalized(); // −dW + aU + bV
	}

	public Camera rotateLeftRight(float angle) {
		return transform(Transformation.DEFAULT.rotate(up, angle));
	}

	public Camera rotateUpDown(float angle) {
		return transform(Transformation.DEFAULT.rotate(u, angle));
	}

	public Camera zoom(float zoom) {
		return transform(Transformation.DEFAULT.scale(new Vector3D(zoom)));
	}

	private Camera transform(Transformation t) {
		return new Camera(t.m.mul(position), t.m.mul(direction), up, fovy);
	}
}
