package material;

import geometries.Hit;
import light.Light;
import ray.Ray;
import ray.World;
import textures.Texture;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import application.Tracer;
import color.Color;

/**
 * ReflectiveMaterial
 *
 * @author Waschmaschine
 *         <p>
 *         Das ReflectiveMaterial erbt von der abstrakten Klasse Material und
 *         überschreibt die Methode colorFor. Es reflektiert das Licht und
 *         umliegende Objekte
 */
public class ReflectiveMaterial extends Material {

	private final Texture diffuse;
	private final Texture specular;
	private final Texture reflectionColor;
	private final int exponent;

	/**
	 * Konstruktor: ReflectiveMaterial
	 *
	 * @param diffuse
	 *            Textur der diffusen Reflektion
	 * @param specular
	 *            Textur des Reflektionspunktes
	 * @param reflectionColor
	 *            Textur des Reflektionspunktes
	 * @param exponent
	 *            der Exponent bestimmt die größe des errechneten
	 *            Reflektionspunktes
	 * @throws IllegalArgumentException
	 */
	public ReflectiveMaterial(Texture diffuse, Texture specular, Texture reflectionColor, int exponent) {
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflectionColor = reflectionColor;
		this.exponent = exponent;

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
	 * @return color - für jeden Pixel wird, falls er von der Lichtquelle
	 *         angeleuchtet wird, die Farbe errechnet und zurück gegeben.
	 * @throws IllegalArgumentException
	 */

	@Override
	public Color colorFor(Hit hit, World world, Tracer tracer) {

		if (hit == null) {
			throw new IllegalArgumentException("The hit cannot be null!");
		}
		if (world == null) {
			throw new IllegalArgumentException("The world cannot be null!");
		}
		if (tracer == null) {
			throw new IllegalArgumentException("The tracer cannot be null!");
		}

		Color returnColor = diffuse.colorFor(hit.tex.u, hit.tex.v).mul(world.ambient);
		final Point3 hitPoint = hit.ray.at(hit.t);
		final double factor = hit.n.dot(hit.ray.direction.mul(-1.0)) * 2;
		final Vector3 e = (hit.ray.direction.mul(-1.0)).normalized();
		for (final Light light : world.lights) {
			if (light.illuminates(hitPoint, world)) {
				final Vector3 lightVector = light.directionFrom(hitPoint).normalized();
				final Vector3 reflectedVector = lightVector.normalized().reflectedOn(hit.n);
				final double max = Math.max(0.0, lightVector.dot(hit.n));
				final double maxSP = Math.pow(Math.max(0.0, reflectedVector.dot(e)), this.exponent);

				returnColor = returnColor.add(light.color.mul(this.diffuse.colorFor(hit.tex.u, hit.tex.v)).mul(max)).add(
						light.color.mul(this.specular.colorFor(hit.tex.u, hit.tex.v)).mul(maxSP));
			}
		}
		return returnColor.add(reflectionColor.colorFor(hit.tex.u, hit.tex.v).mul(
				tracer.reflectedColors(new Ray(hitPoint, hit.ray.direction.add(hit.n.mul(factor)).normalized()))));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReflectiveMaterial{" + "diffuse=" + diffuse + ", specular=" + specular + ", reflectionColor=" + reflectionColor
				+ ", exponent=" + exponent + '}';
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final ReflectiveMaterial that = (ReflectiveMaterial) o;

		if (exponent != that.exponent)
			return false;
		if (diffuse != null ? !diffuse.equals(that.diffuse) : that.diffuse != null)
			return false;
		if (specular != null ? !specular.equals(that.specular) : that.specular != null)
			return false;
		return !(reflectionColor != null ? !reflectionColor.equals(that.reflectionColor) : that.reflectionColor != null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = diffuse != null ? diffuse.hashCode() : 0;
		result = 31 * result + (specular != null ? specular.hashCode() : 0);
		result = 31 * result + (reflectionColor != null ? reflectionColor.hashCode() : 0);
		result = 31 * result + exponent;
		return result;
	}
}
