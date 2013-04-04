package raytracer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;

public class Box {
	public final Point3D min, max;

	public Box() {
		min = new Point3D(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
		max = new Point3D(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
	}

	/** Create a Box from a min and max point, given in any order */
	public Box(Point3D a, Point3D b) {
		if (a.x <= b.x) {
			min = a;
			max = b;
		} else {
			min = b;
			max = a;
		}
	}

	public Box(Point3D start) {
		min = start.clone();
		max = start.clone();
	}

	public void update(Point3D p) {
		if (p.x < min.x)
			min.x = p.x;
		else if (p.x > max.x)
			max.x = p.x;

		if (p.y < min.y)
			min.y = p.y;
		else if (p.y > max.y)
			max.y = p.y;

		if (p.z < min.z)
			min.z = p.z;
		else if (p.z > max.z)
			max.z = p.z;
	}

	@Override
	public String toString() {
		return "[" + min.x + ", " + max.x + "] X [" + min.y + ", "
				+ max.y + "] X [" + min.z + ", " + max.z + "]";
	}

	public List<Triangle> toTriangles() {
		List<Triangle> triangles = new ArrayList<Triangle>(6 * 2);

		// top and bottom faces
		/*for (Vector3D normal : Arrays.asList(Vector3D.UP, Vector3D.DOWN)) {
			float z = normal == Vector3D.UP ? max.z : min.z;
			addFace(triangles, normal,
					new Point3D(min.x, min.y, z), new Point3D(max.x, min.y, z),
					new Point3D(max.x, max.y, z), new Point3D(min.x, max.y, z));
		}*/

		// right and left faces
		for (Vector3D normal : Arrays.asList(Vector3D.RIGHT, Vector3D.LEFT)) {
			float y = normal == Vector3D.RIGHT ? max.y : min.y;
			addFace(triangles, normal,
					new Point3D(min.x, y, min.z), new Point3D(max.x, y, min.z),
					new Point3D(max.x, y, max.z), new Point3D(min.x, y, max.z));
		}

		// front and back faces
		for (Vector3D normal : Arrays.asList(Vector3D.NEAR, Vector3D.FAR)) {
			float x = normal == Vector3D.NEAR ? max.x : min.x;
			addFace(triangles, normal,
					new Point3D(x, min.y, min.z), new Point3D(x, max.y, min.z),
					new Point3D(x, max.y, max.z), new Point3D(x, min.y, max.z));
		}

		return triangles;
	}

	private void addFace(List<Triangle> t, Vector3D normal,
			Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
		t.add(new Triangle(p1, p2, p3, normal));
		t.add(new Triangle(p3, p4, p1, normal));
	}

	public void include(Box box) {
		update(box.min);
		update(box.max);
	}
}
