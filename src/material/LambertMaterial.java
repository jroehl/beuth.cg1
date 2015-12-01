package material;

import application.Tracer;
import geometries.Hit;
import light.Light;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

/**
 * LambertMaterial
 *
 * @author Waschmaschine
 *         <p>
 *         Das LambertMaterial erbt von der abstrakten Klasse Material und
 *         überschreibt die Methode colorFor. Es reflektiert das Licht diffus.
 */

public class LambertMaterial extends Material {

	private final Color cd;

	/**
	 * Konstruktor: LambertMaterial
	 *
	 * @param cd
	 *            einzige farbe des Materials
	 * @throws IllegalArgumentException
	 */
	public LambertMaterial(Color cd) throws IllegalArgumentException {
		if (cd == null) {
			throw new IllegalArgumentException();
		}
		this.cd = cd;
	}
	/**
	 * Method: colorFor(Color)
	 *
	 * @param hit
	 *            : übergebenes hit - Objekt
	 * @param world
	 *            : übergebenes world - Objekt
	 * @return color - für jeden Pixel wird, falls er von der Lichtquelle
	 *         angeleuchtet wird, die Farbe errechnet und zurück gegeben
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

		Color returnColor = cd.mul(world.ambient);

		for (final Light light : world.lights) {

			if (light.illuminates(hit.ray.at(hit.t), world)) {
				final Color lightColor = light.color;
				final Vector3 lightVector = light.directionFrom(hit.ray.at(hit.t)).normalized();
				final double max = Math.max(0.0, lightVector.dot(hit.n));

				returnColor = returnColor.add(cd.mul(lightColor).mul(max));
			}
		}
		return returnColor;
	}
	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String LambertMaterial Werte
	 */
	@Override
	public String toString() {
		return "LambertMaterial [cd=" + cd + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
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
		result = prime * result + ((cd == null) ? 0 : cd.hashCode());
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
		final LambertMaterial other = (LambertMaterial) obj;
		if (cd == null) {
			if (other.cd != null)
				return false;
		} else if (!cd.equals(other.cd))
			return false;
		return true;
	}

}
