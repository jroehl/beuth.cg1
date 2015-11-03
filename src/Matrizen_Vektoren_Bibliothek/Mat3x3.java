package Matrizen_Vektoren_Bibliothek;

/**
 *
 * 3x3-Matrix
 *
 * @author Waschmaschine
 */
public class Mat3x3 {

	/**
	 * Feld 1 - 1 einer 3x3 Matrix
	 */
	final public double m11;

	/**
	 * Feld 1 - 2 einer 3x3 Matrix
	 */
	final public double m12;

	/**
	 * Feld 1 - 3 einer 3x3 Matrix
	 */
	final public double m13;

	/**
	 * Feld 2 - 1 einer 3x3 Matrix
	 */
	final public double m21;

	/**
	 * Feld 2 - 2 einer 3x3 Matrix
	 */
	final public double m22;

	/**
	 * Feld 2 - 3 einer 3x3 Matrix
	 */
	final public double m23;

	/**
	 * Feld 3 - 1 einer 3x3 Matrix
	 */
	final public double m31;

	/**
	 * Feld 3 - 2 einer 3x3 Matrix
	 */
	final public double m32;

	/**
	 * Feld 3 - 3 einer 3x3 Matrix
	 */
	final public double m33;

	/**
	 * Determinante der Matrix
	 */
	final public double determinant;

	/**
	 * Konstruktor der Klasse. Die Parameter werden Zeile für Zeile übergeben
	 *
	 * @param m11
	 *            setzt den double Wert des Feld 1 - 1
	 * @param m12
	 *            setzt den double Wert des Feld 1 - 2
	 * @param m13
	 *            setzt den double Wert des Feld 1 - 3
	 * @param m21
	 *            setzt den double Wert des Feld 2 - 1
	 * @param m22
	 *            setzt den double Wert des Feld 2 - 2
	 * @param m23
	 *            setzt den double Wert des Feld 2 - 3
	 * @param m31
	 *            setzt den double Wert des Feld 3 - 1
	 * @param m32
	 *            setzt den double Wert des Feld 3 - 2
	 * @param m33
	 *            setzt den double Wert des Feld 3 - 3
	 */
	public Mat3x3(final double m11, final double m12, final double m13,
			final double m21, final double m22, final double m23,
			final double m31, final double m32, final double m33) {
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
		this.determinant = (this.m11 * this.m22 * this.m33 + this.m12
				* this.m23 * this.m31 + this.m13 * this.m21 * this.m32
				- this.m31 * this.m22 * this.m13 - this.m32 * this.m23
				* this.m11 - this.m33 * this.m21 * this.m12);

	}

	/**
	 * Matrix-Matrix-Multiplikation
	 *
	 * @param m
	 *            Matrix die mit der Matrix multipliziert wird
	 * @return neues Mat3x3 Objekt (Matrix)
	 * @throws IllegalArgumentException
	 */
	public Mat3x3 mul(final Mat3x3 m) throws IllegalArgumentException {

		if (m == null) {
			throw new IllegalArgumentException();
		}

		return new Mat3x3(this.m11 * m.m11 + this.m12 * m.m21 + this.m13
				* m.m31,
				this.m11 * m.m12 + this.m12 * m.m22 + this.m13 * m.m32,
				this.m11 * m.m13 + this.m12 * m.m23 + this.m13 * m.m33,
				this.m21 * m.m11 + this.m21 * m.m12 + this.m23 * m.m31,
				this.m21 * m.m12 + this.m22 * m.m22 + this.m23 * m.m32,
				this.m21 * m.m13 + this.m23 * m.m32 + this.m23 * m.m33,
				this.m31 * m.m11 + this.m32 * m.m21 + this.m33 * m.m13,
				this.m31 * m.m12 + this.m32 * m.m22 + this.m33 * m.m32,
				this.m31 * m.m13 + this.m32 * m.m23 + this.m33 * m.m33);
	}

	/**
	 * Matrix-Vektor-Multiplikation
	 *
	 * @param v
	 *            Vektor der mit der Matrix multipliziert wird
	 * @return neues Vector3 Objekt (Vektor)
	 * @throws IllegalArgumentException
	 */
	public Vector3 mul(final Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return new Vector3((this.m11 * v.x + this.m12 * v.y + this.m13 * v.z),
				(this.m21 * v.x + this.m22 * v.y + this.m23 * v.z), (this.m31
						* v.x + this.m32 * v.y + this.m33 * v.z));
	}

	/**
	 * Matrix-Punkt-Multiplikation
	 *
	 * @param p
	 *            Punkt der mit der Matrix multipliziert wird
	 * @return neues Point 3 Objekt (Punkt)
	 * @throws IllegalArgumentException
	 */
	public Point3 mul(final Point3 p) throws IllegalArgumentException {

		if (p == null) {
			throw new IllegalArgumentException();
		}

		return new Point3(this.m11 * p.x + this.m12 * p.y + this.m13 * p.z,
				this.m21 * p.x + this.m22 * p.y + this.m23 * p.z, this.m31
						* p.x + this.m32 * p.y + this.m33 * p.z);
	}

	/**
	 * Spalte 1 austauschen
	 *
	 * @param m
	 *            Vektor der in die erste Spalte der Matrix eingesetzt wird
	 * @return neues Mat3x3 Objekt (Matrix)
	 * @throws IllegalArgumentException
	 */
	public Mat3x3 changeCol1(final Vector3 m) throws IllegalArgumentException {

		if (m == null) {
			throw new IllegalArgumentException();
		}

		return new Mat3x3(m.x, m12, m13, m.y, m22, m23, m.z, m32, m33);

	}

	/**
	 * Spalte 2 austauschen
	 *
	 * @param m
	 *            Vektor der in die zweite Spalte der Matrix eingesetzt wird
	 * @return neues Mat3x3 Objekt (Matrix)
	 * @throws IllegalArgumentException
	 */
	public Mat3x3 changeCol2(final Vector3 m) throws IllegalArgumentException {

		if (m == null) {
			throw new IllegalArgumentException();
		}

		return new Mat3x3(m11, m.x, m13, m21, m.y, m23, m31, m.z, m33);
	}

	/**
	 * Spalte 3 austauschen
	 *
	 * @param m
	 *            Vektor der in die dritte Spalte der Matrix eingesetzt wird
	 * @return neues Mat3x3 Objekt (Matrix)
	 * @throws IllegalArgumentException
	 */
	public Mat3x3 changeCol3(final Vector3 m) throws IllegalArgumentException {

		if (m == null) {
			throw new IllegalArgumentException();
		}

		return new Mat3x3(m11, m12, m.x, m21, m22, m.y, m31, m32, m.z);

	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param o
	 *            Objekt das mit der Matrix verglichen wird
	 * @return true | false
	 *
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Mat3x3 mat3x3 = (Mat3x3) o;

		if (Double.compare(mat3x3.m11, m11) != 0)
			return false;
		if (Double.compare(mat3x3.m12, m12) != 0)
			return false;
		if (Double.compare(mat3x3.m13, m13) != 0)
			return false;
		if (Double.compare(mat3x3.m21, m21) != 0)
			return false;
		if (Double.compare(mat3x3.m22, m22) != 0)
			return false;
		if (Double.compare(mat3x3.m23, m23) != 0)
			return false;
		if (Double.compare(mat3x3.m31, m31) != 0)
			return false;
		if (Double.compare(mat3x3.m32, m32) != 0)
			return false;
		if (Double.compare(mat3x3.m33, m33) != 0)
			return false;
		return Double.compare(mat3x3.determinant, determinant) == 0;

	}

	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */
	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(m11);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m12);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m13);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m21);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m22);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m23);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m31);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m32);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(m33);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(determinant);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Mat3x3 Werte
	 */
	@Override
	public String toString() {
		return "Mat3x3{" + "m11=" + m11 + ", m12=" + m12 + ", m13=" + m13
				+ ", m21=" + m21 + ", m22=" + m22 + ", m23=" + m23 + ", m31="
				+ m31 + ", m32=" + m32 + ", m33=" + m33 + ", determinant="
				+ determinant + '}';
	}
}
