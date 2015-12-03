package material;

import geometries.Hit;
import light.Light;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Vector3;
import application.Tracer;
import color.Color;

/**
 * Created by Waschmaschine on 01.12.15.
 */
public class ReflectiveMaterial extends Material {

	private final Color diffuse;
	private final Color specular;
	private final Color reflectionColor;
	private final int exponent;

	public ReflectiveMaterial(Color diffuse, Color specular, Color reflectionColor, int exponent) {
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflectionColor = reflectionColor;
		this.exponent = exponent;

	}

	@Override
	public Color colorFor(Hit hit, World world, Tracer tracer) {

		Color returnColor = diffuse.mul(world.ambient);

		for (final Light light : world.lights) {

			if (light.illuminates(hit.ray.at(hit.t), world)) {

				final Color lightColor = light.color;

				final Vector3 lightVector = light.directionFrom(hit.ray.at(hit.t)).normalized();

				final Vector3 reflectedVector = lightVector.reflectedOn(hit.n);

				final Vector3 e = (hit.ray.direction.mul(-1)).normalized();

				final double max = Math.max(0.0, lightVector.dot(hit.n));

				returnColor = returnColor.add(diffuse.mul(lightColor).mul(max));

				returnColor = returnColor.add(specular.mul(lightColor.mul(Math.pow(Math.max(0.0, reflectedVector.dot(e)), exponent))));
				returnColor.add(tracer.reflectedColors(hit.ray, reflectionColor, 6));
				// hier soll die Farbe der Reflektionen dazu addiert werden -
				// wird in der Methode im Tracer errechnet

			}
		}
		return returnColor;
	}
}
