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
public class CylinderBody extends Geometry {

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
	public CylinderBody(Material material) throws IllegalArgumentException {
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
		final double t = Double.MAX_VALUE;
		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}
		final double a = (ray.direction.x * ray.direction.x) + (ray.direction.z * ray.direction.z);
		final double b = (2 * (ray.origin.sub(center).x * ray.direction.x) + (2 * (ray.origin.sub(center).z * ray.direction.z)));

		final double c = ((ray.origin.sub(center).x) * (ray.origin.sub(center).x))
				+ ((ray.origin.sub(center).z) * (ray.origin.sub(center).z)) - 1;

		// unter der wurzel
		final double d = (b * b) - (4 * a * c);

		if (d > 0.0001) {

			final double t1 = (-b + Math.sqrt(d)) / (2 * a);
			final double t2 = (-b - Math.sqrt(d)) / (2 * a);

			final double minT1 = Math.min(t1, t2);

			final Hit h = new Hit(minT1, ray, this, createNormalToPoint(ray, minT1), texFor(ray.at(minT1)));

			final Point3 p = h.ray.at(minT1);
			if (p.y <= 2 && p.y >= -2) {

				return h;
			}

		}
		return null;

	}
	public TexCoord2 texFor(final Point3 point) {
		if (point == null) {
			throw new IllegalArgumentException("The Point cannot be null!");
		}

		// final double teta = Math.acos(point.y);
		final double phi = Math.atan2(point.x, point.z);

		// return new TexCoord2(phi / (Math.PI * 2), -teta / Math.PI);
		return new TexCoord2(phi / (Math.PI * 2), (point.y + 1) / 2);
	}

	public Normal3 createNormalToPoint(Ray ray, double t) {

		final Normal3 normal = new Normal3(ray.at(t).x, 0, ray.at(t).z);
		return normal;

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
