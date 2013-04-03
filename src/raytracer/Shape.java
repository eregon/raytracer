package raytracer;

public class Shape {
	public Geometry geometry;
	public Material material;
	public Transformation transformation;

	public Shape(Geometry geometry, Material material, Transformation transformation) {
		this.geometry = geometry;
		this.material = material;
		this.transformation = transformation;
	}

	public Intersection intersection(Ray worldRay) {
		Ray ray = new Ray(transformation.m_1.mul(worldRay.origin));
		ray.direction = transformation.m_1.mul(worldRay.direction);
		Intersection inter = geometry.intersection(ray);
		if (inter != null)
			inter.shape = this;
		return inter;
	}
}
