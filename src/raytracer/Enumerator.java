package raytracer;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public abstract class Enumerator implements Iterator<Integer>, Iterable<Integer> {
	@Override
	public Iterator<Integer> iterator() {
		return this;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}

class LinearEnumerator extends Enumerator {
	int height, width;
	int current, next = 0, last;

	public LinearEnumerator(int height, int width) {
		this.height = height;
		this.width = width;
		last = height * width - 1;
	}

	@Override
	public boolean hasNext() {
		return current <= last;
	}

	@Override
	public Integer next() {
		if (!hasNext())
			throw new NoSuchElementException();
		current = next;
		next++;
		return current;
	}

}

class SpiralEnumerator extends Enumerator {
	final int h, w;
	int mx, my;
	int current;
	Dir dir = Dir.NORTH;
	int last;

	public SpiralEnumerator(int height, int width) {
		h = height;
		w = width;
		mx = w / 2 - 1;
		my = h / 2 - 1;
		current = (my + 1) * w + mx;
		last = h * w - 1; // (w-1, h-1) = (h-1)*w+w-1
	}

	@Override
	public boolean hasNext() {
		return current != last;
	}

	@Override
	public Integer next() {
		if (!hasNext())
			throw new NoSuchElementException();
		int x = current % w, y = current / w;
		if ((x > mx && y <= my) ? x - mx == -(y - my) + 1 : Math.abs(x - mx) == Math.abs(y - my)) {
			dir = dir.right();
		}
		y += dir.dy;
		x += dir.dx;
		if (y < 0 || y >= h) {
			dir = dir.opposite();
			y += dir.dy;
			x = w - (dir == Dir.SOUTH ? 1 : 2) - x;
		}
		return current = y * w + x;
	}
}

class CircleEnumerator extends Enumerator {
	int h, w;
	int mx, my;
	final int minx, maxx, miny, maxy;
	int last;
	int x = 0, y = 1;
	int r = 1;
	int r_1 = r - 1;
	Dir dir = Dir.SOUTH;
	boolean hasNext = true;
	Queue<Integer> produced = new ArrayDeque<Integer>();

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

class SkippingEnumerator extends Enumerator {
	Enumerator iter;
	int offset;
	int every;
	boolean hasNext = true;
	int current, next;

	public SkippingEnumerator(Enumerator iter, int offset, int every) {
		this.iter = iter;
		this.offset = offset;
		this.every = every;
		for (int i = 0; i < offset; i++) {
			if (!iter.hasNext()) {
				hasNext = false;
				break;
			}
			next = iter.next();
		}
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public Integer next() {
		if (!hasNext)
			throw new NoSuchElementException();
		current = next;
		for (int i = 0; i < every; i++) {
			if (!iter.hasNext()) {
				hasNext = false;
				break;
			}
			next = iter.next();
		}
		return current;
	}
}
