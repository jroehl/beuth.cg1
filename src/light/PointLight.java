package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

/**
 * PointLight
 *
 * @author Waschmaschine
 *         <p>
 *         Strahlt gleichmäßig in alle Richtungen wie eine Lampe mit einer
 *         festen Position
 */
public class PointLight extends Light {

	private final Point3 pl;
	private final Color cl;

	/**
	 * Konstruktor: PointLight
	 *
	 * @param position
	 *            Point3 des Lichts
	 * @param cl
	 *            Color des Lichts
	 * @throws IllegalArgumentException
	 */
	public PointLight(Color cl, Point3 position) throws IllegalArgumentException {
		super(cl);
		if (cl == null) {
			throw new IllegalArgumentException("cl cannot be null!");
		}

		if (position == null) {
			throw new IllegalArgumentException("position cannot be null!");
		}
		this.cl = cl;
		this.pl = position;
	}

	/**
	 * Method: illuminates(Point3)
	 *
	 * @param p
	 *            Übergebenes Point3 - Objekt
	 * @return true / false wenn der übergebene Punkt beleuchtet wird
	 * @throws IllegalArgumentException
	 */
	@Override
	public boolean illuminates(Point3 p) throws IllegalArgumentException {
		if (p == null) {
			throw new IllegalArgumentException("The point3 cannot be null!");
		}
		return true;
	}

	/**
	 * Method: directionFrom(Point3)
	 *
	 * @param p
	 *            Übergebenes Point3 - Objekt
	 * @return Der zur Lichtquelle zeigende Vector3
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector3 directionFrom(Point3 p) throws IllegalArgumentException {
		if (p == null) {
			throw new IllegalArgumentException("The point3 cannot be null!");
		}
		return pl.sub(p).normalized();
	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String PointLight Werte
	 */
	@Override
	public String toString() {
		return "PointLight [pl=" + pl + ", cl=" + cl + "]";
	}
	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cl == null) ? 0 : cl.hashCode());
		result = prime * result + ((pl == null) ? 0 : pl.hashCode());
		return result;
	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param o
	 *            Objekt das mit der Matrix verglichen wird
	 * @return true | false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PointLight other = (PointLight) obj;
		if (cl == null) {
			if (other.cl != null)
				return false;
		} else if (!cl.equals(other.cl))
			return false;
		if (pl == null) {
			if (other.pl != null)
				return false;
		} else if (!pl.equals(other.pl))
			return false;
		return true;
	}
}