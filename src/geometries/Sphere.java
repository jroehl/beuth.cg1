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
public class Sphere extends Geometry {

	public Normal3 n;

	/**
	 * center - Point3 Objekt der Sphere
	 */

	private final Point3 center;
	/**
	 * radius - double wert der Sphere
	 */
	private final double radius;

	private final double count = 0.0;
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
	public Sphere(Material material) throws IllegalArgumentException {
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

		final double a = ray.direction.dot(ray.direction);

		final double b = ray.direction.dot(ray.origin.sub(center).mul(2));

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

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param o
	 *            Objekt das mit der Matrix verglichen wird
	 * @return true | false
	 */

	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Sphere Werte
	 */

}
