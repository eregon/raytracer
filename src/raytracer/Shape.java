package raytracer;

import uclouvain.ingi2325.math.Matrix4;

public class Shape {
	public Geometry geometry;
	public Material material;
	/** The inverse transformation matrix */
	public Matrix4 transformation;
	public Matrix4 transformation_t;

	public Shape(Geometry geometry, Material material, Matrix4 transformation) {
		this.geometry = geometry;
		this.material = material;
		this.transformation = transformation;
		transformation_t = transformation.transpose();
	}

	public Intersection intersection(Ray worldRay) {
		Ray ray = new Ray(transformation.mul(worldRay.origin));
		ray.direction = transformation.mul(worldRay.direction);
		Intersection inter = geometry.intersection(ray);
		if (inter != null)
			inter.shape = this;
		return inter;
	}
}
