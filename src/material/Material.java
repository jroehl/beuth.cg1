package material;

import geometries.Hit;
import ray.World;
import color.Color;
/**
 * material
 *
 * @author Waschmaschine
 *         <p>
 *         Die abstrakte Basisklasse Material deklariert die Methode colorFor.
 *         Diesewird in den jeweiligen Material-Klassen überschrieben und
 *         ermittelt für jedes Pixel die zurück zu gebende Farbe.
 */

public abstract class Material {

	/**
	 * Method: colorFor(Color)
	 *
	 * @param hit
	 *            : übergebenes hit - Objekt
	 * @param world
	 *            : übergebenes world - Objekt
	 * @return null - muss in den erbenden Klassen überschrieben werden
	 */

	public Color colorFor(Hit hit, World world) {
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Material [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
