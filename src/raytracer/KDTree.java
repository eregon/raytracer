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
		if (shapes.size() <= 2)
			return new KDLeaf(shapes);

		Collections.sort(shapes, Shape.comparatorForAxis(axis));
		int median = shapes.size() / 2;

		Axis nextAxis = axis.next();
		float split = shapes.get(median).boundingBox.center().get(axis);
		return new KDSplitNode(axis, split,
				generate(shapes.subList(0, median), nextAxis),
				generate(shapes.subList(median, shapes.size()), nextAxis));
	}
}

interface KDNode extends Surface {
	void print(int i);
};

class KDSplitNode implements KDNode {
	Axis splitAxis;
	float splitLocation;
	KDNode left, right;

	public KDSplitNode(Axis splitAxis, float splitLocation, KDNode left, KDNode right) {
		this.splitAxis = splitAxis;
		this.splitLocation = splitLocation;
		this.left = left;
		this.right = right;
	}

	public void print() {
		print(0);
	}

	@Override
	public void print(int depth) {
		System.out.print("<KDNode " + splitAxis + " " + splitLocation);
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

	@Override
	public Intersection intersection(Ray ray) {
		return null; // TODO
	}
}

class KDLeaf implements KDNode {
	List<Shape> shapes;

	public KDLeaf(List<Shape> shapes) {
		this.shapes = shapes;
	}

	@Override
	public Intersection intersection(Ray ray) {
		return null; // TODO
	}

	@Override
	public void print(int depth) {
		System.out.print("<KDLeaf");
		for (Shape shape : shapes)
			System.out.print(" " + shape.geometry);
		System.out.println(">");
	}
}
