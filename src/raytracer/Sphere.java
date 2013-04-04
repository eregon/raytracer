package raytracer;

import uclouvain.ingi2325.math.Matrix4;
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
	public Box computeBoundingBox(Transformation transformation) {
		// See http://stackoverflow.com/questions/4368961/calculating-an-aabb-for-a-transformed-sphere
		Matrix4 s = new Matrix4(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, -1);

		if (radius != 1.0f)
			transformation = transformation.scale(new Vector3D(radius, radius, radius));

		Matrix4 r = transformation.m.mul(s.inverse()).mul(transformation.m.transpose());
		float r03 = r.m03, r13 = r.m13, r23 = r.m23, r33 = r.m33, r00 = r.m00, r11 = r.m11, r22 = r.m22;

		return new Box(
				new Point3D(
						(r03 - (float) Math.sqrt(r03 * r03 - r33 * r00)) / r33,
						(r13 - (float) Math.sqrt(r13 * r13 - r33 * r11)) / r33,
						(r23 - (float) Math.sqrt(r23 * r23 - r33 * r22)) / r33),
				new Point3D(
						(r03 + (float) Math.sqrt(r03 * r03 - r33 * r00)) / r33,
						(r13 + (float) Math.sqrt(r13 * r13 - r33 * r11)) / r33,
						(r23 + (float) Math.sqrt(r23 * r23 - r33 * r22)) / r33));
	}

	@Override
	public Intersection intersection(Ray ray) {
		Vector3D e_c = ray.origin.toVector();
		float A = ray.direction.dotProduct(ray.direction); // d*d, strictly positive
		float B = 2 * ray.direction.dotProduct(e_c); // 2 * d * (e - c)
		float C = e_c.dotProduct(e_c) - radius * radius; // (e - c) * (e - c) - r * r

		float discriminant = B * B - 4 * A * C;

		if (discriminant >= 0) {
			float t;
			if (discriminant > 0) {
				// A > 0, so -B - sqrt(...) is always smaller
				float sqrt = (float) Math.sqrt(discriminant);
				// We want t positive, so (- B +- sqrt) positive
				if (-B >= sqrt) { // -B - sqrt >= 0
					t = (-B - sqrt) / (2 * A);
				} else if (sqrt >= B) { // -B + sqrt >= 0
					t = (-B + sqrt) / (2 * A);
				} else
					return null;
			} else {
				t = -B / (2 * A);
				if (t < 0)
					return null;
			}

			Intersection inter = new Intersection();
			inter.distance = t;

			inter.point = ray.origin.add(ray.direction.mul(inter.distance));
			inter.normal = inter.point.toVector().normalized();
			return inter;
		} else {
			return null;
		}
	}
}
