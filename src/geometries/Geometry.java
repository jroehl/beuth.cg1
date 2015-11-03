package geometries;

import ray.Ray;
import color.Color;

public abstract class Geometry {

	public final Color color;

	public Geometry(Color color) {
		this.color = color;
	}

	public Hit hit(Ray r) {
		return null;

	}

}
