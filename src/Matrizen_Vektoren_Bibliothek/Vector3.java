package Matrizen_Vektoren_Bibliothek;

/**
 * Vektor
 * 
 * @author Waschmaschine
 */
public class Vector3 {

	public final double x;
	public final double y;
	public final double z;
	public final double magnitude;

	/**
	 * Konstruktor
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.magnitude = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));
	}

	/**
	 * Addition eines Vektors
	 * 
	 * @param v
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector3 add(Vector3 v) throws IllegalArgumentException {
		if (v == null) {
			throw new IllegalArgumentException();
		}
		return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
	}

	/**
	 * Addition einer Normalen
	 * 
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector3 add(Normal3 n) throws IllegalArgumentException {
		if (n == null) {
			throw new IllegalArgumentException();
		}
		return new Vector3(this.x + n.x, this.y + n.y, this.z + n.z);
	}

	/**
	 * Subtraktion einer Normalen
	 * 
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector3 sub(Normal3 n) throws IllegalArgumentException {

		if (n == null) {
			throw new IllegalArgumentException();
		}

		return new Vector3(this.x - n.x, this.y - n.y, this.z - n.z);

	}

	/**
	 * Multiplikation mit double-Wert
	 * 
	 * @param c
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector3 mul(double c) throws IllegalArgumentException {

		return new Vector3(this.x * c, this.y * c, this.z * c);

	}

	/**
	 * SkalarProdukt eines Vektors
	 * 
	 * @param v
	 * @return
	 * @throws IllegalArgumentException
	 */
	public double dot(Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return this.x * v.x + this.y * v.y + this.z * v.z;

	}

	/**
	 * SkalarProdukt einer Normalen
	 * 
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */
	public double dot(Normal3 n) throws IllegalArgumentException {

		if (n == null) {
			throw new IllegalArgumentException();
		}

		return this.x * n.x + this.y * n.y + this.z * n.z;

	}

	/**
	 * Vektor normalisieren
	 * 
	 * @return
	 */
	public Vector3 normalized() {

		return new Vector3(this.x / this.magnitude, this.y / this.magnitude, this.z / this.magnitude);

	}

	/**
	 * Den übergebenen Vektor als Normale (aber nicht normalisiert) wiedergeben
	 * 
	 * @return
	 */
	public Normal3 asNormal() {

		return new Normal3(this.x, this.y, this.z);
		// "In keinem Fall sollte die Länge der Normalen normalisiert werden, da
		// ansonsten spätere Berechnungen nicht mehr funktionieren."

	}

	/**
	 * Reflektion eines vektors an einer Normalen
	 * 
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector3 reflectedOn(Normal3 n) throws IllegalArgumentException {

		if (n == null) {
			throw new IllegalArgumentException();
		}

		return (this.mul((double) -1)).add(n.mul((double) 2).mul(n.dot(this)));

	}

	/**
	 * Kreuzprodukt (Vektor X Vektor)
	 * 
	 * @param v
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector3 x(Vector3 v) throws IllegalArgumentException {

		if (v == null) {
			throw new IllegalArgumentException();
		}

		return new Vector3(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);

	}

	/**
	 * überschriebene equals-Methode
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Vector3 vector3 = (Vector3) o;

		if (Double.compare(vector3.x, x) != 0)
			return false;
		if (Double.compare(vector3.y, y) != 0)
			return false;
		if (Double.compare(vector3.z, z) != 0)
			return false;
		return Double.compare(vector3.magnitude, magnitude) == 0;

	}

	/**
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
		temp = Double.doubleToLongBits(magnitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * überschriebene toString-Methode
	 */
	public String toString() {
		return "Vector3{" + "x=" + x + ", y=" + y + ", z=" + z + ", magnitude=" + magnitude + '}';
	}
}