package material;

import application.Tracer;
import color.Color;
import geometries.Hit;
import ray.World;
import textures.Texture;

/**
 * SingleColorMaterial
 *
 * Das SingleColorMaterial erbt von der abstrakten Klasse Material und überschreibt die Methode colorFor. Es gibt keine
 * reflektion von Licht.
 */
public class SingleColorMaterial extends Material {

	/**
	 * 
	 */
	private final Texture tex;

	/**
	 * Konstruktor: SingleColorMaterial
	 *
	 * @param tex
	 *            textur für die einzige farbe des Materials
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

	@Override
	public String toString() {
		return "SingleColorMaterial [tex=" + tex + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tex == null) ? 0 : tex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SingleColorMaterial other = (SingleColorMaterial) obj;
		if (tex == null) {
			if (other.tex != null)
				return false;
		} else if (!tex.equals(other.tex))
			return false;
		return true;
	}
}