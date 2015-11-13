package Matrizen_Vektoren_Bibliothek;

/**
 * Vektor
 *
 * @author Waschmaschine
 */
public class Vector3 {

	/**
	 * x-Wert des Vektors
	 */
	public final double x;

	/**
	 * y-Wert des Vektors
	 */
	public final double y;

	/**
	 * z-Wert des Vektors
	 */
	public final double z;

	/**
	 * magnitude (Betrag) des Vektors
	 */
	public final double magnitude;

	/**
	 * Konstruktor
	 *
	 * @param x
	 *            setzt den double x-Wert
	 * @param y
	 *            setzt den double y-Wert
	 * @param z
	 *            setzt den double z-Wert
	 */
	public Vector3(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.magnitude = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)
				+ Math.pow(z, 2)));
	}

	/**
	 * Addition eines Vektors
	 *
	 * @param v
	 *            Vektor der zu dem Vektor addiert wird
	 * @return neues Vector3 Objekt (Vektor)
	 * @throws IllegalArgumentException
	 */
	public Vector3 add(final Vector3 v) throws IllegalArgumentException {
		if (v == null) {
			throw new IllegalArgumentException();
		}
		return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
	}

	/**
	 * Addition einer Normalen
	 *
	 * @param n
	 *            Normale die zu dem Vektor addiert wird
	 * @return neues Vector3 Objekt (Vektor)
	 * @throws IllegalArgumentException
	 */
	public Vector3 add(final Normal3 n) throws IllegalArgumentException {
		if (n == null) {
			throw new IllegalArgumentException();
		}
		return new Vector3(this.x + n.x, this.y + n.y, this.z + n.z);
	}

	/**
	 * Subtraktion einer Normalen
	 *
	 * @param n
	 *            Normale die von dem Vektor subtrahiert wird
	 * @return neues Vector3 Objekt (Vektor)
	 * @throws IllegalArgumentException
	 */
	public Vector3 sub(final Normal3 n) throws IllegalArgumentException {

		if (n == null) {
			throw new IllegalArgumentException();
		}

		return new Vector3(this.x - n.x, this.y - n.y, this.z - n.z);

	}

	/**
	 * Multiplikation mit double-Wert
	 *
	 * @param c
	 *            Double-Wert der mit dem Vektor multipliziert wird
	 * @return neues Vector3 Objekt (Vektor)
	 * @throws IllegalArgumentException
	 */
	public Vector3 mul(final double c) throws IllegalArgumentException {

		return new Vector3(this.x * c, this.y * c, this.z * c);

	}

	/**
	 * SkalarProdukt eines Vektors
	 *
	 * @param v
	 *            Vektor der mit dem Vektor das Skalarprodukt bildet
	 * @return Double Wert (Skalarprodukt)
	 * @throws IllegalArgumentException
	 */
	public double dot(final Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return this.x * v.x + this.y * v.y + this.z * v.z;

	}

	/**
	 * SkalarProdukt einer Normalen
	 *
	 * @param n
	 *            Normale die mit dem Vektor das Skalarprodukt bildet
	 * @return Double Wert (Skalarprodukt)
	 * @throws IllegalArgumentException
	 */
	public double dot(final Normal3 n) throws IllegalArgumentException {

		if (n == null) {
			throw new IllegalArgumentException();
		}

		return this.x * n.x + this.y * n.y + this.z * n.z;

	}

	/**
	 * Vektor normalisieren
	 *
	 * @return neues Vector3 Objekt (normalisierter Vektor)
	 */
	public Vector3 normalized() {

		return new Vector3(this.x / this.magnitude, this.y / this.magnitude,
				this.z / this.magnitude);

	}

	/**
	 * Vektor als Normale (aber nicht normalisiert) wiedergeben
	 *
	 * @return neues Normal3 Objekt (Vektor als Normale)
	 */
	public Normal3 asNormal() {

		return new Normal3(this.x, this.y, this.z);
		// "In keinem Fall sollte die Länge der Normalen normalisiert werden, da
		// ansonsten spätere Berechnungen nicht mehr funktionieren."

	}

	/**
	 * Reflektion eines Vektors an einer Normalen
	 *
	 * @param n
	 *            Normale die an dem Vektor reflektiert wird
	 * @return neues Vector3 Objekt (reflektierter Vektor an Normale)
	 * @throws IllegalArgumentException
	 */
	public Vector3 reflectedOn(final Normal3 n) throws IllegalArgumentException {

		if (n == null) {
			throw new IllegalArgumentException();
		}

		return (this.mul(-1)).add(n.mul(2).mul(n.dot(this)));

	}

	/**
	 * Kreuzprodukt (Vektor X Vektor)
	 *
	 * @param v
	 *            Vektor der mit dem Vektor das Kreuzprodukt bildet
	 * @return neues Vector3 Objekt (Vektor Kreuzprodukt mit Vektor)
	 * @throws IllegalArgumentException
	 */
	public Vector3 x(final Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return new Vector3(this.y * v.z - this.z * v.y, this.z * v.x - this.x
				* v.z, this.x * v.y - this.y * v.x);

	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param o
	 *            Objekt das mit dem Punkt verglichen wird
	 * @return true | false
	 *
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Vector3 vector3 = (Vector3) o;

		if (Double.compare(vector3.x, x) != 0)
			return false;
		if (Double.compare(vector3.y, y) != 0)
			return false;
		if (Double.compare(vector3.z, z) != 0)
			return false;
		return Double.compare(vector3.magnitude, magnitude) == 0;

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
		temp = Double.doubleToLongBits(x);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(magnitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Vector3 Werte
	 */
	@Override
	public String toString() {
		return "Vector3{" + "x=" + x + ", y=" + y + ", z=" + z + ", magnitude="
				+ magnitude + '}';
	}
}
