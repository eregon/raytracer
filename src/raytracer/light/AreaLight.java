package raytracer.light;

import java.util.concurrent.ThreadLocalRandom;

import raytracer.Intersection;
import raytracer.Material;
import raytracer.Ray;
import raytracer.RayTracer;
import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class AreaLight extends Light {

	final Point3D position;
	final Vector3D a, b;

	public AreaLight(Point3D position, Vector3D a, Vector3D b, float intensity, Color color) {
		super(color, intensity);
		this.position = position;
		this.a = a;
		this.b = b;
	}

	@Override
	public Vector3D l(Point3D hit) {
		throw new Error("Must not be called, it is varying");
	}

	@Override
	public float distanceTo(Point3D hit) {
		throw new Error("Must not be called, it is varying");
	}

	@Override
	public Color shading(Color base, RayTracer tracer, Material material, Ray shadowRay, Vector3D n, Vector3D v) {
		final int points_by_side = tracer.soft_shadows_points;
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		Point3D hit = shadowRay.origin;
		Color color = Color.NONE;

		for (int i = 0; i < points_by_side; i++) {
			for (int j = 0; j < points_by_side; j++) {
				Point3D pos = position
						.add(a.mul((random.nextFloat() + i) / points_by_side))
						.add(b.mul((random.nextFloat() + j) / points_by_side));
				Vector3D l = pos.sub(hit).normalized();
				float d = n.dotProduct(l); // diffuse factor
				if (d > 0) { // First check if light is not in opposite direction
					shadowRay.setDirection(l);
					Intersection inter = tracer.shoot(shadowRay, RayTracer.LIGHT_EPSILON, pos.distanceTo(hit));
					if (inter == null)
						color = color.add(material.colorUnderLight(this, n, l, v, d));
				}
			}
		}

		return base.add(color.div(points_by_side * points_by_side));
	}
}
