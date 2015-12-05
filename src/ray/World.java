package ray;

import geometries.Geometry;
import geometries.Hit;

import java.util.ArrayList;

import light.Light;
import application.Tracer;
import color.Color;

/**
 * Die Klasse World beinhaltet eine Menge mit den Objekten zu dazustellenden
 * Szene. Sie hat ebenfalls eine Methode hit, wobei der uebergebene Strahl gegen
 * alle Objekte der Szene getestet wird. Es wird der Schnittpunkt mit dem
 * kleinsten positiven 𝑡 zurückgegeben. Die Klasse World hat ein Attribut
 * namens backgroundColor vom Typ Color, welche verwendet wird, wenn ein Strahl
 * keine Geometrie trifft.
 */
public class World {

	/**
	 * Wird verwendet wenn ein Strahl keine Geometrie trifft
	 */
	public Color backgroundColor;
	public Color ambient = new Color(0.25, 0.25, 0.25); // wird auf die ganze
	// Szene addiert, um
	// alles heller zu
	// machen

	/**
	 * Liste zum Speichern der Geometrischen Elemente.
	 */
	public ArrayList<Geometry> objs = new ArrayList<Geometry>();

	/**
	 * Liste aller Lichter welche auf der World initialisiert sind.
	 */
	public ArrayList<Light> lights = new ArrayList<Light>();

	/**
	 * Konstruktor
	 *
	 * @param backgroundColor
	 * @throws IllegalArgumentException
	 */
	public World(Color backgroundColor) throws IllegalArgumentException {

		if (backgroundColor == null) {
			throw new IllegalArgumentException("The backgroundColor cannot be null!");
		}

		this.backgroundColor = backgroundColor;
	}

	/**
	 * Testet den Uebergebene Strahl gegen alle Objekte der Szene. Und liefert
	 * den Schnittpunkt mit dem kleinsten positiven 𝑡.
	 *
	 * @param ray
	 *            Strahl welcher auf Objekte Geschickt wird.
	 * @return Schnittpunkt mit dem kleinsten positiven t
	 * @throws IllegalArgumentException
	 */
	public Color hit(Ray ray) throws IllegalArgumentException {

		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		Hit hit = null;

		for (final Geometry obj : objs) {
			final Hit objHit = obj.hit(ray);

			if (objHit != null) {
				if (hit == null || objHit.t < hit.t) {
					hit = objHit;
				}
			}
		}

		if (hit != null) {
			return hit.geo.material.colorFor(hit, this, new Tracer(this, 5));// ,ray
		}

		return backgroundColor;

	}

	public Hit getHit(Ray ray) throws IllegalArgumentException {

		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		Hit hit = null;

		final double u = 0.00001;
		double t2 = 0;

		for (final Geometry obj : objs) {
			hit = obj.hit(ray);

			if (hit != null) {
				t2 = hit.t;
			}

			if (hit != null && t2 >= u) {
				hit.geo.material.colorFor(hit, this, new Tracer(this, 5));
			}
		}

		if (hit != null) {
			return hit;
		}

		return null;

	}

	/**
	 * Fügt ein neues Geometrisches-Objekt in die Liste der Objekte hinzu.
	 *
	 * @param obj
	 *            Geometrisches-Objekt welches hinzugefügt werden soll.
	 * @throws IllegalArgumentException
	 */
	public void addGeometry(Geometry obj) throws IllegalArgumentException {
		if (obj == null) {
			throw new IllegalArgumentException("The Geometry cannot be null!");
		}

		objs.add(obj);
	}

	/**
	 * Fügt ein neues Licht-Objekt in die Liste der Lichter hinzu.
	 *
	 * @param obj
	 *            Light-Objekt welches hinzugefügt werden soll.
	 * @throws IllegalArgumentException
	 */
	public void addLight(Light light) throws IllegalArgumentException {
		if (light == null) {
			throw new IllegalArgumentException("The Light cannot be null!");
		}

		lights.add(light);
	}

	/**
	 * Ueberschreibung der toString()-Methode.
	 */
	@Override
	public String toString() {
		return "World [backgroundColor=" + backgroundColor + ", ambient=" + ambient + ", objs=" + objs + ", lights=" + lights + "]";
	}

	/**
	 * Ueberschreibung der hashCode()-Methode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ambient == null) ? 0 : ambient.hashCode());
		result = prime * result + ((backgroundColor == null) ? 0 : backgroundColor.hashCode());
		result = prime * result + ((lights == null) ? 0 : lights.hashCode());
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
		if (ambient == null) {
			if (other.ambient != null)
				return false;
		} else if (!ambient.equals(other.ambient))
			return false;
		if (backgroundColor == null) {
			if (other.backgroundColor != null)
				return false;
		} else if (!backgroundColor.equals(other.backgroundColor))
			return false;
		if (lights == null) {
			if (other.lights != null)
				return false;
		} else if (!lights.equals(other.lights))
			return false;
		if (objs == null) {
			if (other.objs != null)
				return false;
		} else if (!objs.equals(other.objs))
			return false;
		return true;
	}
}