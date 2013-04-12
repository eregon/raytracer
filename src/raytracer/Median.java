package raytracer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Selection algorithm from http://rosettacode.org/wiki/Averages/Median#C */
public class Median<T> {
	final List<T> list;
	final Comparator<T> comp;

	public Median(List<T> list, Comparator<T> comp) {
		this.list = list;
		this.comp = comp;
	}

	public T select(List<T> list, int left, int right, int k) {
		while (left < right) {
			T pivot = list.get(k);
			Collections.swap(list, k, right);

			int pos;
			for (int i = pos = left; i < right; i++) {
				if (comp.compare(list.get(i), pivot) < 0) {
					Collections.swap(list, i, pos);
					pos++;
				}
			}
			Collections.swap(list, right, pos);
			if (pos == k)
				break;
			if (pos < k)
				left = pos + 1;
			else
				right = pos - 1;
		}
		return list.get(k);
	}

	public T findMedian() {
		return select(list, 0, list.size() - 1, (list.size() - 1) / 2);
	}
}
