package raytracer;

import uclouvain.ingi2325.utils.Point3D;

public class Triangle implements Geometry {
	String name;
	Point3D a, b, c;

	public Triangle(String name, Point3D a, Point3D b, Point3D c) {
		this.name = name;
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public Point3D intersection(Ray ray) {
		return null;
	}
}
