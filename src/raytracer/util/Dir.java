package raytracer.util;

public enum Dir {
	NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

	public final int dx;
	public final int dy;

	private Dir(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public Dir right() {
		switch (this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		default:
			return null;
		}
	}

	public Dir opposite() {
		return right().right();
	}
}
