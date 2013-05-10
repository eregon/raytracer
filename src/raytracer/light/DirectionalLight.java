package raytracer.light;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class DirectionalLight extends Light {

	final Vector3D direction;
	final Vector3D l;

	public DirectionalLight(Vector3D direction, Color color, float intensity) {
		super(color, intensity);
		direction = direction.normalize();

		this.direction = direction;
		l = direction.opposite();
	}

	@Override
	public Vector3D l(Point3D hit) {
		return l;
	}

	@Override
	public float distanceTo(Point3D hit) {
		return Float.POSITIVE_INFINITY;
	}

}
