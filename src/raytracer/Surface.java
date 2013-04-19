package raytracer;

public interface Surface {
	/** Closest intersection, must set distance if found */
	public Intersection intersection(Ray ray, float t0, float t1);
}
