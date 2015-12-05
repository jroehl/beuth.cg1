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

		if (hit == null) {
			throw new IllegalArgumentException("The hit cannot be null!");
		}
		if (world == null) {
			throw new IllegalArgumentException("The world cannot be null!");
		}
		if (tracer == null) {
			throw new IllegalArgumentException("The tracer cannot be null!");
		}

		Color returnColor = diffuse.mul(world.ambient);
		final Point3 hitPoint = hit.ray.at(hit.t);
		final double factor = hit.n.dot(hit.ray.direction.mul(-1.0)) * 2;
		// final double factor = hit.n.dot(hit.ray.direction.reflectedOn(hit.n))
		// * 2;
		final Vector3 e = (hit.ray.direction.mul(-1.0)).normalized();
		for (final Light light : world.lights) {

			if (light.illuminates(hitPoint, world)) {

				final Vector3 lightVector = light.directionFrom(hitPoint).normalized();

				final Vector3 reflectedVector = lightVector.normalized().reflectedOn(hit.n);

				final double max = Math.max(0.0, lightVector.dot(hit.n));
				final double maxSP = Math.pow(Math.max(0.0, reflectedVector.dot(e)), this.exponent);

				returnColor = returnColor.add(light.color.mul(this.diffuse).mul(max)).add(light.color.mul(this.specular).mul(maxSP));
			}
		}

		return returnColor.add(reflectionColor.mul(tracer.reflectedColors(new Ray(hitPoint, hit.ray.direction.add(
				hit.n.mul(factor + 0.0001)).normalized()))));
		// return returnColor.add(reflectionColor.mul(tracer.reflectedColors(new
		// Ray(hitPoint, hit.ray.direction.add(hit.n.mul(factor))))));
		// return returnColor;
	}
}
