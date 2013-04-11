package raytracer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Selection algorithm from http://en.wikipedia.org/wiki/Selection_algorithm */
public class Median<T> {
	List<T> list;
	Comparator<T> comp;

	public Median(List<T> list, Comparator<T> comp) {
		this.list = list;
		this.comp = comp;
	}

	private int partition(List<T> list, int left, int right, int pivotIndex) {
		T pivotValue = list.get(pivotIndex);
		Collections.swap(list, pivotIndex, right);

		int storeIndex = left;
		for (int i = left; i < right; i++) {
			if (comp.compare(list.get(i), pivotValue) < 0) {
				Collections.swap(list, storeIndex, i);
				storeIndex++;
			}
		}
		Collections.swap(list, right, storeIndex);
		return storeIndex;
	}

	public T select(List<T> list, int left, int right, int k) {
		while (true) {
			if (left == right)
				return list.get(left);

			int pivotIndex = (left + right + 1) / 2; // TODO
			int pivotNewIndex = partition(list, left, right, pivotIndex);
			int pivotDist = pivotNewIndex - left + 1;
			if (pivotDist == k) {
				return list.get(pivotNewIndex);
			} else if (k < pivotDist) {
				right = pivotNewIndex - 1;
			} else {
				k -= pivotDist;
				left = pivotNewIndex + 1;
			}
		}
	}

	public T findMedian() {
		return select(list, 0, list.size() - 1, (list.size() + 1) / 2);
	}
}
