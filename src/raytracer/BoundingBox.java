package raytracer;

import uclouvain.ingi2325.utils.Point3D;

/** Axis-Aligned Bounding Box */
public class BoundingBox implements Surface {
	public final Point3D min, max;

	/** Use this constructor with include(Box) to have a box including other boxes */
	public BoundingBox() {
		min = new Point3D(Float.MAX_VALUE);
		max = new Point3D(Float.MIN_VALUE);
	}

	/** Create a Box from a min and max point, given in any order */
	public BoundingBox(Point3D a, Point3D b) {
		if (a.x <= b.x) {
			min = a;
			max = b;
		} else {
			min = b;
			max = a;
		}
	}

	/** Use this with update(Point3D) for incremental construction of the box */
	public BoundingBox(Point3D start) {
		min = start.clone();
		max = start.clone();
	}

	public static BoundingBox including(BoundingBox a, BoundingBox b) {
		return new BoundingBox(
				new Point3D(
						Math.min(a.min.x, b.min.x),
						Math.min(a.min.y, b.min.y),
						Math.min(a.min.z, b.min.z)),
				new Point3D(
						Math.max(a.max.x, b.max.x),
						Math.max(a.max.y, b.max.y),
						Math.max(a.max.z, b.max.z)));
	}

	public Point3D bound(boolean isMax) {
		return isMax ? max : min;
	}

	public Point3D center() {
		return min.middle(max);
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

	@Override
	public String toString() {
		return "[" + min.x + ", " + max.x + "] X [" + min.y + ", "
				+ max.y + "] X [" + min.z + ", " + max.z + "]";
	}

	@Override
	public Intersection intersection(Ray ray) {
		// From http://www.cs.utah.edu/~awilliam/box/box.pdf
		float tmin, tmax, tymin, tymax, tzmin, tzmax;

		tmin = (bound(ray.signx).x - ray.origin.x) * ray.direction_1.x;
		tmax = (bound(!ray.signx).x - ray.origin.x) * ray.direction_1.x;

		tymin = (bound(ray.signy).y - ray.origin.y) * ray.direction_1.y;
		tymax = (bound(!ray.signy).y - ray.origin.y) * ray.direction_1.y;

		if (tmin > tymax || tymin > tmax)
			return null;
		if (tymin > tmin)
			tmin = tymin;
		if (tymax < tmax)
			tmax = tymax;

		tzmin = (bound(ray.signz).z - ray.origin.z) * ray.direction_1.z;
		tzmax = (bound(!ray.signz).z - ray.origin.z) * ray.direction_1.z;

		if (tmin > tzmax || tzmin > tmax)
			return null;
		if (tzmin > tmin)
			tmin = tzmin;
		if (tzmax < tmax)
			tmax = tzmax;

		if (tmax <= 0)
			return null;

		Intersection inter = new Intersection();
		inter.distance = tmin > 0 ? tmin : tmax;
		return inter;
	}
}
