package raytracer;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class PointLight extends Light {

	Point3D position;

	public PointLight(Color color, Point3D position, float intensity) {
		super(color, intensity);
		this.position = position;
	}

	@Override
	public Vector3D l(Point3D hit) {
		return position.sub(hit);
		//return hit.sub(position);
	}

}