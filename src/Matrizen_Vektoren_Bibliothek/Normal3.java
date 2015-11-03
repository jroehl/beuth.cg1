package Matrizen_Vektoren_Bibliothek;

/**
 * Repräsentiert einen Normalen-Vektor
 * 
 * @author Waschmaschine
 */
public class Normal3 {

	/**
	 * X-Wert eines normalen Vektors
	 */
	final public double x;

	/**
	 * Y-Wert eines normalen Vektors.
	 */
	final public double y;

	/**
	 * Z-Wert eines normalen Vektors.
	 */
	final public double z;

	/**
	 * Konstruktor der Klasse
	 * 
	 * @param x
	 *            setzt den X-Wert des Normalen-Vektors
	 * @param y
	 *            setzt den Y-Wert des Normalen-Vektors
	 * @param z
	 *            setzt den Z-Wert des Normalen-Vektors
	 */
	public Normal3(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Multiplikation mit einem double-Wert
	 * 
	 * @param n
	 *            Wert welcher mit dem Vektor multipliziert werden soll.
	 * @return Neues Normal3-Objekt
	 */
	public Normal3 mul(final double n) {
		return new Normal3(this.x * n, this.y * n, this.z * n);
	}

	/**
	 * Addition mit einem Normalen-Vektor.
	 * 
	 * @param n
	 *            Vektor welcher für die Addition verwendet werden soll
	 * @return Neues Normal3-Objekt
	 * @throws IllegalArgumentException
	 */
	public Normal3 add(final Normal3 n) throws IllegalArgumentException {
		if (n == null) {
			throw new IllegalArgumentException();
		}
		return new Normal3(this.x + n.x, this.y + n.y, this.z + n.z);
	}

	/**
	 * SkalarProdukt mit Normalen-Vektor
	 * 
	 * @param n
	 *            Skalar
	 * @return Skalarprodukt
	 * @throws IllegalArgumentException
	 */
	public double dot(final Vector3 n) throws IllegalArgumentException {
		if (n == null) {
			throw new IllegalArgumentException();
		}
		return this.x * n.x + this.y * n.y + this.z * n.z;
	}

	/**
	 * Ueberschriebene toString-Methode
	 */
	@Override
	public String toString() {
		return "Normal3{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}

	/**
	 * Ueberschriebene equals-Methode
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Normal3 normal3 = (Normal3) o;

		if (Double.compare(normal3.x, x) != 0)
			return false;
		if (Double.compare(normal3.y, y) != 0)
			return false;
		return Double.compare(normal3.z, z) == 0;

	}

	/**
	 * Ueberschriebene hashCode-Methode
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
		return result;
	}
}
