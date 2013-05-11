package raytracer.geometry;

import raytracer.BoundingBox;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Transformation;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

/** A circle on the XY plane */
public class Circle implements Geometry {
	// center is always at (0,0,0)
	final float radius;

	public Circle(float radius) {
		this.radius = radius;
	}

	@Override
	public BoundingBox computeBoundingBox(Transformation transformation) {
		BoundingBox box = new BoundingBox(
				new Point3D(-radius, -radius, 0),
				new Point3D(radius, radius, 0));
		return box.transform(transformation);
	}

	@Override
	public Intersection intersection(Ray ray, float t0, float t1) {
		Point3D p = ray.origin;
		Vector3D d = ray.direction;

		if (d.z == 0f)
			return null;

		float t = -p.z / d.z;
		if (t < t0 || t > t1)
			return null;

		float x = p.x + t * d.x, y = p.y + t * d.y;

		if (x > radius || y > radius || Math.sqrt(x * x + y * y) > radius)
			return null;

		Intersection inter = new Intersection();
		inter.distance = t;
		inter.normal = (d.z < 0) ? Vector3D.UP : Vector3D.DOWN;
		return inter;
	}
}
