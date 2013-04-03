package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Sphere implements Geometry {
	// center is always at (0,0,0)
	float radius;

	public Sphere(float radius) {
		super();
		this.radius = radius;
	}

	@Override
	public Box boundingBox(Transformation transformation) {
		Vector3D r = new Vector3D(radius, radius, radius);
		Point3D c = transformation.m.mul(Point3D.ORIGIN);
		return new Box(c.add(r.opposite()), c.add(r));
	}

	@Override
	public Intersection intersection(Ray ray) {
		Vector3D e_c = ray.origin.toVector();
		float A = ray.direction.dotProduct(ray.direction); // d*d, strictly positive
		float B = 2 * ray.direction.dotProduct(e_c); // 2 * d * (e - c)
		float C = e_c.dotProduct(e_c) - radius * radius; // (e - c) * (e - c) - r * r

		float discriminant = B * B - 4 * A * C;

		if (discriminant >= 0) {
			Intersection inter = new Intersection();

			if (discriminant > 0)
				// A > 0, so -B - sqrt(...) is always smaller
				inter.distance = (float) (-B - Math.sqrt(discriminant)) / (2 * A);
			else
				inter.distance = -B / (2 * A);

			inter.point = ray.origin.add(ray.direction.mul(inter.distance));
			inter.normal = inter.point.toVector().normalized();
			return inter;
		} else {
			return null;
		}
	}
}
