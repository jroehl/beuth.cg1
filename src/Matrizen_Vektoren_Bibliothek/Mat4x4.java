package Matrizen_Vektoren_Bibliothek;

public class Mat4x4 {
	final public double m11;
	final public double m12;
	final public double m13;
	final public double m14;

	final public double m21;
	final public double m22;
	final public double m23;
	final public double m24;

	final public double m31;
	final public double m32;
	final public double m33;
	final public double m34;

	final public double m41;
	final public double m42;
	final public double m43;
	final public double m44;

	public Mat4x4(double m11, double m12, double m13, double m14, double m21, double m22, double m23, double m24, double m31, double m32,
			double m33, double m34, double m41, double m42, double m43, double m44) {
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m14 = m14;

		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m24 = m24;

		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
		this.m34 = m34;

		this.m41 = m41;
		this.m42 = m42;
		this.m43 = m43;
		this.m44 = m44;
	}

	// _________________________________________________________________________________

	public Vector3 mul(Vector3 v) {
		return new Vector3(v.x * this.m11 + v.y * this.m12 + v.z * this.m13, v.x * this.m21 + v.y * this.m22 + v.z * this.m23, v.x
				* this.m31 + v.y * this.m32 + v.z * this.m33);
	}

	// _________________________________________________________________________________

	public Point3 mul(Point3 p) {
		return new Point3(p.x * this.m11 + p.y * this.m12 + p.z * m13 + m14, p.x * this.m21 + p.y * this.m22 + p.z * m23 + m24, p.x
				* this.m31 + p.y * this.m32 + p.z * m33 + m34);
	}

	// _________________________________________________________________________________

	public Mat4x4 mul(Mat4x4 m) {
		return new Mat4x4(m11 * m.m11 + m12 * m.m21 + m13 * m.m31 + m14 * m.m41, m11 * m.m12 + m12 * m.m22 + m13 * m.m32 + m14 * m.m42, m11
				* m.m13 + m12 * m.m23 + m13 * m.m33 + m14 * m.m43, m11 * m.m14 + m12 * m.m24 + m13 * m.m34 + m14 * m.m44, m21 * m.m11 + m22
				* m.m21 + m23 * m.m31 + m24 * m.m41, m21 * m.m12 + m22 * m.m22 + m23 * m.m32 + m24 * m.m42, m21 * m.m13 + m22 * m.m23 + m23
				* m.m33 + m24 * m.m43, m21 * m.m14 + m22 * m.m24 + m23 * m.m34 + m24 * m.m44, m31 * m.m11 + m32 * m.m21 + m33 * m.m31 + m34
				* m.m41, m31 * m.m12 + m32 * m.m22 + m33 * m.m32 + m34 * m.m42, m31 * m.m13 + m32 * m.m23 + m33 * m.m33 + m34 * m.m43, m31
				* m.m14 + m32 * m.m24 + m33 * m.m34 + m34 * m.m44, m41 * m.m11 + m42 * m.m21 + m43 * m.m31 + m44 * m.m41, m41 * m.m12 + m42
				* m.m22 + m43 * m.m32 + m44 * m.m42, m41 * m.m13 + m42 * m.m23 + m43 * m.m33 + m44 * m.m43, m41 * m.m14 + m42 * m.m24 + m43
				* m.m34 + m44 * m.m44);
	}

	// _________________________________________________________________________________

	public Mat4x4 transposed() { // Zeilen und Spalten vertauscht
		return new Mat4x4(m11, m21, m31, m41, m12, m22, m32, m42, m13, m23, m33, m43, m14, m24, m34, m44);
	}

}
