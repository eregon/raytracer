package raytracer.geometry;

import raytracer.BoundingBox;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Transformation;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Triangle implements Geometry {
	public final Point3D _a, _b, _c;
	public final Vector3D na, nb, nc;

	public Triangle(Point3D a, Point3D b, Point3D c, Vector3D normal) {
		_a = a;
		_b = b;
		_c = c;
		na = nb = nc = normal;
	}

	public Triangle(Point3D a, Point3D b, Point3D c, Vector3D na, Vector3D nb, Vector3D nc) {
		super();
		_a = a;
		_b = b;
		_c = c;
		this.na = na;
		this.nb = nb;
		this.nc = nc;
	}

	@Override
	public BoundingBox computeBoundingBox(Transformation transformation) {
		BoundingBox box = new BoundingBox(transformation.m.mul(_a));
		box.update(transformation.m.mul(_b));
		box.update(transformation.m.mul(_c));
		return box;
	}

	@Override
	public Intersection intersection(Ray ray, float t0, float t1) {
		float a = _a.x - _b.x;
		float b = _a.y - _b.y;
		float c = _a.z - _b.z;

		float d = _a.x - _c.x;
		float e = _a.y - _c.y;
		float f = _a.z - _c.z;

		float g = ray.direction.x;
		float h = ray.direction.y;
		float i = ray.direction.z;

		float j = _a.x - ray.origin.x;
		float k = _a.y - ray.origin.y;
		float l = _a.z - ray.origin.z;

		float ei_hf = e * i - h * f;
		float gf_di = g * f - d * i;
		float dh_eg = d * h - e * g;
		float ak_jb = a * k - j * b;
		float jc_al = j * c - a * l;
		float bl_kc = b * l - k * c;

		float m = a * ei_hf + b * gf_di + c * dh_eg;
		float t = -(f * ak_jb + e * jc_al + d * bl_kc) / m;
		if (t < t0 || t > t1)
			return null;

		float gamma = (i * ak_jb + h * jc_al + g * bl_kc) / m;
		if (gamma < 0 || gamma > 1)
			return null;

		float beta = (j * ei_hf + k * gf_di + l * dh_eg) / m;
		if (beta < 0 || beta > 1 - gamma)
			return null;

		Intersection inter = new Intersection();
		inter.distance = t;
		// (1.0 - (u + v)) * na + nb * u + nc * v
		inter.normal = (na.mul(1f - (beta + gamma)).add(nb.mul(beta).add(nc.mul(gamma)))).normalized();
		return inter;
	}
}
