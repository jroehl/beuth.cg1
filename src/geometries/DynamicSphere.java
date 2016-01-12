package geometries;

import material.Material;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.TexCoord2;

/**
 * Sphere
 *
 * @author Waschmaschine
 *         <p>
 *         Die von Geometry abgeleitete Klasse Sphere implementiert die Methode
 *         hit entsprechend der Formeln und Algorithmen zur Schnittberechnung.
 */
public class DynamicSphere extends Geometry {

	public Normal3 n;

	/**
	 * center - Point3 Objekt der Sphere
	 */

	private final Point3 center;
	/**
	 * radius - double wert der Sphere
	 */
	private double radius;

	private double count = 0.0;
	private final boolean up = true;

	/**
	 * Konstruktor: Sphere
	 *
	 * @param color
	 *            color Objekt der Geometrie
	 * @param center
	 *            Point3 Objekt der Ebene
	 * @param radius
	 *            double wert der Sphere
	 * @throws IllegalArgumentException
	 */
	public DynamicSphere(Material material) throws IllegalArgumentException {
		super(material);

		this.center = new Point3(0, 0, 0);
		this.radius = 0.0;

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

		final double a = ray.direction.dot(ray.direction);
		// a = a + count;
		final double b = ray.direction.dot(ray.origin.sub(center).mul(2 - Math.pow((count() / 3500000), 4)));

		radius = radius + Math.pow((count() / 535000000), 1.172005); // +
		// Math.pow((count()
		// /
		// 50000),1.8)

		// System.out.println(b);
		final double c = (ray.origin.sub(center).dot(ray.origin.sub(center))) - (this.radius * this.radius);

		// unter der wurzel
		final double d = (b * b) - (4 * a * c);

		if (d > 0.0001) {

			final double t1 = (-b + Math.sqrt(d)) / (2 * a);
			final double t2 = (-b - Math.sqrt(d)) / (2 * a);
			final double minT = Math.min(t1, t2);

			return new Hit(minT, ray, this, createNormalToPoint(ray, minT), texFor(ray.at(minT)));

		} else if (d == 0.0) {

			final double t = -b / (2 * a);
			n = createNormalToPoint(ray, t);
			return new Hit(t, ray, this, n, texFor(ray.at(t)));

		}

		return null;

	}
	public Normal3 createNormalToPoint(Ray ray, double t) {

		final Normal3 normal = ray.at(t).sub(this.center).normalized().asNormal();
		// final Normal3 normalNew = new Normal3(normal.x * Math.pow((count() /
		// 50000), 1.8), normal.y * Math.pow((count() / 50000), 1.8),
		// normal.z * Math.pow((count() / 500000), 1.8));
		return normal;

	}

	public TexCoord2 texFor(final Point3 point) {
		if (point == null) {
			throw new IllegalArgumentException("The Point cannot be null!");
		}

		final double teta = Math.acos(point.y);
		final double phi = Math.atan2(point.x, point.z);

		return new TexCoord2(phi / (Math.PI * 2), -teta / Math.PI);
	}

	// public double count() {
	// if (count <= 8190.0 && up == true) {
	// count = count + 00000000.1;
	//
	// }
	//
	// if (count >= 8190.0) {
	// up = false;
	// }
	//
	// if (count >= 0.0 && up == false) {
	// count = count - 00000000.1;
	//
	// }
	//
	// if (count <= 0.0) {
	// up = true;
	// }
	//
	// return count;
	// }
	public double count() {
		if (count <= Double.MAX_VALUE && up == true) {
			count = count + 0000000000000000.1;

		}

		// if (count >= 8190.0) {
		// up = false;
		// }
		//
		// if (count >= 0.0 && up == false) {
		// count = count - 00000000.1;
		//
		// }
		//
		// if (count <= 0.0) {
		// up = true;
		// }

		return count;
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

		final DynamicSphere sphere = (DynamicSphere) o;

		if (Double.compare(sphere.radius, radius) != 0)
			return false;
		return !(center != null ? !center.equals(sphere.center) : sphere.center != null);

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
		result = center != null ? center.hashCode() : 0;
		temp = Double.doubleToLongBits(radius);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Sphere Werte
	 */
	@Override
	public String toString() {
		return "Sphere{" + "center=" + center + ", radius=" + radius + '}';
	}
}
