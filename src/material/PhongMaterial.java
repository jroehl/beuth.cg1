package material;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import geometries.Hit;
import light.Light;
import ray.World;
import color.Color;

public class PhongMaterial extends Material {
	private final Color diffuse;
	private final Color specular;
	private final int exponent;

	public PhongMaterial(Color diffuse, Color specular, int exponent) {
		this.diffuse = diffuse;
		this.specular = specular;
		this.exponent = exponent;
	}

	@Override
	// public Color colorFor(Hit hit, World world) {
	// final Color c = diffuse.mul(world.ambient);
	// for (int i = 0; i < world.lights.size(); i++) {
	//
	// c.add(diffuse.mul(world.lights.get(i).getColor()).mul(
	// Math.max(0,
	// world.lights.get(i).directionFrom(hit.ray.at(hit.t)).dot(hit.n)))).add(
	// specular.mul(world.lights.get(i).getColor()).mul(
	// Math.max(
	// 0,
	// (hit.ray.origin.sub(hit.ray.at(hit.t)).dot(world.lights.get(i).directionFrom(hit.ray.at(hit.t))
	// .reflectedOn(hit.n))))));
	//
	// }
	//
	// return c;
	//
	// }
	public Color colorFor(Hit hit, World world) {
//		Color c = diffuse.mul(world.ambient);
//		for (final Light li : world.lights) {
//			c = c.add(diffuse.mul(li.color).mul(Math.max(0, li.directionFrom(hit.ray.at(hit.t)).dot(hit.n)))).add(
//					specular.mul(li.color)
//							.mul(Math.max(0,
//									(hit.ray.origin.sub(hit.ray.at(hit.t)).dot(li.directionFrom(hit.ray.at(hit.t)).reflectedOn(hit.n))))));
//
//		}
//
//		return c;

		final Normal3 normal = hit.n;

		final Point3 point = hit.ray.at(hit.t);

		Color color = world.ambient.mul(diffuse);

		final Vector3 e = (hit.ray.direction.mul(-1)).normalized();

		Color lightColor = new Color(0, 0, 0);

		for (Light light : world.lights) {

				final Vector3 lightVector = light.directionFrom(point)
						.normalized();
				final Vector3 reflectedVector = lightVector.reflectedOn(normal);

				final double maxNL = Math.max(0.0, lightVector.dot(normal));
				final double maxER = Math.pow(
						Math.max(0.0, reflectedVector.dot(e)), exponent);

				lightColor = lightColor
						.add(light.color.mul(diffuse).mul(maxNL)).add(
								light.color.mul(specular).mul(maxER));

				color = color.add(lightColor);

		}

		return color;

	}

}
