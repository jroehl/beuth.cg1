package geometries;

import java.util.ArrayList;
import java.util.HashSet;

import Matrizen_Vektoren_Bibliothek.Point3;
import material.Material;
import ray.Ray;
import ray.Transform;

/**
 * AxisAlignedBox
 *
 * Die von Geometry abgeleitete Klasse AxisAlignedBox implementiert die Methode hit entsprechend der Formeln und
 * Algorithmen zur Schnittberechnung.
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

	/**
	 * left - Node, welches eine Plane enthällt, die so transformiert wurde, dass sie die linke Seite der Box darstellt
	 *
	 * right - Node, welches eine Plane enthällt, die so transformiert wurde, dass sie die rechte Seite der Box
	 * darstellt
	 *
	 * top - Node, welches eine Plane enthällt, die so transformiert wurde, dass sie die obere Seite der Box darstellt
	 *
	 * bottom - Node, welches eine Plane enthällt, die so transformiert wurde, dass sie die untere Seite der Box
	 * darstelltde containing plane translated to be placed on the bottom side of the box
	 *
	 * front - Node, welches eine Plane enthällt, die so transformiert wurde, dass sie die vordere Seite der Box
	 * darstellt
	 *
	 * far - Node, welches eine Plane enthällt, die so transformiert wurde, dass sie die hintere Seite der Box darstellt
	 */
	private final Node left;
	private final Node right;

	private final Node top;
	private final Node bottom;

	private final Node front;
	private final Node far;

	/**
	 * Konstruktor: AxisAlignedBox
	 *
	 * @param material
	 *            Material enthällt Textur (diese enthällt Color-Objekte oder Image-Textur)
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

		left = new Node(new Transform().translate(this.lbf).rotateZ(Math.PI / 2), geos);
		right = new Node(new Transform().translate(this.run).rotateZ(-Math.PI / 2), geos);

		top = new Node(new Transform().translate(this.run), geos);
		bottom = new Node(new Transform().translate(this.lbf).rotateX(Math.PI), geos);

		front = new Node(new Transform().translate(this.run).rotateZ(Math.PI).rotateX(Math.PI / 2), geos);
		far = new Node(new Transform().translate(this.lbf).rotateZ(Math.PI).rotateX(-Math.PI / 2), geos);

	}

	/**
	 * Method: hit(ray)
	 *
	 * @param ray
	 *            Ray Objekt
	 * @return Hit / null Bei einem Treffer wird das generierte Hit Objekt zurückgegeben und null vice versa
	 *
	 * @throws IllegalArgumentException
	 */
	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {

		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		Hit returnHit = null;
		final HashSet<Hit> hits = new HashSet<Hit>();
		double t = Double.MAX_VALUE;

		final Hit[] xHits = new Hit[] { left.hit(ray), right.hit(ray) };
		final Hit[] yHits = new Hit[] { top.hit(ray), bottom.hit(ray) };
		final Hit[] zHits = new Hit[] { front.hit(ray), far.hit(ray) };

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

			if (h != null && h.t < t && t > 0.0001 && h.t > 0.0001) {
				t = h.t;
				returnHit = h;
			}
		}
		return returnHit;
	}

	@Override
	public String toString() {
		return "AxisAlignedBox [lbf=" + lbf + ", run=" + run + ", left=" + left + ", right=" + right + ", top=" + top + ", bottom=" + bottom
				+ ", front=" + front + ", far=" + far + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AxisAlignedBox other = (AxisAlignedBox) obj;
		if (bottom == null) {
			if (other.bottom != null)
				return false;
		} else if (!bottom.equals(other.bottom))
			return false;
		if (far == null) {
			if (other.far != null)
				return false;
		} else if (!far.equals(other.far))
			return false;
		if (front == null) {
			if (other.front != null)
				return false;
		} else if (!front.equals(other.front))
			return false;
		if (lbf == null) {
			if (other.lbf != null)
				return false;
		} else if (!lbf.equals(other.lbf))
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		if (run == null) {
			if (other.run != null)
				return false;
		} else if (!run.equals(other.run))
			return false;
		if (top == null) {
			if (other.top != null)
				return false;
		} else if (!top.equals(other.top))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bottom == null) ? 0 : bottom.hashCode());
		result = prime * result + ((far == null) ? 0 : far.hashCode());
		result = prime * result + ((front == null) ? 0 : front.hashCode());
		result = prime * result + ((lbf == null) ? 0 : lbf.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + ((run == null) ? 0 : run.hashCode());
		result = prime * result + ((top == null) ? 0 : top.hashCode());
		return result;
	}
}