package geometries;

import ray.Ray;
import color.Color;

public abstract class Geometry {

	public final Color c;

	public Geometry(Color c) {
		this.c = c;
	}

	public Hit hit(Ray r) {
		return null;

	}

}
