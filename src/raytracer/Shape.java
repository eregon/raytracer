package raytracer;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

public class Shape extends BVHNode {
	private static final Map<Axis, Comparator<Shape>> COMPARATORS = comparators();

	public final Geometry geometry;
	public final Material material;
	public final Transformation transformation;

	public Shape(Geometry geometry, Material material, Transformation transformation) {
		super(geometry.computeBoundingBox(transformation));
		this.geometry = geometry;
		this.material = material;
		this.transformation = transformation;
	}

	@Override
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

	private static Map<Axis, Comparator<Shape>> comparators() {
		Map<Axis, Comparator<Shape>> comparators =
				new EnumMap<Axis, Comparator<Shape>>(Axis.class);
		for (final Axis axis : Axis.values()) {
			comparators.put(axis, new Comparator<Shape>() {
				@Override
				public int compare(Shape s1, Shape s2) {
					return Float.compare(
							s1.boundingBox.center().get(axis),
							s2.boundingBox.center().get(axis));
				}
			});
		}
		return comparators;
	}

	public static Comparator<Shape> comparatorForAxis(Axis axis) {
		return COMPARATORS.get(axis);
	}
}
