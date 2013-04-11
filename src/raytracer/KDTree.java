package raytracer;

import java.util.Collections;
import java.util.List;

public class KDTree {
	KDNode root;

	public KDTree(List<Shape> objects) {
		root = generate(objects);
	}

	public KDNode generate(List<Shape> shapes) {
		return generate(shapes, Axis.X);
	}

	public KDNode generate(List<Shape> shapes, Axis axis) {
		if (shapes.isEmpty())
			return null;

		Collections.sort(shapes, Shape.comparatorForAxis(axis));
		int median = shapes.size() / 2;

		Axis nextAxis = axis.next();
		return new KDNode(shapes.get(median),
				generate(shapes.subList(0, median), nextAxis),
				generate(shapes.subList(median + 1, shapes.size()), nextAxis));
	}
}

class KDNode {
	Shape location;
	KDNode left, right;

	public KDNode(Shape location, KDNode left, KDNode right) {
		this.location = location;
		this.left = left;
		this.right = right;
	}

	public void print() {
		print(0);
	}

	private void print(int depth) {
		System.out.print("<KDNode loc=" + location.boundingBox.center());
		if (left == null && right == null) {
			System.out.println(">");
			return;
		} else
			System.out.println();

		indent(depth + 1);
		System.out.print("left=");
		if (left == null)
			System.out.println(left);
		else
			left.print(depth + 1);

		indent(depth + 1);
		System.out.print("right=");
		if (right == null)
			System.out.println(right);
		else
			right.print(depth + 1);
	}

	private void indent(int amount) {
		for (int i = 0; i < amount; i++)
			System.out.print("  ");
	}
}
