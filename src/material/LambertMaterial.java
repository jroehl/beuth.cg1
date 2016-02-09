package material;

import Matrizen_Vektoren_Bibliothek.Vector3;
import application.Tracer;
import color.Color;
import geometries.Hit;
import light.Light;
import ray.World;
import textures.Texture;

/**
 * LambertMaterial
 *
 * Das LambertMaterial erbt von der abstrakten Klasse Material und überschreibt die Methode colorFor. Es reflektiert das
 * Licht diffus.
 */
public class LambertMaterial extends Material {

	/**
	 * Textur des Materials(enthällt Info über die Fraben der jeweiligen Pixel auf der Oberfläche der geometrien)
	 */
	private final Texture tex;

	/**
	 * Konstruktor: LambertMaterial
	 *
	 * @param tex
	 *            Einzige textur des Materials
	 * @throws IllegalArgumentException
	 */
	public LambertMaterial(Texture tex) throws IllegalArgumentException {
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
	 * @return color - für jeden Pixel wird, falls er von der Lichtquelle angeleuchtet wird, die Farbe errechnet und
	 *         zurück gegeben
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

		Color returnColor = tex.colorFor(hit.tex.u, hit.tex.v).mul(world.ambient);

		for (final Light light : world.lights) {

			if (light.illuminates(hit.ray.at(hit.t), world)) {
				final Color lightColor = light.color;
				final Vector3 lightVector = light.directionFrom(hit.ray.at(hit.t)).normalized();
				final double max = Math.max(0.0, lightVector.dot(hit.n));

				returnColor = returnColor.add(tex.colorFor(hit.tex.u, hit.tex.v).mul(lightColor).mul(max));
			}
		}
		return returnColor;
	}
}
