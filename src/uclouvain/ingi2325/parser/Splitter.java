package uclouvain.ingi2325.parser;

public final class Splitter {
	private static final int MAX_FLOAT_DECIMAL_DIGITS = 7;

	final String str;
	final int len;
	int pos;

	public Splitter(String str) {
		this.str = str;
		len = str.length();
	}

	public final boolean more() {
		return pos < len;
	}

	public final char current() {
		return str.charAt(pos);
	}

	public final String rest() {
		return str.substring(pos);
	}

	public final boolean have(char c) {
		if (pos < len && str.charAt(pos) == c) {
			pos++;
			return true;
		} else
			return false;
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

	public final String getWord() {
		int start = pos;
		while (pos < len && str.charAt(pos) != ' ')
			pos++;
		return str.substring(start, pos);
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

	public final float getFloat() {
		double value = 0;
		boolean neg = negative();

		int before = pos;
		int divider = 1;
		int exponent = 0;
		boolean afterDot = false;
		char c;
		int digits = 0;
		while (pos < len && ((c = str.charAt(pos)) >= '0' && c <= '9')) {
			value = 10 * value + (str.charAt(pos) - '0');
			digits++;
			pos++;

			if (afterDot)
				divider *= 10;

			if (pos == len)
				break;

			if (str.charAt(pos) == '.') {
				pos++;
				afterDot = true;
			} else if (str.charAt(pos) == 'E') {
				pos++;
				exponent = getInt();
				break;
			}

			if (digits > MAX_FLOAT_DECIMAL_DIGITS) {
				while (pos < len && ((c = str.charAt(pos)) >= '0' && c <= '9'))
					pos++;
				break;
			}
		}
		if (pos == before)
			throw new NumberFormatException(str.substring(before));
		value /= divider;
		if (exponent != 0)
			value *= Math.pow(10, exponent);
		return (float) (neg ? -value : value);
	}
}
