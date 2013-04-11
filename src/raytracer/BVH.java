package raytracer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BVH {
	BVHNode root;

	public BVH(List<Shape> shapes) {
		root = generate(shapes);
	}

	public BVHNode generate(List<Shape> shapes) {
		return generate(shapes, Axis.X);
	}

	public BVHNode generate(List<Shape> shapes, Axis axis) {
		if (shapes.size() == 1)
			return new BVHLeaf(shapes.get(0));

		Comparator<Shape> comparator = Shape.comparatorForAxis(axis);
		Shape median = new Median<Shape>(shapes, comparator).findMedian();

		int maxLeftSize = (shapes.size() + 1) / 2;
		List<Shape> left = new ArrayList<Shape>(maxLeftSize);
		List<Shape> right = new ArrayList<Shape>(shapes.size() - maxLeftSize);
		for (Shape shape : shapes) {
			// We need to ensure no less than half go to the right (happens when many are equal)
			if (comparator.compare(shape, median) <= 0 && left.size() < maxLeftSize)
				left.add(shape);
			else
				right.add(shape);
		}

		Axis next = axis.next();

		return new BVHSplitNode(
				generate(left, next),
				generate(right, next));
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
	Shape shape;

	public BVHLeaf(Shape shape) {
		this.shape = shape;
		box = shape.boundingBox;
	}

	@Override
	public Intersection intersection(Ray ray) {
		return shape.intersection(ray);
	}

	@Override
	public void print(int depth) {
		System.out.print("<BVHLeaf " + shape + ">");
	}
}
