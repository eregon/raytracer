package raytracer.light;

import java.util.Random;

import raytracer.Intersection;
import raytracer.Material;
import raytracer.Ray;
import raytracer.RayTracer;
import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class AreaLight extends Light {

	static final int POINTS_BY_SIDE = 8;
	static final int N_POINTS = POINTS_BY_SIDE * POINTS_BY_SIDE;
	static final Random randomizer = new Random();

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
		Point3D hit = shadowRay.origin;
		Color color = Color.NONE;

		for (int i = 0; i < POINTS_BY_SIDE; i++) {
			for (int j = 0; j < POINTS_BY_SIDE; j++) {
				//Point3D pos = position.add(a.mul(randomizer.nextFloat())).add(b.mul(randomizer.nextFloat()));
				Point3D pos = position
						.add(a.mul((randomizer.nextFloat() + i) / POINTS_BY_SIDE))
						.add(b.mul((randomizer.nextFloat() + j) / POINTS_BY_SIDE));
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

		return base.add(color.div(N_POINTS));
	}
}
