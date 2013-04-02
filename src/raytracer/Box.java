package raytracer;

import uclouvain.ingi2325.utils.Point3D;

public class Box {
	public final Point3D min, max;

	public Box(Point3D min, Point3D max) {
		this.min = min;
		this.max = max;
	}
}
