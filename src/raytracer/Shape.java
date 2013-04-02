package raytracer;

import uclouvain.ingi2325.math.Matrix4;

public class Shape {
	public Geometry geometry;
	public Material material;
	/** The inverse transformation matrix */
	public Matrix4 transformation;

	public Shape(Geometry geometry, Material material, Matrix4 transformation) {
		this.geometry = geometry;
		this.material = material;
		this.transformation = transformation;

	}
}
