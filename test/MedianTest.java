import java.util.Arrays;
import java.util.Comparator;

import junit.framework.TestCase;
import raytracer.Median;

public class MedianTest extends TestCase {
	Comparator<Integer> ci = new Comparator<Integer>() {
		@Override
		public int compare(Integer a, Integer b) {
			return a.compareTo(b);
		}
	};

	private int median(Integer... list) {
		Median<Integer> med = new Median<Integer>(Arrays.asList(list), ci);
		return med.findMedian();
	}

	public void testFindMedian() throws Exception {
		assertEquals(1, median(1));
		assertEquals(1, median(1, 2));
		assertEquals(2, median(3, 1, 2));
		assertEquals(2, median(2, 7, 0, 4));
		assertEquals(3, median(2, 7, 0, 4, 3));
		assertEquals(3, median(2, 7, 0, 4, 3, 6));
		assertEquals(5, median(5, 4, 6, 7, 2, 3, 8));
	}
}
