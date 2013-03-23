package raytracer;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public abstract class Light {

	Color color;
	float intensity;

	public Light(Color color, float intensity) {
		this.color = color;
		this.intensity = intensity;
	}

	public abstract Vector3D l(Point3D hit);
}