package raytracer;

import uclouvain.ingi2325.math.Matrix4;
import uclouvain.ingi2325.utils.Vector3D;
import uclouvain.ingi2325.utils.Vector4;

public class Transformation {

	public static final Transformation DEFAULT = new Transformation();

	public Transformation parent;

	/** The transformation matrix */
	public Matrix4 m;
	/** The inverse transformation matrix */
	public Matrix4 m_1;
	/** The transposed inverse transformation matrix */
	public Matrix4 m_1t;

	private Transformation() {
		parent = null;
		m = Matrix4.IDENTITY;
		m_1 = m;
		m_1t = m;
	}

	public Transformation(Transformation parent) {
		this.parent = parent;
	}

	public boolean isDefault() {
		return this == DEFAULT;
	}

	private Transformation transform(Matrix4 direct, Matrix4 inverse) {
		Transformation t = new Transformation(this);
		t.m = m.mul(direct);
		t.m_1 = inverse.mul(m_1);
		t.m_1t = t.m_1.transpose();
		return t;
	}

	public Transformation onTopOf(Transformation base) {
		return base.transform(m, m_1);
	}

	public Transformation translate(Vector3D t) {
		return transform(translation(t), translation(t.opposite()));
	}

	public Transformation scale(Vector3D scale) {
		return transform(scaling(scale), scaling(scale.inverse()));
	}

	public Transformation rotate(Vector3D axis, float angle) {
		axis = axis.normalize();
		return transform(rotation(axis, angle), rotation(axis, -angle));
	}

	public static Matrix4 translation(Vector3D translation) {
		Matrix4 m = Matrix4.identity();
		m.setColumn(3, new Vector4(translation, 1));
		return m;
	}

	public static Matrix4 scaling(Vector3D scale) {
		Matrix4 m = Matrix4.identity();
		m.setDiagonal(new Vector4(scale, 1));
		return m;
	}

	public static Matrix4 rotation(Vector3D axis, float angle) {
		// See http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle
		float x = axis.x, y = axis.y, z = axis.z;
		float s = (float) Math.sin(angle);
		float c = (float) Math.cos(angle);
		float d = 1 - c;

		return new Matrix4(
				c + x * x * d, x * y * d - z * s, x * z * d + y * s, 0,
				y * x * d + z * s, c + y * y * d, y * z * d - x * s, 0,
				z * x * d - y * s, z * y * d + x * s, c + z * z * d, 0,
				0, 0, 0, 1);
	}
}
