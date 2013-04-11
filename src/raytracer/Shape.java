package raytracer;

import java.util.Comparator;

public class Shape {
	public Geometry geometry;
	public Material material;
	public Transformation transformation;
	public Box boundingBox;

	public Shape(Geometry geometry, Material material, Transformation transformation) {
		this.geometry = geometry;
		this.material = material;
		this.transformation = transformation;
		boundingBox = geometry.computeBoundingBox(transformation);
	}

	public Intersection intersection(Ray worldRay) {
		Ray ray = new Ray(transformation.m_1.mul(worldRay.origin));
		ray.setDirection(transformation.m_1.mul(worldRay.direction));
		Intersection inter = geometry.intersection(ray);
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
