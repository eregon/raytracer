package uclouvain.ingi2325.math;

import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Vector3D;
import uclouvain.ingi2325.utils.Vector4;

/**
 * Represents a 4 by 4 matrix of floats.
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class Matrix4 {
	public final static Matrix4 IDENTITY = Matrix4.identity();

	/**
	 * The first element of the first row.
	 */
	public float m00;

	/**
	 * The second element of the first row.
	 */
	public float m01;

	/**
	 * The third element of the first row.
	 */
	public float m02;

	/**
	 * The forth element of the first row.
	 */

	public float m03;

	/**
	 * The first element of the second row.
	 */

	public float m10;

	/**
	 * The second element of the second row.
	 */

	public float m11;

	/**
	 * The third element of the second row.
	 */

	public float m12;

	/**
	 * The fourth element of the second row.
	 */

	public float m13;

	/**
	 * The first element of the third row.
	 */

	public float m20;

	/**
	 * The second element of the third row.
	 */
	public float m21;

	/**
	 * The third element of the third row.
	 */
	public float m22;

	/**
	 * The fourth element of the third row.
	 */
	public float m23;

	/**
	 * The first element of the fourth row.
	 */
	public float m30;

	/**
	 * The second element of the fourth row.
	 */
	public float m31;

	/**
	 * The third element of the fourth row.
	 */
	public float m32;

	/**
	 * The fourth element of the fourth row.
	 */
	public float m33;

	/**
	 * Constructs and initializes a matrix to all zeros.
	 */
	public Matrix4() {
		m00 = 0.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m03 = 0.0F;
		m10 = 0.0F;
		m11 = 0.0F;
		m12 = 0.0F;
		m13 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 0.0F;
		m23 = 0.0F;
		m30 = 0.0F;
		m31 = 0.0F;
		m32 = 0.0F;
		m33 = 0.0F;
	}

	/**
	 * Constructs and initializes a matrix from the specified 16 values
	 * 
	 * @param m00
	 *            the first row, first column element
	 * @param m01
	 *            the first row, second column element
	 * @param m02
	 *            the first row, third column element
	 * @param m03
	 *            the first row, fourth column element
	 * @param m10
	 *            the second row, first column element
	 * @param m11
	 *            the second row, second column element
	 * @param m12
	 *            the second row, third column element
	 * @param m13
	 *            the second row, fourth column element
	 * @param m20
	 *            the third row, first column element
	 * @param m21
	 *            the third row, second column element
	 * @param m22
	 *            the third row, third column element
	 * @param m23
	 *            the third row, fourth column element
	 * @param m30
	 *            the fourth row, first column element
	 * @param m31
	 *            the fourth row, second column element
	 * @param m32
	 *            the fourth row, third column element
	 * @param m33
	 *            the fourth row, fourth column element
	 */
	public Matrix4(float m00, float m01, float m02, float m03, float m10,
			float m11, float m12, float m13, float m20, float m21, float m22,
			float m23, float m30, float m31, float m32, float m33) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}

	/**
	 * Constructs a new matrix with the same values as the matrix parameter.
	 * 
	 * @param matrix
	 *            the matrix to copy.
	 */
	public Matrix4(Matrix4 matrix) {
		m00 = matrix.m00;
		m01 = matrix.m01;
		m02 = matrix.m02;
		m03 = matrix.m03;
		m10 = matrix.m10;
		m11 = matrix.m11;
		m12 = matrix.m12;
		m13 = matrix.m13;
		m20 = matrix.m20;
		m21 = matrix.m21;
		m22 = matrix.m22;
		m23 = matrix.m23;
		m30 = matrix.m30;
		m31 = matrix.m31;
		m32 = matrix.m32;
		m33 = matrix.m33;
	}

	/**
	 * Returns a string that contains the values of this matrix.
	 */
	@Override
	public String toString() {
		return m00 + ", " + m01 + ", " + m02 + ", " + m03 + "\n" + m10 + ", "
				+ m11 + ", " + m12 + ", " + m13 + "\n" + m20 + ", " + m21
				+ ", " + m22 + ", " + m23 + "\n" + m30 + ", " + m31 + ", "
				+ m32 + ", " + m33 + "\n";
	}

	/**
	 * Sets the specified element of this matrix to the value provided.
	 * 
	 * @param rowIndex
	 *            the row index
	 * @param columnIndex
	 *            the column index
	 * @param value
	 *            the value
	 */
	public final void setElement(int rowIndex, int columnIndex, float value) {
		switch (rowIndex) {
		case 0:
			switch (columnIndex) {
			case 0:
				m00 = value;
				break;

			case 1:
				m01 = value;
				break;

			case 2:
				m02 = value;
				break;

			case 3:
				m03 = value;
				break;

			default:
				throw new ArrayIndexOutOfBoundsException();
			}
			break;

		case 1:
			switch (columnIndex) {
			case 0:
				m10 = value;
				break;

			case 1:
				m11 = value;
				break;

			case 2:
				m12 = value;
				break;

			case 3:
				m13 = value;
				break;

			default:
				throw new ArrayIndexOutOfBoundsException();
			}
			break;

		case 2:
			switch (columnIndex) {
			case 0:
				m20 = value;
				break;

			case 1:
				m21 = value;
				break;

			case 2:
				m22 = value;
				break;

			case 3:
				m23 = value;
				break;

			default:
				throw new ArrayIndexOutOfBoundsException();
			}
			break;

		case 3:
			switch (columnIndex) {
			case 0:
				m30 = value;
				break;

			case 1:
				m31 = value;
				break;

			case 2:
				m32 = value;
				break;

			case 3:
				m33 = value;
				break;

			default:
				throw new ArrayIndexOutOfBoundsException();
			}
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * Retrieves the value at the specified row and column of this matrix.
	 * 
	 * @param rowIndex
	 *            the row index
	 * @param columnIndex
	 *            the column index
	 * @return the value stored at given indexes in the represented matrix.
	 */
	public final float getElement(int rowIndex, int columnIndex) {
		switch (rowIndex) {
		default:
			break;

		case 0:
			switch (columnIndex) {
			case 0:
				return m00;

			case 1:
				return m01;

			case 2:
				return m02;

			case 3:
				return m03;
			}
			break;

		case 1:
			switch (columnIndex) {
			case 0:
				return m10;

			case 1:
				return m11;

			case 2:
				return m12;

			case 3:
				return m13;
			}
			break;

		case 2:
			switch (columnIndex) {
			case 0:
				return m20;

			case 1:
				return m21;

			case 2:
				return m22;

			case 3:
				return m23;
			}
			break;

		case 3:
			switch (columnIndex) {
			case 0:
				return m30;

			case 1:
				return m31;

			case 2:
				return m32;

			case 3:
				return m33;
			}
			break;
		}
		throw new ArrayIndexOutOfBoundsException();
	}

	/**
	 * Copies the matrix values in the specified row into the vector parameter.
	 * 
	 * @param rowIndex
	 *            the row index
	 * @param vector
	 *            the vector used to copy the values.
	 */
	public final void getRow(int rowIndex, Tuple4 vector) {
		if (rowIndex == 0) {
			vector.x = m00;
			vector.y = m01;
			vector.z = m02;
			vector.w = m03;
		} else if (rowIndex == 1) {
			vector.x = m10;
			vector.y = m11;
			vector.z = m12;
			vector.w = m13;
		} else if (rowIndex == 2) {
			vector.x = m20;
			vector.y = m21;
			vector.z = m22;
			vector.w = m23;
		} else if (rowIndex == 3) {
			vector.x = m30;
			vector.y = m31;
			vector.z = m32;
			vector.w = m33;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * Copies the matrix values in the specified column into the vector
	 * parameter. Vector must be initialized.
	 * 
	 * @param columnIndex
	 *            the column index.
	 * @param v
	 *            the vector to store the values.
	 */
	public final void getColumn(int columnIndex, Tuple4 v) {
		if (columnIndex == 0) {
			v.x = m00;
			v.y = m10;
			v.z = m20;
			v.w = m30;
		} else if (columnIndex == 1) {
			v.x = m01;
			v.y = m11;
			v.z = m21;
			v.w = m31;
		} else if (columnIndex == 2) {
			v.x = m02;
			v.y = m12;
			v.z = m22;
			v.w = m32;
		} else if (columnIndex == 3) {
			v.x = m03;
			v.y = m13;
			v.z = m23;
			v.w = m33;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * Sets the specified row of this matrix to the four values provided.
	 * 
	 * @param rowIndex
	 *            the row index
	 * @param m0
	 *            the first value of the row
	 * @param m1
	 *            the second value of the row
	 * @param m2
	 *            the third value of the row
	 * @param m3
	 *            the fourth value of the row
	 */
	public final void setRow(int rowIndex, float m0, float m1, float m2,
			float m3) {
		switch (rowIndex) {
		case 0:
			m00 = m0;
			m01 = m1;
			m02 = m2;
			m03 = m3;
			break;

		case 1:
			m10 = m0;
			m11 = m1;
			m12 = m2;
			m13 = m3;
			break;

		case 2:
			m20 = m0;
			m21 = m1;
			m22 = m2;
			m23 = m3;
			break;

		case 3:
			m30 = m0;
			m31 = m1;
			m32 = m2;
			m33 = m3;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * Sets the specified row of this matrix to the Vector provided.
	 * 
	 * @param rowIndex
	 *            the row index.
	 * @param vector
	 *            the vector representing the values to use as replacement.
	 */
	public final void setRow(int rowIndex, Tuple4 vector) {
		setRow(rowIndex, vector.x, vector.y, vector.z, vector.w);
	}

	/**
	 * Sets the specified column of this matrix to the four values provided.
	 * 
	 * @param columnIndex
	 *            the column index
	 * @param m0
	 *            the first value for the column
	 * @param m1
	 *            the second value for the column
	 * @param m2
	 *            the third value for the column
	 * @param m3
	 *            the fourth value for the column
	 */
	public final void setColumn(int columnIndex, float m0, float m1, float m2,
			float m3) {
		switch (columnIndex) {
		case 0:
			m00 = m0;
			m10 = m1;
			m20 = m2;
			m30 = m3;
			break;

		case 1:
			m01 = m0;
			m11 = m1;
			m21 = m2;
			m31 = m3;
			break;

		case 2:
			m02 = m0;
			m12 = m1;
			m22 = m2;
			m32 = m3;
			break;

		case 3:
			m03 = m0;
			m13 = m1;
			m23 = m2;
			m33 = m3;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * Sets the specified column of this matrix to the vector provided.
	 * 
	 * @param columnIndex
	 *            the column index
	 * @param vector
	 *            the vector to use a replacement
	 */
	public final void setColumn(int columnIndex, Vector4 vector) {
		setColumn(columnIndex, vector.x, vector.y, vector.z, vector.w);
	}

	public final void setDiagonal(Vector4 v) {
		m00 = v.x;
		m11 = v.y;
		m22 = v.z;
		m33 = v.w;
	}

	/**
	 * Sets this matrix to all zeros.
	 */
	public final void resetToZero() {
		m00 = 0.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m03 = 0.0F;
		m10 = 0.0F;
		m11 = 0.0F;
		m12 = 0.0F;
		m13 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 0.0F;
		m23 = 0.0F;
		m30 = 0.0F;
		m31 = 0.0F;
		m32 = 0.0F;
		m33 = 0.0F;
	}

	/**
	 * Sets this matrix to identity.
	 */
	public final void resetToIdentity() {
		m00 = 1.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m03 = 0.0F;
		m10 = 0.0F;
		m11 = 1.0F;
		m12 = 0.0F;
		m13 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 1.0F;
		m23 = 0.0F;
		m30 = 0.0F;
		m31 = 0.0F;
		m32 = 0.0F;
		m33 = 1.0F;
	}

	public static Matrix4 identity() {
		return new Matrix4(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1);
	}

	public Matrix4 mul(float f) {
		return new Matrix4(
				m00 * f, m01 * f, m02 * f, m03 * f,
				m10 * f, m11 * f, m12 * f, m13 * f,
				m20 * f, m21 * f, m22 * f, m23 * f,
				m30 * f, m31 * f, m32 * f, m33 * f);
	}

	public Matrix4 mul(Matrix4 m) {
		if (m == Matrix4.IDENTITY)
			return this;

		/* Generated by:
		4.times do |i|
		  4.times do |j|
		    puts 4.times.map { |k|
		      "m#{i}#{k} * m.m#{k}#{j}"
		    }.join(" + ") + ','
		  end
		end */
		return new Matrix4(
				m00 * m.m00 + m01 * m.m10 + m02 * m.m20 + m03 * m.m30,
				m00 * m.m01 + m01 * m.m11 + m02 * m.m21 + m03 * m.m31,
				m00 * m.m02 + m01 * m.m12 + m02 * m.m22 + m03 * m.m32,
				m00 * m.m03 + m01 * m.m13 + m02 * m.m23 + m03 * m.m33,
				m10 * m.m00 + m11 * m.m10 + m12 * m.m20 + m13 * m.m30,
				m10 * m.m01 + m11 * m.m11 + m12 * m.m21 + m13 * m.m31,
				m10 * m.m02 + m11 * m.m12 + m12 * m.m22 + m13 * m.m32,
				m10 * m.m03 + m11 * m.m13 + m12 * m.m23 + m13 * m.m33,
				m20 * m.m00 + m21 * m.m10 + m22 * m.m20 + m23 * m.m30,
				m20 * m.m01 + m21 * m.m11 + m22 * m.m21 + m23 * m.m31,
				m20 * m.m02 + m21 * m.m12 + m22 * m.m22 + m23 * m.m32,
				m20 * m.m03 + m21 * m.m13 + m22 * m.m23 + m23 * m.m33,
				m30 * m.m00 + m31 * m.m10 + m32 * m.m20 + m33 * m.m30,
				m30 * m.m01 + m31 * m.m11 + m32 * m.m21 + m33 * m.m31,
				m30 * m.m02 + m31 * m.m12 + m32 * m.m22 + m33 * m.m32,
				m30 * m.m03 + m31 * m.m13 + m32 * m.m23 + m33 * m.m33);
	}

	public Point3D mul(Point3D p) {
		return new Point3D(
				m00 * p.x + m01 * p.y + m02 * p.z + m03,
				m10 * p.x + m11 * p.y + m12 * p.z + m13,
				m20 * p.x + m21 * p.y + m22 * p.z + m23);
	}

	public Vector3D mul(Vector3D v) {
		return new Vector3D(
				m00 * v.x + m01 * v.y + m02 * v.z,
				m10 * v.x + m11 * v.y + m12 * v.z,
				m20 * v.x + m21 * v.y + m22 * v.z);
	}

	public Matrix4 inverse() {
		// From http://stackoverflow.com/questions/1148309/inverting-a-4x4-matrix
		Matrix4 inv = new Matrix4();

		inv.m00 = m11 * m22 * m33 - m11 * m23 * m32 - m21 * m12 * m33
				+ m21 * m13 * m32 + m31 * m12 * m23 - m31 * m13 * m22;
		inv.m10 = -m10 * m22 * m33 + m10 * m23 * m32 + m20 * m12 * m33
				- m20 * m13 * m32 - m30 * m12 * m23 + m30 * m13 * m22;
		inv.m20 = m10 * m21 * m33 - m10 * m23 * m31 - m20 * m11 * m33
				+ m20 * m13 * m31 + m30 * m11 * m23 - m30 * m13 * m21;
		inv.m30 = -m10 * m21 * m32 + m10 * m22 * m31 + m20 * m11 * m32
				- m20 * m12 * m31 - m30 * m11 * m22 + m30 * m12 * m21;

		float det = m00 * inv.m00 + m01 * inv.m10 + m02 * inv.m20 + m03 * inv.m30;
		if (det == 0f)
			throw new Error("Non invertible matrix!");
		det = 1.0f / det;

		inv.m01 = -m01 * m22 * m33 + m01 * m23 * m32 + m21 * m02 * m33
				- m21 * m03 * m32 - m31 * m02 * m23 + m31 * m03 * m22;
		inv.m11 = m00 * m22 * m33 - m00 * m23 * m32 - m20 * m02 * m33
				+ m20 * m03 * m32 + m30 * m02 * m23 - m30 * m03 * m22;
		inv.m21 = -m00 * m21 * m33 + m00 * m23 * m31 + m20 * m01 * m33
				- m20 * m03 * m31 - m30 * m01 * m23 + m30 * m03 * m21;
		inv.m31 = m00 * m21 * m32 - m00 * m22 * m31 - m20 * m01 * m32
				+ m20 * m02 * m31 + m30 * m01 * m22 - m30 * m02 * m21;

		inv.m02 = m01 * m12 * m33 - m01 * m13 * m32 - m11 * m02 * m33
				+ m11 * m03 * m32 + m31 * m02 * m13 - m31 * m03 * m12;
		inv.m12 = -m00 * m12 * m33 + m00 * m13 * m32 + m10 * m02 * m33
				- m10 * m03 * m32 - m30 * m02 * m13 + m30 * m03 * m12;
		inv.m22 = m00 * m11 * m33 - m00 * m13 * m31 - m10 * m01 * m33
				+ m10 * m03 * m31 + m30 * m01 * m13 - m30 * m03 * m11;
		inv.m32 = -m00 * m11 * m32 + m00 * m12 * m31 + m10 * m01 * m32
				- m10 * m02 * m31 - m30 * m01 * m12 + m30 * m02 * m11;

		inv.m03 = -m01 * m12 * m23 + m01 * m13 * m22 + m11 * m02 * m23
				- m11 * m03 * m22 - m21 * m02 * m13 + m21 * m03 * m12;
		inv.m13 = m00 * m12 * m23 - m00 * m13 * m22 - m10 * m02 * m23
				+ m10 * m03 * m22 + m20 * m02 * m13 - m20 * m03 * m12;
		inv.m23 = -m00 * m11 * m23 + m00 * m13 * m21 + m10 * m01 * m23
				- m10 * m03 * m21 - m20 * m01 * m13 + m20 * m03 * m11;
		inv.m33 = m00 * m11 * m22 - m00 * m12 * m21 - m10 * m01 * m22
				+ m10 * m02 * m21 + m20 * m01 * m12 - m20 * m02 * m11;

		return inv.mul(det);
	}

	public Matrix4 transpose() {
		return new Matrix4(
				m00, m10, m20, m30,
				m01, m11, m21, m31,
				m02, m12, m22, m32,
				m03, m13, m23, m33);
	}
}
