package raytracer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BVH implements Surface {
	private final BVHNode root;

	public BVH(List<Shape> shapes) {
		root = generate(shapes);
	}

	public BVHNode generate(List<Shape> shapes) {
		return generate(shapes, Axis.X);
	}

	public BVHNode generate(List<Shape> shapes, Axis axis) {
		if (shapes.size() == 1)
			return shapes.get(0);

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

	@Override
	public Intersection intersection(Ray ray, float t0, float t1) {
		return root.intersection(ray, t0, t1);
	}
}

abstract class BVHNode implements Surface {
	public final BoundingBox boundingBox;

	public BVHNode(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
};

class BVHSplitNode extends BVHNode {
	BVHNode left, right;

	public BVHSplitNode(BVHNode left, BVHNode right) {
		super(BoundingBox.including(left.boundingBox, right.boundingBox));
		this.left = left;
		this.right = right;
	}

	@Override
	public Intersection intersection(Ray ray, float t0, float t1) {
		Intersection l = null, r = null;

		if (left.boundingBox.intersection(ray, t0, t1) != null)
			l = left.intersection(ray, t0, t1);
		if (right.boundingBox.intersection(ray, t0, t1) != null)
			r = right.intersection(ray, t0, t1);

		if (l != null && r != null)
			return l.distance <= r.distance ? l : r;
		else
			return l != null ? l : r;
	}
}
