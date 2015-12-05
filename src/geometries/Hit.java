package geometries;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;

/**
 * Hit
 *
 * @author Waschmaschine
 *         <p>
 *         Die Klasse Hit drückt einen Schnittpunkt mit einem Objekt der Klasse
 *         Hit aus. Dieses verweist auf den Strahl, die Geometrie und beinhaltet
 *         das t an dem dieser Strahl und diese Geometrie sich schneiden.
 */
public class Hit {

	/**
	 * t - Faktor
	 */
	public final double t;
	/**
	 * ray - Ray Objekt
	 */
	public final Ray ray;
	/**
	 * geo - Geometry Objekt
	 */
	public final Geometry geo;
	/**
	 * n - Normal3 Normale Objekt
	 */
	public final Normal3 n;

	// public final Normal3 normSP;

	/**
	 * Konstruktor: Hit
	 *
	 * @param t
	 *            Faktor
	 * @param ray
	 *            Ray Objekt
	 * @param geo
	 *            Geometry Objekt
	 * @throws IllegalArgumentException
	 */
	public Hit(final double t, final Ray ray, final Geometry geo, final Normal3 n) throws IllegalArgumentException {

		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}
		if (geo == null) {
			throw new IllegalArgumentException("The Geometry cannot be null!");
		}
		this.t = t;
		this.ray = ray;
		this.geo = geo;
		this.n = n;

	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Hit Werte
	 */
	@Override
	public String toString() {
		return "Hit [t=" + t + ", ray=" + ray + ", geo=" + geo + "]";
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
		result = prime * result + ((geo == null) ? 0 : geo.hashCode());
		result = prime * result + ((ray == null) ? 0 : ray.hashCode());
		long temp;
		temp = Double.doubleToLongBits(t);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param obj
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
		final Hit other = (Hit) obj;
		if (geo == null) {
			if (other.geo != null)
				return false;
		} else if (!geo.equals(other.geo))
			return false;
		if (ray == null) {
			if (other.ray != null)
				return false;
		} else if (!ray.equals(other.ray))
			return false;
		if (Double.doubleToLongBits(t) != Double.doubleToLongBits(other.t))
			return false;
		return true;
	}

}
