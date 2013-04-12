package uclouvain.ingi2325.parser;

public final class Splitter {
	final String str;
	final int len;
	int pos;

	public Splitter(String str) {
		this.str = str;
		len = str.length();
	}

	private final boolean negative() {
		if (str.charAt(pos) == '-') {
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
		while (pos < len && (c = str.charAt(pos)) >= '0' && c <= '9') {
			value = 10 * value + (str.charAt(pos) - '0');
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
		while (pos < len && ((c = str.charAt(pos)) >= '0' && c <= '9')) {
			value = 10 * value + (str.charAt(pos) - '0');
			pos++;
			if (afterDot) {
				divider *= 10;
			} else if (pos < len && str.charAt(pos) == '.') {
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
		if (str.charAt(pos) == c) {
			pos++;
			return true;
		} else
			return false;
	}

	public final String getWord() {
		int start = pos;
		while (pos < len && str.charAt(pos) != ' ')
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
		return str.charAt(pos);
	}

	public final int count(char c) {
		int count = 0;
		for (int i = pos; i < len; i++) {
			if (str.charAt(i) == c)
				count++;
		}
		return count;
	}

	public final void eatSpace() {
		if (pos < len && str.charAt(pos) == ' ')
			pos++;
	}
}
