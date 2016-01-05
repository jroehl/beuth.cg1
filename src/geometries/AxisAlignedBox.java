package geometries;

import java.util.ArrayList;
import java.util.HashSet;

import material.Material;
import ray.Ray;
import ray.Transform;
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
	private final Point3 lbf = new Point3(-0.5, -0.5, -0.5);
	/**
	 * RightUpperNarrow - rechter oberer näherer Punkt
	 */
	private final Point3 run = new Point3(0.5, 0.5, 0.5);

	private final Node left;
	private final Node right;

	private final Node top;
	private final Node bottom;

	private final Node front;
	private final Node far;

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

		final ArrayList<Geometry> geos = new ArrayList<Geometry>();
		geos.add(new Plane(material));

		left = new Node(new Transform().translate(this.lbf).rotateZ(-Math.PI / 2), geos);
		right = new Node(new Transform().translate(this.run).rotateZ(Math.PI / 2), geos);

		top = new Node(new Transform().translate(this.run).rotateX(Math.PI), geos);
		bottom = new Node(new Transform().translate(this.lbf), geos);

		front = new Node(new Transform().translate(this.run).rotateX(-Math.PI / 2), geos);
		far = new Node(new Transform().translate(this.lbf).rotateX(Math.PI / 2), geos);

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

		Hit returnHit = null;
		final HashSet<Hit> hits = new HashSet<Hit>(); // keine doppelten
														// Einträge!
		double t = Double.MAX_VALUE;

		final Hit[] xHits = new Hit[]{left.hit(ray), right.hit(ray)};
		final Hit[] yHits = new Hit[]{top.hit(ray), bottom.hit(ray)};
		final Hit[] zHits = new Hit[]{front.hit(ray), far.hit(ray)};

		for (int i = 0; i < 2; i++) {
			if (xHits[i] != null) {
				final Point3 p = ray.at(xHits[i].t);
				if (p.y + 0.0001 >= lbf.y && p.y + 0.0001 <= run.y && p.z + 0.0001 >= lbf.z && p.z + 0.0001 <= run.z)
					hits.add(xHits[i]);
			}
		}

		for (int i = 0; i < 2; i++) {
			if (yHits[i] != null) {
				final Point3 p = ray.at(yHits[i].t);
				if (p.x + 0.0001 >= lbf.x && p.x + 0.0001 <= run.x && p.z + 0.0001 >= lbf.z && p.z + 0.0001 <= run.z)
					hits.add(yHits[i]);
			}
		}

		for (int i = 0; i < 2; i++) {
			if (zHits[i] != null) {
				final Point3 p = ray.at(zHits[i].t);
				if (p.x + 0.0001 >= lbf.x && p.x + 0.0001 <= run.x && p.y + 0.0001 >= lbf.y && p.y + 0.0001 <= run.y)
					hits.add(zHits[i]);
			}
		}

		for (final Hit h : hits) {

			if (h != null && h.t < t && t > 0 && h.t > 0.0001) {
				t = h.t;
				returnHit = h;
			}
		}
		return returnHit;
	}

	// ________________________________________________________________________________________________________________________________________

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