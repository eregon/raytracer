package raytracer;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class Enumerator implements Iterator<Integer>, Iterable<Integer> {
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
