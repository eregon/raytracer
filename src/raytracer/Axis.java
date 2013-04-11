package raytracer;

public enum Axis {
	X, Y, Z;

	public Axis next() {
		switch (this) {
		case X:
			return Y;
		case Y:
			return Z;
		case Z:
			return X;
		default:
			return null;
		}
	}
}
