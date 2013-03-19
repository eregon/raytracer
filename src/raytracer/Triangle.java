package raytracer;

import uclouvain.ingi2325.utils.Point3D;

public class Triangle implements Geometry {
	String name;
	Point3D _a, _b, _c;

	public Triangle(String name, Point3D a, Point3D b, Point3D c) {
		this.name = name;
		_a = a;
		_b = b;
		_c = c;
	}

	@Override
	public float intersection(Ray ray) {
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
			return -1;

		float gamma = (i * ak_jb + h * jc_al + g * bl_kc) / m;
		if (gamma < 0 || gamma > 1)
			return -1;

		float beta = (j * ei_hf + k * gf_di + l * dh_eg) / m;
		if (beta < 0 || beta > 1 - gamma)
			return -1;

		return t;
	}
}
