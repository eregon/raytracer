package raytracer.enumerator;

import java.util.NoSuchElementException;

public class RadialEnumerator extends Enumerator {
	final Enumerator iter;
	final int offset;
	final int n;
	final int h, w;
	final int mx, my;
	final double a, b;
	boolean hasNext = true;
	int current, next;

	public RadialEnumerator(Enumerator iter, int offset, int n, int height, int width) {
		this.iter = iter;
		this.offset = offset;
		this.n = n;
		h = height;
		w = width;
		mx = w / 2 - 1;
		my = h / 2 - 1;
		// - Math.PI for [-PI; PI] range for atan2()
		a = offset * 2 * Math.PI / n - Math.PI;
		b = (offset + 1) * 2 * Math.PI / n - Math.PI;

		generate();
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	private void generate() {
		int x, y;
		double angle;
		do {
			if (!iter.hasNext()) {
				hasNext = false;
				break;
			}
			next = iter.next();
			x = next % w - mx;
			y = next / w - my;
			angle = Math.atan2(y, x);
		} while (!(a < angle && angle <= b));
	}

	@Override
	public Integer next() {
		if (!hasNext)
			throw new NoSuchElementException();

		current = next;

		generate();

		return current;
	}
}
