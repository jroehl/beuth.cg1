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

		Color returnColor = cd.mul(world.ambient);

		for (Light light : world.lights) {
			Color lightColor = light.color;
				final Vector3 lightVector = light.directionFrom(
						hit.ray.at(hit.t)).normalized();
				final double max = Math.max(0.0, lightVector.dot(hit.n));

				returnColor = returnColor.add(cd.mul(lightColor).mul(max));
		}
		return returnColor;

	}
}
