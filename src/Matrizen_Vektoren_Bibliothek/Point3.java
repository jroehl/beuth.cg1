package Matrizen_Vektoren_Bibliothek;

/**
 * Punkt
 * 
 * @author Waschmaschine
 */
public class Point3 {

	/**
	 * x-Wert des Punktes
	 */
	public final double x;

	/**
	 * y-Wert des Punktes
	 */
	public final double y;

	/**
	 * z-Wert des Punktes
	 */
	public final double z;

	/**
	 * Konstruktor.
	 * 
	 * @param x
	 * 		setzt den double x-Wert
	 * @param y
	 * 		setzt den double y-Wert
	 * @param z
	 * 		setzt den double z-Wert
	 */
	public Point3(final double x,final double y,final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Subtraktion eines Punktes
	 * 
	 * @param p
	 * 		Punkt der von dem Punkt subtrahiert wird
	 * @return
	 * 		neues Vector3 Objekt (Vektor)
	 * @throws IllegalArgumentException
	 */
	public Vector3 sub(final Point3 p) throws IllegalArgumentException {

		if (p == null) {
			throw new IllegalArgumentException();
		}

		return new Vector3(this.x - p.x, this.y - p.y, this.z - p.z);
	}

	/**
	 * Subtraktion eines Vektors
	 * 
	 * @param v
	 * 		Vektor der von dem Punkt subtrahiert wird
	 * @return
	 * 		neues Point3 Objekt (Punkt)
	 * @throws IllegalArgumentException
	 */
	public Point3 sub(final Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return new Point3(this.x - v.x, this.y - v.y, this.z - v.z);

	}

	/**
	 * Addition eines Vektors
	 * 
	 * @param v
	 * 		Vektor der zu dem Punkt addiert wird
	 * @return
	 * 		neues Point3 Objekt (Punkt)
	 * @throws IllegalArgumentException
	 */
	public Point3 add(final Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return new Point3(this.x + v.x, this.y + v.y, this.z + v.z);

	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param o
	 * 		Objekt das mit dem Punkt verglichen wird
	 * @return
	 * 		true | false
	 *
	 */
	@Override
	public boolean equals(final Object o) {
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
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return
	 * 		int hashcode
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
	 * Ueberschriebene toString-Methode
	 *
	 * @return
	 * 		String Point3 Werte
	 */
	@Override
	public String toString() {
		return "Point3{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}