package raytracer.light;

import raytracer.Intersection;
import raytracer.Material;
import raytracer.Ray;
import raytracer.RayTracer;
import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public abstract class Light {

	final Color color;
	final float intensity;
	/** color * intensity */
	final Color computedColor;

	public Light(Color color, float intensity) {
		color = color.validate();

		this.color = color;
		this.intensity = intensity;
		computedColor = color.mul(intensity);
	}

	public abstract Vector3D l(Point3D hit);

	public abstract float distanceTo(Point3D hit);

	public Color computedColor(Vector3D l) {
		return computedColor;
	}

	public Color shading(Color color, RayTracer tracer, Material material, Ray shadowRay, Vector3D n, Vector3D v) {
		Point3D hit = shadowRay.origin;
		Vector3D l = l(hit);
		if (l == null)
			return color;
		float d = n.dotProduct(l); // diffuse factor
		if (d > 0) { // First check if light is not in opposite direction
			shadowRay.setDirection(l);
			Intersection i = tracer.shoot(shadowRay, RayTracer.LIGHT_EPSILON, distanceTo(hit));
			if (i == null)
				color = color.add(material.colorUnderLight(this, n, l, v, d));
		}
		return color;
	}
}
