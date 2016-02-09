package geometries;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.TexCoord2;
import material.Material;
import ray.Ray;

/**
 * Disc - Geometry.
 *
 * Die von Geometry abgeleitete Klasse Plane implementiert die Methode hit entsprechend der Formeln und Algorithmen zur
 * Schnittberechnung.
 */
public class Disc extends Geometry {

	/**
	 * a - Point3 Objekt der Ebene
	 */
	public final Point3 a;

	/**
	 * n - Normal3 Objekt der Ebene
	 */
	public final Normal3 n;

	/**
	 * Konstruktor: Disc
	 *
	 * @param color
	 *            color Objekt der Geometrie
	 * @param a
	 *            Point3 Objekt der Ebene
	 * @param n
	 *            Normal3 Objekt der Ebene
	 * @throws IllegalArgumentException
	 */
	public Disc(Material material) throws IllegalArgumentException {
		super(material);

		this.a = new Point3(0, 0, 0);
		this.n = new Normal3(0, 1, 0);
	}

	public Disc(Material material, Point3 p, Normal3 n) throws IllegalArgumentException {
		super(material);

		this.a = p;
		this.n = n;
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

		final double nenner = ray.direction.dot(n);

		if (nenner != 0.0) {
			final double t = n.dot(a.sub(ray.origin)) / nenner;

			if (t > 0.0001) {

				final Point3 p = ray.at(t);

				if (Math.pow(p.sub(new Point3(0, 0, 0)).magnitude, 2) < 1) {
					return new Hit(t, ray, this, this.n, new TexCoord2(p.x, -p.z));
				}
			}
		}
		return null;
	}
}