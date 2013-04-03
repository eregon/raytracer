package raytracer;

import uclouvain.ingi2325.utils.Point3D;

public class Box {
	public final Point3D min, max;

	public Box(Point3D min, Point3D max) {
		this.min = min;
		this.max = max;
	}

	public Box(Point3D start) {
		min = start.clone();
		max = start.clone();
	}

	public void update(Point3D p) {
		if (p.x < min.x)
			min.x = p.x;
		else if (p.x > max.x)
			max.x = p.x;

		if (p.y < min.y)
			min.y = p.y;
		else if (p.y > max.y)
			max.y = p.y;

		if (p.z < min.z)
			min.z = p.z;
		else if (p.z > max.z)
			max.z = p.z;
	}
}
