package Matrizen_Vektoren_Bibliothek;

/**
 *
 * Die 4x4 Matrix wird benötigt, um Objekte zu skalieren, zu transformieren oder um eine der drei Achsen zu rotieren
 *
 */
public class Mat4x4 {
	/**
	 * @param m11
	 *            -m14 erste Spalte der Matrix
	 *
	 * @param m21
	 *            -m24 erste Spalte der Matrix
	 *
	 * @param m31
	 *            -m34 erste Spalte der Matrix
	 *
	 * @param m41
	 *            -m44 erste Spalte der Matrix
	 */
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

	/**
	 * Konstruktor: Mat4x4
	 *
	 * @param m11
	 *            - m44 double-Werte der Felder der Matrix
	 *
	 * @throws IllegalArgumentException
	 */
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

	/**
	 * Methode mul(Vector3)
	 *
	 * @param v
	 *            übergebener Vector3
	 * @return mit der Mat4x4 multiplizierter Vector3
	 *
	 * @throws IllegalArgumentException
	 */
	public Vector3 mul(Vector3 v) {

		if (v == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		return new Vector3(

				v.x * m11 + v.y * m12 + v.z * m13,

				v.x * m21 + v.y * m22 + v.z * m23,

				v.x * m31 + v.y * m32 + v.z * m33);
	}

	/**
	 * Methode mul(Point3)
	 *
	 * @param v
	 *            übergebener Point3
	 * @return mit der Mat4x4 multiplizierter Point3
	 *
	 * @throws IllegalArgumentException
	 */
	public Point3 mul(Point3 p) {

		if (p == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}
		return new Point3(

				p.x * m11 + p.y * m12 + p.z * m13 + m14,

				p.x * m21 + p.y * m22 + p.z * m23 + m24,

				p.x * m31 + p.y * m32 + p.z * m33 + m34

		);
	}

	/**
	 * Methode mul(Mat4x4)
	 *
	 * @param v
	 *            übergebener Mat4x4
	 * @return mit der Mat4x4 multiplizierte Mat4x4
	 *
	 * @throws IllegalArgumentException
	 */
	public Mat4x4 mul(Mat4x4 m) {

		if (m == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		return new Mat4x4(m11 * m.m11 + m12 * m.m21 + m13 * m.m31 + m14 * m.m41, m11 * m.m12 + m12 * m.m22 + m13 * m.m32 + m14 * m.m42,
				m11 * m.m13 + m12 * m.m23 + m13 * m.m33 + m14 * m.m43, m11 * m.m14 + m12 * m.m24 + m13 * m.m34 + m14 * m.m44,
				m21 * m.m11 + m22 * m.m21 + m23 * m.m31 + m24 * m.m41, m21 * m.m12 + m22 * m.m22 + m23 * m.m32 + m24 * m.m42,
				m21 * m.m13 + m22 * m.m23 + m23 * m.m33 + m24 * m.m43, m21 * m.m14 + m22 * m.m24 + m23 * m.m34 + m24 * m.m44,
				m31 * m.m11 + m32 * m.m21 + m33 * m.m31 + m34 * m.m41, m31 * m.m12 + m32 * m.m22 + m33 * m.m32 + m34 * m.m42,
				m31 * m.m13 + m32 * m.m23 + m33 * m.m33 + m34 * m.m43, m31 * m.m14 + m32 * m.m24 + m33 * m.m34 + m34 * m.m44,
				m41 * m.m11 + m42 * m.m21 + m43 * m.m31 + m44 * m.m41, m41 * m.m12 + m42 * m.m22 + m43 * m.m32 + m44 * m.m42,
				m41 * m.m13 + m42 * m.m23 + m43 * m.m33 + m44 * m.m43, m41 * m.m14 + m42 * m.m24 + m43 * m.m34 + m44 * m.m44);

	}

	/**
	 * Vertauscht Zeilen und Spalten gibt die Inverse zurück.
	 * 
	 * @return
	 */
	public Mat4x4 transposed() {
		return new Mat4x4(m11, m21, m31, m41, m12, m22, m32, m42, m13, m23, m33, m43, m14, m24, m34, m44);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(m11);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m12);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m13);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m14);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m21);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m22);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m23);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m24);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m31);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m32);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m33);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m34);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m41);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m42);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m43);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m44);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Mat4x4 other = (Mat4x4) obj;
		if (Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11))
			return false;
		if (Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12))
			return false;
		if (Double.doubleToLongBits(m13) != Double.doubleToLongBits(other.m13))
			return false;
		if (Double.doubleToLongBits(m14) != Double.doubleToLongBits(other.m14))
			return false;
		if (Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21))
			return false;
		if (Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22))
			return false;
		if (Double.doubleToLongBits(m23) != Double.doubleToLongBits(other.m23))
			return false;
		if (Double.doubleToLongBits(m24) != Double.doubleToLongBits(other.m24))
			return false;
		if (Double.doubleToLongBits(m31) != Double.doubleToLongBits(other.m31))
			return false;
		if (Double.doubleToLongBits(m32) != Double.doubleToLongBits(other.m32))
			return false;
		if (Double.doubleToLongBits(m33) != Double.doubleToLongBits(other.m33))
			return false;
		if (Double.doubleToLongBits(m34) != Double.doubleToLongBits(other.m34))
			return false;
		if (Double.doubleToLongBits(m41) != Double.doubleToLongBits(other.m41))
			return false;
		if (Double.doubleToLongBits(m42) != Double.doubleToLongBits(other.m42))
			return false;
		if (Double.doubleToLongBits(m43) != Double.doubleToLongBits(other.m43))
			return false;
		if (Double.doubleToLongBits(m44) != Double.doubleToLongBits(other.m44))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mat4x4 [m11=" + m11 + ", m12=" + m12 + ", m13=" + m13 + ", m14=" + m14 + ", m21=" + m21 + ", m22=" + m22 + ", m23=" + m23
				+ ", m24=" + m24 + ", m31=" + m31 + ", m32=" + m32 + ", m33=" + m33 + ", m34=" + m34 + ", m41=" + m41 + ", m42=" + m42
				+ ", m43=" + m43 + ", m44=" + m44 + "]";
	}
}