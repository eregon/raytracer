package raytracer;

public class Shape {
	public Geometry geometry;
	public Material material;

	public Shape(Geometry geometry, Material material) {
		this.geometry = geometry;
		this.material = material;
	}
}
