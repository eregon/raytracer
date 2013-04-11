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

	@Override
	public String toString() {
		return root.toString();
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

	@Override
	public String toString() {
		return "<KDNode loc=" + location.boundingBox.center()
				+ "\n  left=" + left + "\n  right=" + right + ">";
	}
}
