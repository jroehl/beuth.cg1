package material;

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
		Color c = diffuse.mul(world.ambient);
		for (final Light li : world.lights) {
			c = c.add(diffuse.mul(li.color).mul(Math.max(0, li.directionFrom(hit.ray.at(hit.t)).dot(hit.n)))).add(
					specular.mul(li.color)
							.mul(Math.max(0,
									(hit.ray.origin.sub(hit.ray.at(hit.t)).dot(li.directionFrom(hit.ray.at(hit.t)).reflectedOn(hit.n))))));

		}

		return c;

	}

}
