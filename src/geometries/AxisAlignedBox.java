package geometries;

import material.Material;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;

/**
 * AxisAlignedBox
 *
 * @author Waschmaschine
 *         <p>
 *         Die von Geometry abgeleitete Klasse AxisAlignedBox implementiert die
 *         Methode hit entsprechend der Formeln und Algorithmen zur
 *         Schnittberechnung.
 */
public class AxisAlignedBox extends Geometry {

	/**
	 * LeftBottomFar - linker unterer entfernter Punkt
	 */
	private final Point3 lbf;
	/**
	 * RightUpperNarrow - rechter oberer näherer Punkt
	 */
	private final Point3 run;

	/**
	 * Konstruktor: AxisAlignedBox
	 *
	 * @param color
	 *            RGB Color der Geometrie
	 * @param lbf
	 *            linker unterer entfernter Punkt
	 * @param run
	 *            rechter oberer näherer Punkt
	 *
	 * @throws IllegalArgumentException
	 */
	public AxisAlignedBox(Material material) throws IllegalArgumentException {
		super(material);

		this.lbf = new Point3(-0.5, -0.5, -0.5);
		this.run = new Point3(0.5, 0.5, 0.5);
	}

	/**
	 * Method: hit(ray)
	 *
	 * @param ray
	 *            Ray Objekt
	 * @return Hit / null Bei einem Treffer wird das generierte Hit Objekt
	 *         zurückgegeben und null vice versa
	 *
	 * @throws IllegalArgumentException
	 */
	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {

		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		final Plane b1 = new Plane(material, lbf, new Normal3(-1, 0, 0));
		final Plane b2 = new Plane(material, lbf, new Normal3(0, -1, 0));
		final Plane b3 = new Plane(material, lbf, new Normal3(0, 0, -1));

		final Plane f1 = new Plane(material, run, new Normal3(1, 0, 0));
		final Plane f2 = new Plane(material, run, new Normal3(0, 1, 0));
		final Plane f3 = new Plane(material, run, new Normal3(0, 0, 1));

		final Plane[] planes = {b1, b2, b3, f1, f2, f3};

		// double tf = -1;
		// Normal3 nf = null;

		Hit hit = null;

		for (final Plane plane : planes) {

			if (ray.origin.sub(plane.a).normalized().dot(plane.n) > 0) {
				// denonimator / nenner
				final double d = ray.direction.dot(plane.n);

				if (d != 0) {
					final double t = plane.a.sub(ray.origin).dot(plane.n) / d;

					if (hit == null || t > hit.t) {
						hit = new Hit(t, ray, this, plane.n);
					}
				}
			}

			if (hit != null) {
				final Point3 p = hit.ray.at(hit.t);
				final double eps = 0.0001;

				if ((lbf.x <= p.x + eps && p.x <= run.x + eps) && (lbf.y <= p.y + eps && p.y <= run.y + eps)
						&& (lbf.z <= p.z + eps && p.z <= run.z + eps)) {

					return plane.hit(ray);
				}
			}
		}
		return null;

	}
	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String AxisAlignedBox Werte
	 */
	@Override
	public String toString() {
		return "AxisAlignedBox{" + "lbf=" + lbf + ", run=" + run + '}';
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
		if (!super.equals(o))
			return false;

		final AxisAlignedBox that = (AxisAlignedBox) o;

		if (lbf != null ? !lbf.equals(that.lbf) : that.lbf != null)
			return false;
		return !(run != null ? !run.equals(that.run) : that.run != null);

	}

	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (lbf != null ? lbf.hashCode() : 0);
		result = 31 * result + (run != null ? run.hashCode() : 0);
		return result;
	}
}