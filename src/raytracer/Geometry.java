package raytracer;

public interface Geometry extends Surface {
	/** Closest intersection, must set distance, point and normal if found */
	@Override
	public Intersection intersection(Ray ray, float t0, float t1);

	public BoundingBox computeBoundingBox(Transformation transformation);
}
