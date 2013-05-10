package raytracer.geometry;

import raytracer.BoundingBox;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Transformation;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Cube extends BoundingBox implements Geometry {
	static enum Face {
		FRONT(Vector3D.NEAR), BACK(Vector3D.FAR),
		TOP(Vector3D.UP), BOTTOM(Vector3D.DOWN),
		LEFT(Vector3D.LEFT), RIGHT(Vector3D.RIGHT);

		Vector3D normal;

		private Face(Vector3D normal) {
			this.normal = normal;
		}
	}

	public Cube(float size) {
		super(new Point3D(-size / 2), new Point3D(size / 2));
	}

	public Cube(BoundingBox box) {
		super(box.min, box.max);
	}

	public Point3D[] vertices() {
		return new Point3D[] {
				min,
				new Point3D(min.x, min.y, max.z),
				new Point3D(min.x, max.y, min.z),
				new Point3D(min.x, max.y, max.z),
				new Point3D(max.x, min.y, min.z),
				new Point3D(max.x, min.y, max.z),
				new Point3D(max.x, max.y, min.z),
				max
		};
	}

	@Override
	public BoundingBox computeBoundingBox(Transformation transformation) {
		BoundingBox box = new BoundingBox(transformation.m.mul(min));
		for (Point3D p : vertices())
			box.update(transformation.m.mul(p));
		return box;
	}

	@Override
	public Intersection intersection(Ray ray, float t0, float t1) {
		Intersection inter = super.intersection(ray, t0, t1);
		if (inter != null) {
			Point3D p = inter.point(ray);

			float d, best = Math.abs(p.x - max.x);
			Face face = Face.FRONT;

			if ((d = Math.abs(p.x - min.x)) < best) {
				best = d;
				face = Face.BACK;
			}
			if ((d = Math.abs(p.y - max.y)) < best) {
				best = d;
				face = Face.RIGHT;
			}
			if ((d = Math.abs(p.y - min.y)) < best) {
				best = d;
				face = Face.LEFT;
			}
			if ((d = Math.abs(p.z - max.z)) < best) {
				best = d;
				face = Face.TOP;
			}
			if ((d = Math.abs(p.z - min.z)) < best) {
				best = d;
				face = Face.BOTTOM;
			}

			inter.normal = face.normal;
		}
		return inter;
	}
}
