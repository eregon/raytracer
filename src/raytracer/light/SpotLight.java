package raytracer.light;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class SpotLight extends Light {

	final Point3D position;
	final Vector3D direction;
	final Vector3D oppositeDirection;
	/** angle from axis to exterior */
	final float angle;
	final float cos;

	public SpotLight(Point3D position, Vector3D direction, float angle, Color color, float intensity) {
		super(color, intensity);
		direction = direction.normalize();
		angle = Math.abs(angle) / 2;

		this.position = position;
		this.direction = direction;
		oppositeDirection = direction.opposite();
		this.angle = angle;
		cos = (float) Math.cos(angle);
	}

	@Override
	public Vector3D l(Point3D hit) {
		Vector3D l = position.sub(hit).normalized();
		if (l.dotProduct(oppositeDirection) >= cos)
			return l;
		else
			return null;
	}

	@Override
	public float distanceTo(Point3D hit) {
		return position.distanceTo(hit);
	}

	private float intensity(Vector3D l) {
		return (1 - ((float) Math.acos(l.dotProduct(oppositeDirection)) / angle)) * intensity;
	}

	@Override
	public Color computedColor(Vector3D l) {
		return color.mul(intensity(l));
	}
}
