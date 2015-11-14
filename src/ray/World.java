package ray;

import geometries.Geometry;
import geometries.Hit;

import java.util.ArrayList;

import color.Color;

/**
 * Die Klasse World beinhaltet eine Menge mit den Objekten zu dazustellenden
 * Szene. Sie hat ebenfalls eine Methode hit, wobei der übergebene Strahl gegen
 * alle Objekte der Szene getestet wird. Es wird der Schnittpunkt mit dem
 * kleinsten positiven 𝑡 zurückgegeben. Die Klasse World hat ein Attribut
 * namens backgroundColor vom Typ Color, welche verwendet wird, wenn ein Strahl
 * keine Geometrie trifft.
 */
public class World {

	/**
	 * Wird verwendet wenn ein Strahl keine Geometrie trifft
	 */
	Color backgroundColor;

	/**
	 * Liste zum Speichern der Geometrischen Elemente.
	 */
	ArrayList<Geometry> objs = new ArrayList<Geometry>();

	/**
	 * Konstruktor
	 *
	 * @param backgroundColor
	 */
	public World(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Testet den Uebergebene Strahl gegen alle Objekte der Szene. Und liefert
	 * den Schnittpunkt mit dem kleinsten positiven 𝑡.
	 *
	 * @param ray
	 *            Strahl welcher auf Objekte Geschickt wird.
	 * @return Den Schnittpunkt mit dem kleinsten positiven 𝑡.
	 */
	public Color hit(Ray ray) {

		Hit hit = null;
		// double t = Double.MAX_VALUE; //????????

		for (final Geometry obj : objs) {
			final Hit objHit = obj.hit(ray);

			if (objHit != null) {
				if (hit == null || objHit.t < hit.t) {
					hit = objHit;

				}
			}
		}

		if (hit != null) {
			return hit.geo.color;
		}
		return backgroundColor;

//		Hit min = null;
//
//		for (Geometry g : objs){
//
//			final Hit hit = g.hit(ray);
//
//			if(hit != null && (min == null || hit.t < min.t)){
//				min = hit;
//			}
//		}
//
//		return (min != null) ? min.geo.color : backgroundColor;
	}

	/**
	 * Fügt ein neues Geometrisches-Objekt in die Liste der Objekte hinzu.
	 *
	 * @param obj
	 *            Geometrisches-Objekt welches hinzugefügt werden soll.
	 */
	public void add(Geometry obj) {
		objs.add(obj);
	}

	/**
	 * Ueberschreibung der toString()-Methode.
	 */
	@Override
	public String toString() {
		return "World [backgroundColor=" + backgroundColor + ", objs=" + objs
				+ "]";
	}

	/**
	 * Ueberschreibung der hashCode()-Methode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((backgroundColor == null) ? 0 : backgroundColor.hashCode());
		result = prime * result + ((objs == null) ? 0 : objs.hashCode());
		return result;
	}

	/**
	 * Ueberschreibung der equals()-Methode.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final World other = (World) obj;
		if (backgroundColor == null) {
			if (other.backgroundColor != null)
				return false;
		} else if (!backgroundColor.equals(other.backgroundColor))
			return false;
		if (objs == null) {
			if (other.objs != null)
				return false;
		} else if (!objs.equals(other.objs))
			return false;
		return true;
	}
}