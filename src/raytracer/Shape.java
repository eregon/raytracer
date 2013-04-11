package raytracer;

import java.util.Comparator;

public class Shape {
	public Geometry geometry;
	public Material material;
	public Transformation transformation;
	public BoundingBox boundingBox;

	public Shape(Geometry geometry, Material material, Transformation transformation) {
		this.geometry = geometry;
		this.material = material;
		this.transformation = transformation;
		boundingBox = geometry.computeBoundingBox(transformation);
	}

	public Intersection intersection(Ray worldRay) {
		Intersection inter;

		if (transformation.isDefault()) {
			inter = geometry.intersection(worldRay);
		} else {
			Ray ray = new Ray(transformation.m_1.mul(worldRay.origin));
			ray.setDirection(transformation.m_1.mul(worldRay.direction));
			inter = geometry.intersection(ray);
		}
		if (inter != null)
			inter.shape = this;
		return inter;
	}

	public static Comparator<Shape> comparatorForAxis(final Axis axis) {
		return new Comparator<Shape>() {
			@Override
			public int compare(Shape s1, Shape s2) {
				return Float.compare(
						s1.boundingBox.center().get(axis),
						s2.boundingBox.center().get(axis));
			}
		};
	}
}
