package raytracer;

public interface Geometry extends Surface {
	/** Closest intersection, must set distance, point and normal if found */
	@Override
	public Intersection intersection(Ray ray);

	public Box computeBoundingBox(Transformation transformation);
}
