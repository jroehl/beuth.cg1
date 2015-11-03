package Matrizen_Vektoren_Bibliothek;

/**
 * Normalen-Vektor
 * 
 * @author Waschmaschine
 */
public class Normal3 {

	final public double x;
	final public double y;
	final public double z;

	/**
	 * Konstruktor
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Normal3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Multiplikation mit double-Wert
	 * 
	 * @param n
	 * @return
	 */
	public Normal3 mul(double n) {
		return new Normal3(this.x * n, this.y * n, this.z * n);
	}

	/**
	 * Addition einer Normalen
	 * 
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Normal3 add(Normal3 n) throws IllegalArgumentException {
		if (n == null) {
			throw new IllegalArgumentException();
		}
		return new Normal3(this.x + n.x, this.y + n.y, this.z + n.z);
	}

	/**
	 * Addition eines Vektors
	 * 
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */
	public double dot(Vector3 n) throws IllegalArgumentException {
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
	public boolean equals(Object o) {
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