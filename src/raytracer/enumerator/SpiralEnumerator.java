package raytracer.enumerator;

import java.util.NoSuchElementException;

import raytracer.util.Dir;

public class SpiralEnumerator extends Enumerator {
	final int h, w;
	final int mx, my;
	final int last;
	int current;
	Dir dir = Dir.NORTH;

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
