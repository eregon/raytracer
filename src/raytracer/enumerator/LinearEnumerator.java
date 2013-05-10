package raytracer.enumerator;

import java.util.NoSuchElementException;

public class LinearEnumerator extends Enumerator {
	final int height, width, last;
	int current, next = 0;

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
