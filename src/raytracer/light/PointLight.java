package raytracer.light;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class PointLight extends Light {

	final Point3D position;

	public PointLight(Point3D position, Color color, float intensity) {
		super(color, intensity);
		this.position = position;
	}

	@Override
	public Vector3D l(Point3D hit) {
		return position.sub(hit).normalized();
	}

	@Override
	public float distanceTo(Point3D hit) {
		return position.distanceTo(hit);
	}

}
