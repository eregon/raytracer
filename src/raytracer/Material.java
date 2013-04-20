package raytracer;

import uclouvain.ingi2325.utils.Color;

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
}
