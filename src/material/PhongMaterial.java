package material;

import geometries.Hit;
import ray.World;
import color.Color;

public class PhongMaterial extends Material {
	private final Color color;
	public PhongMaterial(Color color) {
		this.color = color;
	}

	@Override
	public Color colorFor(Hit hit, World world) {
		return null;

	}

}
