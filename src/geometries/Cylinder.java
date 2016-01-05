package geometries;

import material.Material;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;

/**
 * Sphere
 *
 * @author Waschmaschine
 *         <p>
 *         Die von Geometry abgeleitete Klasse Sphere implementiert die Methode
 *         hit entsprechend der Formeln und Algorithmen zur Schnittberechnung.
 */
public class Cylinder extends Geometry {

	public Normal3 n;

	/**
	 * center - Point3 Objekt der Sphere
	 */

	private final Point3 center;
	/**
	 * radius - double wert der Sphere
	 */
	private final double radius;

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
	public Cylinder(Material material) throws IllegalArgumentException {
		super(material);

		this.center = new Point3(0, 0, 0);
		this.radius = 1;

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
		final double a = (ray.direction.x * ray.direction.x) + (ray.direction.y * ray.direction.y);
		final double b = 2 * ((ray.origin.sub(center).x * ray.direction.x) + (ray.origin.sub(center).y * ray.direction.y));

		final double c = ((ray.origin.sub(center).x) * (ray.origin.sub(center).x))
				+ ((ray.origin.sub(center).y) * (ray.origin.sub(center).y)) - 1;

		// unter der wurzel
		final double d = (b * b) - (4 * a * c);

		if (d > 0.0001) {
			final double t1 = (-b + Math.sqrt(d)) / (2 * a);
			final double t2 = (-b - Math.sqrt(d)) / (2 * a);
			final double minT = Math.min(t1, t2);
			final Hit h = new Hit(minT, ray, this, createNormalToPoint(ray, minT));

			if (h.ray.at(minT).y < 1 && h.ray.at(minT).y < 1) { // Versuch einer
																// Begrenzung...
																// läuft noch
																// nicht
				return h;
			}

		} else if (d == 0.0) {

			final double t = -b / (2 * a);
			n = createNormalToPoint(ray, t);
			final Hit h = new Hit(t, ray, this, n);
			if (h.ray.at(t).y < 1 && h.ray.at(t).y < 1) {
				return h;
			}

		}

		return null;

	}
	public Normal3 createNormalToPoint(Ray ray, double t) {

		final Normal3 normal = ray.at(t).sub(this.center).normalized().asNormal();

		return normal;

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

		final Cylinder sphere = (Cylinder) o;

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
