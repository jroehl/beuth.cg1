package material;

import application.Tracer;
import geometries.Hit;
import ray.World;
import color.Color;

/**
 * SingleColorMaterial
 *
 * @author Waschmaschine
 *         <p>
 *         Das SingleColorMaterial erbt von der abstrakten Klasse Material und
 *         überschreibt die Methode colorFor. Es gibt keine reflektion von
 *         Licht.
 */
public class SingleColorMaterial extends Material {

	private final Color color;

	/**
	 * Konstruktor: SingleColorMaterial
	 *
	 * @param color
	 *            einzige farbe des Materials
	 * @throws IllegalArgumentException
	 */

	public SingleColorMaterial(Color color) throws IllegalArgumentException {
		if (color == null) {
			throw new IllegalArgumentException();
		}
		this.color = color;
	}
	/**
	 * Method: colorFor(Color)
	 *
	 * @param hit
	 *            : übergebenes hit - Objekt
	 * @param world
	 *            : übergebenes world - Objekt
	 * @return color - für jeden Pixel wird die übergeben Farbe zurück gegeben
	 * @throws IllegalArgumentException
	 */
	@Override
	public Color colorFor(Hit hit, World world, Tracer tracer) throws IllegalArgumentException {
		if (hit == null) {
			throw new IllegalArgumentException("hit cannot be null!");
		}
		if (world == null) {
			throw new IllegalArgumentException("world cannot be null!");
		}
		return color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SingleColorMaterial [color=" + color + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SingleColorMaterial other = (SingleColorMaterial) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
}
