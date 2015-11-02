package Matrizen_Vektoren_Bibliothek;

/**
 * Created by jroehl on 13.10.15.
 */
public class Normal3 {

	public final double x;
	public final double y;
	public final double z;

	public Normal3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * ________________________________________________________________________________
	 * Multiplikation mit double-Wert
	 */

	public Normal3 mul(double n) {
		return new Normal3(this.x * n, this.y * n, this.z * n);
	}

	/**
	 * ________________________________________________________________________________
	 * Addition einer Normalen
	 */

	public Normal3 add(Normal3 n) throws IllegalArgumentException {
		if (n == null) {
			throw new IllegalArgumentException();
		}
		return new Normal3(this.x + n.x, this.y + n.y, this.z + n.z);
	}

	/**
	 * ________________________________________________________________________________
	 * Addition eines Vektors
	 */

	public double dot(Vector3 n) throws IllegalArgumentException {
		if (n == null) {
			throw new IllegalArgumentException();
		}
		return this.x * n.x + this.y * n.y + this.z * n.z;
	}

	/**
	 * ________________________________________________________________________________
	 * überschriebene toString-Methode
	 */

	@Override
	public String toString() {
		return "Normal3{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}

	/**
	 * ________________________________________________________________________________
	 * überschriebene equals-Methode
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
	 * ________________________________________________________________________________
	 * überschriebene hashCode-Methode
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
