package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Triangle implements Geometry {
	Point3D _a, _b, _c;
	Vector3D na, nb, nc;

	public Triangle(Point3D[] vertices, Vector3D[] normals) {
		super();
		_a = vertices[0];
		_b = vertices[1];
		_c = vertices[2];
		// TODO: handle null normals
		na = normals[0];
		nb = normals[1];
		nc = normals[2];
	}

	@Override
	public Intersection intersection(Ray ray) {
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
		if (t <= 0) // TODO: validate t max
			return null;

		float gamma = (i * ak_jb + h * jc_al + g * bl_kc) / m;
		if (gamma < 0 || gamma > 1)
			return null;

		float beta = (j * ei_hf + k * gf_di + l * dh_eg) / m;
		if (beta < 0 || beta > 1 - gamma)
			return null;

		Intersection inter = new Intersection();
		inter.distance = t;
		//ray.origin.add(ray.direction.mul(t));
		Vector3D u = _b.sub(_a), v = _c.sub(_a);
		// _a + beta*u + gamma*v
		inter.point = _a.add(u.mul(beta)).add(v.mul(gamma));
		// (1.0 - (u + v)) * na + nb * u + nc * v
		inter.normal = (na.mul(1f - (beta + gamma)).add(nb.mul(beta).add(nc.mul(gamma)))).normalized();
		return inter;
	}
}
