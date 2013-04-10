package raytracer;

public interface Surface {
	/** Closest intersection, must set distance and point if found */
	public Intersection intersection(Ray ray);
}
