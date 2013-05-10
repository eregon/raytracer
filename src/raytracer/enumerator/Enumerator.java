package raytracer.enumerator;

import java.util.Iterator;

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
