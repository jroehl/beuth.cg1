package material;

import geometries.Hit;
import ray.World;
import color.Color;

public class SingleColorMaterial extends Material {

	private final Color color;

	public SingleColorMaterial(Color color) {
		this.color = color;
	}

	@Override
	public Color colorFor(Hit hit, World world) {
		return color;

	}

}
