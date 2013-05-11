package raytracer;

import raytracer.light.Light;
import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Material {
	public final Color diffuse;
	public final Color specular;
	final float shininess;

	public Material(Color diffuse, Color specular, float shininess) {
		if (specular.equals(Color.NONE))
			specular = Color.NONE; // For identity test

		this.diffuse = diffuse;
		this.specular = specular;
		this.shininess = shininess;
	}

	public Material(Color diffuse) {
		this(diffuse, Color.NONE, 0);
	}

	public Material add(Material m) {
		return new Material(diffuse.add(m.diffuse), specular.add(m.specular), shininess + m.shininess);
	}

	public Material mul(float weight) {
		return new Material(diffuse.mul(weight), specular.mul(weight), shininess * weight);
	}

	public Color colorUnderLight(Light light, Vector3D n, Vector3D l, Vector3D v, float d) {
		Color color = diffuse.mul(d);
		if (specular != Color.NONE) {
			Vector3D h = v.add(l).normalized();
			float s = (float) Math.pow(n.dotProduct(h), shininess); // specular factor
			color = color.add(specular.mul(s));
		}
		return color.mul(light.computedColor(l));
	}

	public Color shading(RayTracer tracer, Intersection inter, Ray ray) {
		Point3D hit = inter.point(ray);
		Vector3D n = inter.normal();
		Vector3D v = ray.direction.opposite().normalized();
		Ray shadowRay = new Ray(hit);
		Color color = Color.BLACK;

		for (Light light : tracer.scene.lights)
			color = light.shading(color, tracer, this, shadowRay, n, v);

		return color.div(tracer.lightDivider).validate();
	}

	public Color shadingDoubleSidedNoShadows(RayTracer tracer, Intersection inter, Ray ray) {
		Point3D hit = inter.point(ray);
		Vector3D n = inter.normal();
		Vector3D v = ray.direction.opposite().normalized();
		Color color = Color.BLACK;

		for (Light light : tracer.scene.lights) {
			Vector3D l = light.l(hit);
			if (l == null)
				continue;
			float d = Math.abs(n.dotProduct(l)); // diffuse factor
			color = color.add(colorUnderLight(light, n, l, v, d));
		}
		return color.div(tracer.lightDivider).validate();
	}
}
