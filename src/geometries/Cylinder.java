package geometries;

import java.util.ArrayList;

import material.Material;
import ray.Ray;
import ray.Transform;
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

	private Double t3;

	private Double t4;

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
		final double a = (ray.direction.x * ray.direction.x) + (ray.direction.z * ray.direction.z);
		final double b = (2 * (ray.origin.sub(center).x * ray.direction.x) + (2 * (ray.origin.sub(center).z * ray.direction.z)));

		final double c = ((ray.origin.sub(center).x) * (ray.origin.sub(center).x))
				+ ((ray.origin.sub(center).z) * (ray.origin.sub(center).z)) - 1;

		// unter der wurzel
		final double d = (b * b) - (4 * a * c);

		final ArrayList<Geometry> geos = new ArrayList<Geometry>();
		geos.add(new Disc(material));
		final Node top = new Node(new Transform().translate(new Point3(0, -2, 0)), geos);
		final Node bottom = new Node(new Transform().translate(new Point3(0, 2, 0)).rotateX(Math.PI), geos);

		final Hit hitTop = top.hit(ray);
		final Hit bottomHit = bottom.hit(ray);

		// funktioniert noch nicht - es werden entweder nur der cylinder oder
		// nur die beiden discs getroffen

		if (d > 0.0001) {

			if (hitTop != null) {
				t3 = hitTop.t;

			}

			if (bottomHit != null) {
				t4 = bottomHit.t;
			}

			if (hitTop == null) {
				t3 = Double.MAX_VALUE;
			}

			if (bottomHit == null) {
				t4 = Double.MAX_VALUE;
			}

			final double t1 = (-b + Math.sqrt(d)) / (2 * a);
			final double t2 = (-b - Math.sqrt(d)) / (2 * a);

			final double minT1 = Math.min(t1, t2);
			final double minT2 = Math.min(t3, t4);
			final double minT3 = Math.min(minT1, minT2);

			System.out.println(minT1 + "    " + minT2 + "    " + minT3);

			final Hit h = new Hit(minT3, ray, this, createNormalToPoint(ray, minT3), texFor(ray.at(minT3)));

			final Point3 p = h.ray.at(minT3);
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

		final double teta = Math.acos(point.y);
		final double phi = Math.atan2(point.x, point.z);

		return new TexCoord2(phi / (Math.PI * 2), -teta / Math.PI);
	}

	public Normal3 createNormalToPoint(Ray ray, double t) {

		final Normal3 normal = ray.at(t).sub(this.center).normalized().asNormal();
		// final Normal3 normal = new Normal3(ray.at(t).x, 0, ray.at(t).z);
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
