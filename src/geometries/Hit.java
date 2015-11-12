package geometries;

import ray.Ray;

public class Hit {

	public final double t;
	public final Ray ray;
	public final Geometry geo;

	public Hit(final double t, final Ray ray, final Geometry geo)
			throws IllegalArgumentException {

		if (t == 0 || ray == null | geo == null) {
			throw new IllegalArgumentException();
		}
		this.t = t;
		this.ray = ray;
		this.geo = geo;

	}

}
