package geometries;

import material.Material;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Mat3x3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Triangle
 *
 * @author Waschmaschine
 *         <p>
 *         Die von Geometry abgeleitete Klasse Triangle implementiert die
 *         Methode hit entsprechend der Formeln und Algorithmen zur
 *         Schnittberechnung.
 */
public class Triangle extends Geometry {

	/**
	 * a - Point3 Objekt des Triangle
	 */
	private final Point3 a;
	/**
	 * b - Point3 Objekt des Triangle
	 */
	private final Point3 b;
	/**
	 * c - Point3 Objekt des Triangle
	 */
	private final Point3 c;

	/**
	 * Konstruktor: Triangle
	 *
	 * @param color
	 *            color Objekt der Geometrie
	 * @param a
	 *            Point3 Objekt des Triangle
	 * @param b
	 *            Point3 Objekt des Triangle
	 * @param c
	 *            Point3 Objekt des Triangle
	 * @throws IllegalArgumentException
	 */
	public Triangle(Material material, Point3 a, Point3 b, Point3 c)
			throws IllegalArgumentException {
		super(material);

		if (a == null) {
			throw new IllegalArgumentException("The a cannot be null!");
		}
		if (b == null) {
			throw new IllegalArgumentException("The b cannot be null!");
		}
		if (c == null) {
			throw new IllegalArgumentException("The c cannot be null!");
		}

		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Method: hit(ray)
	 *
	 * @param ray
	 *            Ray Objekt
	 * @return Hit / null Bei einem Treffer wird das generierte Hit Objekt
	 *         zurückgegeben und null vice versa
	 * @throws IllegalArgumentException
	 */
	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {

		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		final Mat3x3 matA = new Mat3x3(a.x - b.x, a.x - c.x, ray.direction.x,
				a.y - b.y, a.y - c.y, ray.direction.y, a.z - b.z, a.z - c.z,
				ray.direction.z);

		final Vector3 vec = a.sub(ray.origin);

		final double beta = matA.changeCol1(vec).determinant / matA.determinant;
		final double gamma = matA.changeCol2(vec).determinant
				/ matA.determinant;

		final double t = matA.changeCol3(vec).determinant / matA.determinant;

		if ((beta > 0 && gamma > 0) && (beta + gamma) <= 1) {
			return new Hit(t, ray, this);
		}

		return null;

	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param o
	 *            Objekt das mit der Matrix verglichen wird
	 * @return true | false
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Triangle triangle = (Triangle) o;

		if (a != null ? !a.equals(triangle.a) : triangle.a != null)
			return false;
		if (b != null ? !b.equals(triangle.b) : triangle.b != null)
			return false;
		return !(c != null ? !c.equals(triangle.c) : triangle.c != null);

	}

	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */
	@Override
	public int hashCode() {
		int result = a != null ? a.hashCode() : 0;
		result = 31 * result + (b != null ? b.hashCode() : 0);
		result = 31 * result + (c != null ? c.hashCode() : 0);
		return result;
	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Geometry Werte
	 */
	@Override
	public String toString() {
		return "Triangle{" + "a=" + a + ", b=" + b + ", c=" + c + '}';
	}
}
