package uclouvain.ingi2325.parser;

public final class Splitter {
	final String str;
	final char[] ary;
	final int len;
	int pos;

	public Splitter(String str) {
		this.str = str;
		ary = str.toCharArray();
		len = ary.length;
	}

	private final boolean negative() {
		if (ary[pos] == '-') {
			pos++;
			return true;
		}
		return false;
	}

	public final int getInt() {
		int value = 0;
		boolean neg = negative();

		int before = pos;
		char c;
		while (pos < len && (c = ary[pos]) >= '0' && c <= '9') {
			value = 10 * value + (ary[pos] - '0');
			pos++;
		}
		if (pos == before)
			throw new NumberFormatException(str.substring(before));
		return neg ? -value : value;
	}

	public float getFloat() {
		float value = 0f;
		boolean neg = negative();

		int before = pos;
		int divider = 1;
		boolean afterDot = false;
		char c;
		while (pos < len && ((c = ary[pos]) >= '0' && c <= '9')) {
			value = 10 * value + (ary[pos] - '0');
			pos++;
			if (afterDot) {
				divider *= 10;
			} else if (pos < len && ary[pos] == '.') {
				afterDot = true;
				pos++;
			}
		}
		if (pos == before)
			throw new NumberFormatException(str.substring(before));
		value /= divider;
		return neg ? -value : value;
	}

	public final boolean have(char c) {
		if (ary[pos] == c) {
			pos++;
			return true;
		} else
			return false;
	}

	public final String getWord() {
		int start = pos;
		while (pos < len && ary[pos] != ' ')
			pos++;
		return str.substring(start, pos);
	}

	public final String rest() {
		return str.substring(pos);
	}

	public final boolean more() {
		return pos < len;
	}

	public final char current() {
		return ary[pos];
	}

	public final int count(char c) {
		int count = 0;
		for (int i = pos; i < len; i++) {
			if (ary[i] == c)
				count++;
		}
		return count;
	}

	public final void eatSpace() {
		if (pos < len && ary[pos] == ' ')
			pos++;
	}
}
