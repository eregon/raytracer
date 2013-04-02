package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Sphere implements Geometry {
	float radius;
	Point3D center;

	public Sphere(float radius) {
		super();
		this.radius = radius;
		center = new Point3D();
	}

	@Override
	public Intersection intersection(Ray ray) {
		Vector3D e_c = ray.origin.sub(center);
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
			inter.normal = inter.point.sub(center).normalized();
			return inter;
		} else {
			return null;
		}
	}
}
