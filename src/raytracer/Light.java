package raytracer;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public abstract class Light {

	final Color color;
	final float intensity;
	/** color * intensity */
	final Color computedColor;

	public Light(Color color, float intensity) {
		this.color = color;
		this.intensity = intensity;
		computedColor = color.mul(intensity);
	}

	public abstract Vector3D l(Point3D hit);

	public abstract float distanceTo(Point3D hit);
}
