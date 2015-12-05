package application;

import geometries.Geometry;
import geometries.Hit;
import ray.Ray;
import ray.World;
import color.Color;

/**
 * Created by Waschmaschine on 01.12.15. umzusentzende Formel: Cr*Fr[(Pr, Rd)]
 * mit: Rd = D+2(-D.dot(N)).mul(N)
 */
public class Tracer {

	public int depth;
	private final World world;

	// private final Ray ray;

	public Tracer(World world, int depth) {// Ray ray
		super();
		this.world = world;
		this.depth = depth;

		// this.ray = ray;
	}

	public Color reflectedColors(Ray ray) {

		if (ray == null) {
			throw new IllegalArgumentException("The ray cannot be null!");
		}
		if (depth <= 0) {

			return world.backgroundColor;
		}

		Hit h = null;
		final double u = 0.00001;
		double t2 = 0;
		for (final Geometry g : world.objs) {
			h = g.hit(ray);

			if (h != null) {
				t2 = h.t;
			}
			if (h != null && t2 >= u) {
				return h.geo.material.colorFor(h, world, new Tracer(world, depth - 1));
			}
		}
		// System.out.println(ray.direction.x + "\n" + ray.direction.y + "\n" +
		// ray.direction.z + "\n");
		// final Hit hit = world.getHit(ray);
		// ray = new Ray(hit.ray.at(hit.t),
		// hit.ray.direction.reflectedOn(hit.n));

		//

		return world.backgroundColor;

	}
}
