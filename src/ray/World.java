package ray;

import java.util.ArrayList;

import color.Color;
import geometries.Geometry;
import geometries.Hit;

/**
 * Die Klasse World beinhaltet eine Menge mit den Objekten zu dazustellenden Szene. Sie hat ebenfalls eine Methode hit,
 * wobei der übergebene Strahl gegen alle Objekte der Szene getestet wird. Es wird der Schnittpunkt mit dem kleinsten
 * positiven 𝑡 zurückgegeben. Die Klasse World hat ein Attribut namens backgroundColor vom Typ Color, welche verwendet
 * wird, wenn ein Strahl keine Geometrie trifft.
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
	 * Testet den Uëbergebene Strahl gegen alle Objekte der Szene. Und liefert den Schnittpunkt mit dem kleinsten
	 * positiven 𝑡.
	 * 
	 * @param ray
	 *            Strahl welcher auf Objekte Geschickt wird.
	 * @return Den Schnittpunkt mit dem kleinsten positiven 𝑡.
	 */
	public Color hit(Ray ray) {
		Hit hit = null;

		for (Geometry obj : objs) {
			Hit objHit = obj.hit(ray);

			if (objHit != null) {
				if (objHit.t < hit.t) {
					hit = obj.hit(ray);
				}
			}
		}

		if (hit != null) {
			return hit.geo.color;
		}
		return backgroundColor;
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
}