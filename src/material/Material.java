package material;

import application.Tracer;
import color.Color;
import geometries.Hit;
import ray.World;

/**
 * material
 *
 * Die abstrakte Basisklasse Material deklariert die Methode colorFor. Diesewird in den jeweiligen Material-Klassen
 * überschrieben und ermittelt für jedes Pixel die zurück zu gebende Farbe.
 */

public abstract class Material {

	/**
	 * Method: colorFor(Color)
	 *
	 * @param hit
	 *            : übergebenes hit - Objekt
	 * @param world
	 *            : übergebenes world - Objekt
	 * @param tracer
	 *            : übergebenes tracer - Objekt
	 * @return null - muss in den erbenden Klassen überschrieben werden
	 */
	public abstract Color colorFor(Hit hit, World world, Tracer tracer);

	@Override
	public String toString() {
		return "Material []";
	}
}
