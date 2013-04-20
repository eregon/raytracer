package raytracer;

import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Material {
	public final Color diffuse;
	public final Color specular;
	final float shininess;

	public Material(Color diffuse, Color specular, float shininess) {
		this.diffuse = diffuse;
		if (specular.equals(Color.NONE))
			this.specular = Color.NONE;
		else
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
		return new Material(diffuse.mul(weight), specular.mul(weight), shininess);
	}

	public Color addLight(Light light, Vector3D n, Vector3D l, float d, Ray ray) {
		Color color = diffuse.mul(d);
		if (specular != Color.NONE) {
			Vector3D v = ray.direction.opposite().normalized();
			Vector3D h = v.add(l).normalized();
			float s = (float) Math.pow(n.dotProduct(h), shininess); // specular factor
			color = color.add(specular.mul(s));
		}
		return color.mul(light.computedColor);
	}

	public Color shading(RayTracer tracer, Intersection inter, Ray ray) {
		Point3D hit = inter.point(ray);
		Vector3D n = inter.normal();
		Color color = Color.BLACK;

		Vector3D maxLightVector = new Vector3D();
		Ray shadowRay = new Ray(hit);

		for (Light light : tracer.scene.lights) {
			Vector3D l = light.l(hit);
			maxLightVector = maxLightVector.add(l.mul(light.intensity));
			float d = n.dotProduct(l); // diffuse factor
			if (d > 0) { // First check if light is not in opposite direction
				shadowRay.setDirection(l);
				Intersection i = tracer.shoot(shadowRay, RayTracer.LIGHT_EPSILON, light.distanceTo(hit));
				if (i == null)
					color = color.add(addLight(light, n, l, d, ray));
			}
		}
		return color.div(maxLightVector.norm()).validate();
	}
}
