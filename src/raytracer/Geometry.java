package raytracer;

public interface Geometry {
	public Intersection intersection(Ray ray);

	public Box boundingBox();
}
