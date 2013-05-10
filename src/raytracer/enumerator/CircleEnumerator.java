package raytracer.enumerator;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

import raytracer.util.Dir;

public class CircleEnumerator extends Enumerator {
	final int h, w;
	final int mx, my;
	final int minx, maxx, miny, maxy;
	final int last;
	int x = 0, y = 1;
	int r = 1;
	int r_1 = r - 1;
	Dir dir = Dir.SOUTH;
	boolean hasNext = true;
	final Queue<Integer> produced = new ArrayDeque<Integer>();

	public CircleEnumerator(int height, int width) {
		h = height;
		w = width;
		mx = w / 2 - 1;
		my = h / 2 - 1;
		last = w - 1;//h * w - 1;
		minx = -mx;
		maxx = mx + 1;
		miny = -my;
		maxy = my + 1;
		add(0, 0);
		add(1, 0);
		add(0, 1);
	}

	@Override
	public boolean hasNext() {
		return hasNext || !produced.isEmpty();
	}

	public boolean add(int x, int y) {
		//System.out.println(produced.getLast());
		if (minx <= x && x <= maxx && miny <= y && y <= maxy) {
			int v = (y + my) * w + (x + mx);
			produced.add(v);
			if (v == last) {
				hasNext = false;
				return true;
			}
		}
		return false;
	}

	private void generate() {
		if (x == dir.dx * r && y == dir.dy * r) {
			dir = dir.right();
		}

		switch (dir) {
		case NORTH:
		case EAST:
			x++;
			break;
		case SOUTH:
		case WEST:
			x--;
			break;
		default:
			break;
		}

		if (x == r && dir == Dir.EAST) { // enlarge circle
			y = 0;
			r++;
			r_1++;
			x++;
			add(x, y);
			return;
		}

		int from, to;
		switch (dir) {
		case NORTH:
		case EAST:
			from = -(int) Math.sqrt(r * r - x * x);
			to = (int) -(Math.sqrt(r_1 * r_1 - x * x)) - 1;
			break;
		case SOUTH:
		case WEST:
			from = (int) Math.sqrt(r_1 * r_1 - x * x) + 1;
			to = (int) Math.sqrt(r * r - x * x);
			break;
		default:
			from = 0;
			to = 0;
			break;
		}

		if (from >= to) {
			y = to;
			if (add(x, y))
				return;
		} else {
			for (y = from; y <= to; y++) {
				if (add(x, y))
					return;
			}
		}

	}

	@Override
	public Integer next() {
		if (!hasNext())
			throw new NoSuchElementException();

		while (produced.isEmpty() && hasNext)
			generate();
		return produced.remove();
	}
}
