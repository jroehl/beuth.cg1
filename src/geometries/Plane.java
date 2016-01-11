package geometries;

import material.Material;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.TexCoord2;

/**
 * Plane
 *
 * @author Waschmaschine
 *         <p>
 *         Die von Geometry abgeleitete Klasse Plane implementiert die Methode
 *         hit entsprechend der Formeln und Algorithmen zur Schnittberechnung.
 */
public class Plane extends Geometry {

	/**
	 * a - Point3 Objekt der Ebene
	 */
	public final Point3 a;
	/**
	 * n - Normal3 Objekt der Ebene
	 */
	public final Normal3 n;

	/**
	 * Konstruktor: Plane
	 *
	 * @param material
	 *            Material des Objekts (enthält Textur, welche
	 *            Color-Informationen enthält)
	 * @throws IllegalArgumentException
	 */
	public Plane(Material material) throws IllegalArgumentException {
		super(material);

		this.a = new Point3(0, 0, 0);
		this.n = new Normal3(0, 1, 0);

	}

	public Plane(Material material, Point3 p, Normal3 n) throws IllegalArgumentException {
		super(material);

		this.a = p;
		this.n = n;

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

		// denonimator / nenner
		final double nenner = ray.direction.dot(n);

		if (nenner != 0.0) {
			final double t = n.dot(a.sub(ray.origin)) / nenner;

			if (t > 0.0001) {

				final Point3 p = ray.at(t);

				return new Hit(t, ray, this, this.n, new TexCoord2(p.x, -p.z));

			}
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

		final Plane plane = (Plane) o;

		if (a != null ? !a.equals(plane.a) : plane.a != null)
			return false;
		return !(n != null ? !n.equals(plane.n) : plane.n != null);

	}

	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */
	@Override
	public int hashCode() {
		int result = a != null ? a.hashCode() : 0;
		result = 31 * result + (n != null ? n.hashCode() : 0);
		return result;
	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Plane Werte
	 */
	@Override
	public String toString() {
		return "Plane{" + "a=" + a + ", n=" + n + '}';
	}
}
