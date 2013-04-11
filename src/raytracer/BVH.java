package raytracer;

import java.util.Collections;
import java.util.List;

public class BVH {
	BVHNode root;

	public BVH(List<Shape> objects) {
		root = generate(objects);
	}

	public BVHNode generate(List<Shape> shapes) {
		return generate(shapes, Axis.X);
	}

	public BVHNode generate(List<Shape> shapes, Axis axis) {
		if (shapes.size() <= 2)
			return new BVHLeaf(shapes);

		Collections.sort(shapes, Shape.comparatorForAxis(axis));
		int median = shapes.size() / 2;

		Axis nextAxis = axis.next();

		List<Shape> left = shapes.subList(0, median);
		List<Shape> right = shapes.subList(median, shapes.size());

		return new BVHSplitNode(
				generate(left, nextAxis),
				generate(right, nextAxis));
	}
}

abstract class BVHNode implements Surface {
	BoundingBox box = new BoundingBox();

	abstract void print(int i);

	public void print() {
		print(0);
	}
};

class BVHSplitNode extends BVHNode {
	BVHNode left, right;

	public BVHSplitNode(BVHNode left, BVHNode right) {
		this.left = left;
		this.right = right;
		box.include(left.box);
		box.include(right.box);
	}

	@Override
	public void print(int depth) {
		System.out.println("<BVHNode " + box);

		indent(depth + 1);
		System.out.print("left=");
		left.print(depth + 1);

		indent(depth + 1);
		System.out.print("right=");
		right.print(depth + 1);
	}

	private void indent(int amount) {
		for (int i = 0; i < amount; i++)
			System.out.print("  ");
	}

	@Override
	public Intersection intersection(Ray ray) {
		Intersection l = null, r = null;

		if (left.box.intersection(ray) != null)
			l = left.intersection(ray);
		if (right.box.intersection(ray) != null)
			r = right.intersection(ray);

		if (l != null && r != null)
			return l.distance <= r.distance ? l : r;
		else
			return l != null ? l : r;
	}
}

class BVHLeaf extends BVHNode {
	List<Shape> shapes;

	public BVHLeaf(List<Shape> shapes) {
		this.shapes = shapes;
		for (Shape shape : shapes)
			box.include(shape.boundingBox);
	}

	@Override
	public Intersection intersection(Ray ray) {
		return null; // TODO
	}

	@Override
	public void print(int depth) {
		System.out.print("<BVHLeaf");
		for (Shape shape : shapes)
			System.out.print(" " + shape.geometry);
		System.out.println(">");
	}
}
