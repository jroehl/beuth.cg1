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
	 * @param material
	 *            Material des Objekts (enthält Textur, welche
	 *            Color-Informationen enthält)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Sphere other = (Sphere) obj;
		if (center == null) {
			if (other.center != null)
				return false;
		} else if (!center.equals(other.center))
			return false;
		if (Double.doubleToLongBits(count) != Double.doubleToLongBits(other.count))
			return false;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		if (up != other.up)
			return false;
		return true;
	}

	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((center == null) ? 0 : center.hashCode());
		long temp;
		temp = Double.doubleToLongBits(count);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((n == null) ? 0 : n.hashCode());
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (up ? 1231 : 1237);
		return result;
	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Sphere Werte
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sphere [n=" + n + ", center=" + center + ", radius=" + radius + ", count=" + count + ", up=" + up + "]";
	}

}
