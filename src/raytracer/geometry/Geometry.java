package raytracer.geometry;

import raytracer.BoundingBox;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Surface;
import raytracer.Transformation;

public interface Geometry extends Surface {
	/** Closest intersection, must set distance and normal if found */
	@Override
	public Intersection intersection(Ray ray, float t0, float t1);

	public BoundingBox computeBoundingBox(Transformation transformation);
}
