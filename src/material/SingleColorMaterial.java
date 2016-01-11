package material;

import geometries.Hit;
import ray.World;
import textures.Texture;
import application.Tracer;
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

	private final Texture tex;

	/**
	 * Konstruktor: SingleColorMaterial
	 *
	 * @param tex
	 *            einzige farbe des Materials
	 * @throws IllegalArgumentException
	 */

	public SingleColorMaterial(Texture tex) throws IllegalArgumentException {
		if (tex == null) {
			throw new IllegalArgumentException();
		}
		this.tex = tex;
	}

	/**
	 * Method: colorFor(Color)
	 *
	 * @param hit
	 *            : übergebenes hit - Objekt
	 * @param world
	 *            : übergebenes world - Objekt
	 * @param tracer
	 *            : übergebenes tracer - Objekt
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

		return tex.colorFor(hit.tex.u, hit.tex.v);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

}
