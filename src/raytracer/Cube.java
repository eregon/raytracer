package raytracer;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Cube extends Box implements Geometry {
	static enum Face {
		FRONT(Vector3D.NEAR), BACK(Vector3D.FAR),
		TOP(Vector3D.UP), BOTTOM(Vector3D.DOWN),
		LEFT(Vector3D.LEFT), RIGHT(Vector3D.RIGHT);

		Vector3D normal;

		private Face(Vector3D normal) {
			this.normal = normal;
		}
	}

	public Cube(Box box) {
		super(box.min, box.max);
	}

	@Override
	public Intersection intersection(Ray ray) {
		Intersection inter = super.intersection(ray);
		if (inter != null) {
			Point3D p = inter.point;

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

	@Override
	public Box computeBoundingBox(Transformation transformation) {
		// TODO
		return null;
	}

}
