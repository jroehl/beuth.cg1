package material;

import geometries.Hit;
import light.Light;
import ray.Ray;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Point3;
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
		final Point3 hitPoint = hit.ray.at(hit.t);
		final double factor = hit.n.dot(hit.ray.direction.mul(-1.0)) * 2;
		final Vector3 e = (hit.ray.direction.mul(-1)).normalized();

		for (final Light light : world.lights) {

			if (light.illuminates(hitPoint, world)) {
				final Color lightColor = light.color;
				final Vector3 lightVector = light.directionFrom(hit.ray.at(hit.t)).normalized();
				final Vector3 reflectedVector = lightVector.reflectedOn(hit.n);

				final double max = Math.max(0.0, lightVector.dot(hit.n));
				returnColor = returnColor.add(diffuse.mul(lightColor).mul(max));
				returnColor = returnColor.add(specular.mul(lightColor.mul(Math.pow(Math.max(0.0, reflectedVector.dot(e)), exponent))));

				// hier soll die Farbe der Reflektionen dazu addiert werden -
				// wird in der Methode im Tracer errechnet

			}
		}

		final Color reflColor = tracer.reflectedColors(new Ray(hitPoint, hit.ray.direction.add(hit.n.mul(factor))), hit);
		return returnColor.add(reflectionColor.mul(reflColor));

	}
}
