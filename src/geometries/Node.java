package geometries;

import java.util.ArrayList;

import ray.Ray;
import ray.Transform;

/**
 * Node
 *
 * @author Waschmaschine
 *         <p>
 *         Node ist eine Art Kontainer für eine beliebige Anzahl vopn
 *         Geometrien. Der Konstruktor erwartet ein Transform-Objekt udn eine
 *         Arraylis<Geometry>. Die Liste enthällt alle Geometrien, welche
 *         geschlossen eingefügt und auf die durch die Merthoden des
 *         Transform-Objketes definierte Art udn weise verformt und verschoben
 *         werden.
 *
 */

public class Node extends Geometry {

	/**
	 * trans - das übergebene Transform-Objekt
	 */
	private final Transform trans;

	/**
	 * geos - Liste mit zu transformierenden Objekten
	 */
	public ArrayList<Geometry> geos;

	public Node(Transform trans, ArrayList<Geometry> geos) throws IllegalArgumentException {
		this.trans = trans;
		this.geos = geos;

	}

	/**
	 * Method: hit(ray)
	 *
	 * @param ray
	 *            Ray Objekt
	 * @return Hit / alle Hit-Methoden der in "geos" enthaltenen Objekte werden
	 *         ausgeführt, der zum Auge am nächsten gelegene Hit ermittelt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {
		final Ray transformedRay = trans.mul(ray); // gibt transformierten Ray
													// zurück
		double t = Double.MAX_VALUE;
		Hit hitLow = null;

		for (final Geometry g : geos) {

			final Hit hit = g.hit(transformedRay);
			if (hit != null) {
				if (hit.t < t && hit.t > 0.0001) {
					t = hit.t;
					hitLow = hit;
				}
			}

		}
		if (hitLow != null) {

			return new Hit(hitLow.t, ray, hitLow.geo, trans.mul(hitLow.n), hitLow.tex);
		}
		return null;
	}

	/**
	 * Method: hashCode()
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
		result = prime * result + ((geos == null) ? 0 : geos.hashCode());
		result = prime * result + ((trans == null) ? 0 : trans.hashCode());
		return result;
	}

	/**
	 * Method: equals()
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
		final Node other = (Node) obj;
		if (geos == null) {
			if (other.geos != null)
				return false;
		} else if (!geos.equals(other.geos))
			return false;
		if (trans == null) {
			if (other.trans != null)
				return false;
		} else if (!trans.equals(other.trans))
			return false;
		return true;
	}

	/**
	 * Method: toString()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [trans=" + trans + ", geos=" + geos + "]";
	}

}
