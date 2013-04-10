package raytracer;

public interface Geometry extends Surface {
	public Box computeBoundingBox(Transformation transformation);
}
