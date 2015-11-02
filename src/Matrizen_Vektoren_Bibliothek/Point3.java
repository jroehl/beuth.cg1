package Matrizen_Vektoren_Bibliothek;

/**
 * Created by jroehl on 13.10.15.
 */
public class Point3 {

	public final double x;
	public final double y;
	public final double z;

	public Point3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * ________________________________________________________________________________
	 * Subtraktion eines Punktes
	 */

	public Vector3 sub(Point3 p) throws IllegalArgumentException {

		if (p == null) {
			throw new IllegalArgumentException();
		}

		return new Vector3(this.x - p.x, this.y - p.y, this.z - p.z);
	}

	/**
	 * ________________________________________________________________________________
	 * Subtraktion eines Vektors
	 */

	public Point3 sub(Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return new Point3(this.x - v.x, this.y - v.y, this.z - v.z);

	}

	/**
	 * ________________________________________________________________________________
	 * Addition eines Vektors
	 */

	public Point3 add(Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return new Point3(this.x + v.x, this.y + v.y, this.z + v.z);

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

		Point3 point3 = (Point3) o;

		if (Double.compare(point3.x, x) != 0)
			return false;
		if (Double.compare(point3.y, y) != 0)
			return false;
		return Double.compare(point3.z, z) == 0;

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

	/**
	 * ________________________________________________________________________________
	 * überschriebene toString-Methode
	 */

	@Override
	public String toString() {
		return "Point3{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
