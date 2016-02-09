package geometries;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.TexCoord2;
import material.Material;
import ray.Ray;

/**
 * BH
 * 
 * Die von Geometry abgeleitete Klasse Sphere implementiert die Methode hit entsprechend der Formeln und Algorithmen zur
 * Schnittberechnung.
 */
public class BH extends Geometry {

	/**
	 * 
	 */
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
	public BH(Material material) throws IllegalArgumentException {
		super(material);

		this.center = new Point3(0, 0, 0);
		this.radius = 1;
	}

	/**
	 * Method: hit(ray)
	 *
	 * @param ray
	 *            Ray Objekt
	 * @return Hit / null Bei einem Treffer wird das generierte Hit Objekt zurückgegeben und null vice versa
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

		final double d = (b * b) - (4 * a * c);

		if (d > 0.0001) {
			final double t1 = (-b + Math.sqrt(d)) / (2 * a);
			final double t2 = (-b - Math.sqrt(d)) / (2 * a);
			final double minT = Math.min(t1, t2);

			final double z1 = (ray.origin.sub(center).x + t1 * ray.direction.x);
			final double z2 = (ray.origin.sub(center).x + t2 * ray.direction.x) * -1;

			final Hit h = new Hit(minT, ray, this, createNormalToPoint(ray, minT), texFor(ray.at(minT)));
			if (Math.min(z1, z2) < h.ray.at(minT).z && Math.max(z1, z2) > h.ray.at(minT).z) {
				return h;
			}

		} else if (d == 0.0) {
			final double t = -b / (2 * a);
			n = createNormalToPoint(ray, t);
			final Hit h = new Hit(t, ray, this, n, texFor(ray.at(t)));

			return h;
		}
		return null;
	}

	/**
	 * 
	 * @param point
	 * @return
	 */
	public TexCoord2 texFor(final Point3 point) {
		if (point == null) {
			throw new IllegalArgumentException("The Point cannot be null!");
		}

		final double teta = Math.acos(point.y);
		final double phi = Math.atan2(point.x, point.z);

		return new TexCoord2(phi / (Math.PI * 2), -teta / Math.PI);
	}

	/**
	 * Erzeugt einen Normal3-Vektor zum übergebenen Punkt t auf dem Ray ray
	 * 
	 * @param ray
	 * @param t
	 * @return
	 */
	public Normal3 createNormalToPoint(Ray ray, double t) {
		return ray.at(t).sub(this.center).normalized().asNormal();
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final BH sphere = (BH) o;

		if (Double.compare(sphere.radius, radius) != 0)
			return false;
		return !(center != null ? !center.equals(sphere.center) : sphere.center != null);

	}

	public int hashCode() {
		int result;
		long temp;
		result = center != null ? center.hashCode() : 0;
		temp = Double.doubleToLongBits(radius);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Sphere{" + "center=" + center + ", radius=" + radius + '}';
	}
}