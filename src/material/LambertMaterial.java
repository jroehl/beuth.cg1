package material;

import Matrizen_Vektoren_Bibliothek.Vector3;
import geometries.Hit;
import light.Light;
import ray.World;
import color.Color;

public class LambertMaterial extends Material {
	private final Color cd;

	public LambertMaterial(Color cd) {
		this.cd = cd;
	}

	// @Override
	// public Color colorFor(Hit hit, World world) {
	// final Color c = cd.mul(world.ambient);
	// for (int i = 0; i < world.lights.size(); i++) {
	//
	// c.add(cd.mul(world.lights.get(i).getColor()).mul(Math.max(0,
	// world.lights.get(i).directionFrom(hit.ray.at(hit.t)).dot(hit.n))));
	// // STIMMT DAS?????
	// }
	// return c;
	//
	// }
	@Override
	public Color colorFor(Hit hit, World world) {

		Color c = cd.mul(world.ambient);

		for (final Light li : world.lights) {

			final Color lightColor = li.color;

			final Vector3 lightVector = li.directionFrom(
					hit.ray.at(hit.t)).normalized();

			final double max = Math.max(0.0, lightVector.dot(hit.n));

			c = c.add(c.mul(lightColor).mul(max));

			// STIMMT DAS?????
		}
		return c;

	}
}
