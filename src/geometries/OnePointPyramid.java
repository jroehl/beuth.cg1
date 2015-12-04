package geometries;

import material.Material;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;

/**
 * Idee: ein Mittelpunkt von dem aus ein Strahl nach unten und drei Strahlen mit
 * jeweils gleichem winkel zuieinander in jede richtung nach schräg oben
 * geschickt werden... AxisAlignedBox
 *
 * Problem: wie kann man Abfragen, ob der Hit innerhalb der Pyramidenfläche
 * liegt?
 *
 * @author Waschmaschine
 *         <p>
 *         Die von Geometry abgeleitete Klasse AxisAlignedBox implementiert die
 *         Methode hit entsprechend der Formeln und Algorithmen zur
 *         Schnittberechnung.
 */
public class OnePointPyramid extends Geometry {

	/**
	 * LeftBottomFar - linker unterer entfernter Punkt
	 */
	private final Point3 middle;

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
	public OnePointPyramid(Material material, Point3 middle) throws IllegalArgumentException {
		super(material);

		if (middle == null) {
			throw new IllegalArgumentException("The lbf cannot be null!");
		}

		this.middle = middle;
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
		final Point3 bottomPoint = new Point3(middle.x, middle.y - 3, middle.z);
		final Plane bottom = new Plane(material, bottomPoint, bottomPoint.sub(middle).mul(-1).asNormal());
		final Point3 vrPoint = new Point3(middle.x + 2, middle.y + 2, middle.z + 2);
		final Plane vr = new Plane(material, vrPoint, vrPoint.sub(middle).mul(-1).asNormal());
		final Point3 vlPoint = new Point3(middle.x - 2, middle.y + 2, middle.z + 2);
		final Plane vl = new Plane(material, vlPoint, vlPoint.sub(middle).mul(-1).asNormal());
		final Point3 backPoint = new Point3(middle.x, middle.y + 2, middle.z + 2);
		final Plane back = new Plane(material, backPoint, backPoint.sub(middle).mul(-1).asNormal());

		final Plane[] planes = {bottom, vr, vl, back};

		double tf = -1;
		Normal3 nf = null;

		for (final Plane plane : planes) {

			if (ray.origin.sub(plane.a).dot(plane.n) > 0) {
				// denonimator / nenner
				final double d = ray.direction.dot(plane.n);

				if (d != 0) {
					final double t = plane.a.sub(ray.origin).dot(plane.n) / d;

					if (t > tf) {
						nf = plane.n;
						tf = t;
					}
				}
			}
		}

		final Hit hit = new Hit(tf, ray, this, nf);
		final Point3 p = hit.ray.at(hit.t);
		final double eps = 0.00001;

		// if ((lbf.x <= p.x + eps && p.x <= run.x + eps) && (lbf.y <= p.y + eps
		// && p.y <= run.y + eps)
		// && (lbf.z <= p.z + eps && p.z <= run.z + eps)) {
		// return hit;
		// }

		return null;

	}

}