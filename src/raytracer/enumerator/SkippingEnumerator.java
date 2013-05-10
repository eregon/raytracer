package raytracer.enumerator;

import java.util.NoSuchElementException;

public class SkippingEnumerator extends Enumerator {
	final Enumerator iter;
	final int offset;
	final int every;
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
