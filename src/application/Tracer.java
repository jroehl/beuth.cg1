package application;

import color.Color;
import geometries.Geometry;
import geometries.Hit;
import ray.Ray;
import ray.World;

/**
 * Ein Tracer ist ein Objekt, welches eine Funktion zum Raytracen zur Verfügung stellt (die Funktion Cr*Fr[(Pr, Rd)]
 * mit: Rd = D+2(-D.dot(N)).mul(N)))
 * 
 * Refeltions verfolgung.
 */
public class Tracer {

	/**
	 * depth - int Rekursionstiefe world - World Objekt
	 */
	public int depth;

	/**
	 * 
	 */
	private final World world;

	/**
	 * Erzeugt einen Konstruktor: Tracer
	 *
	 * @param world
	 *            Das World Objekt
	 * @param depth
	 *            int Rekursionstiefe
	 */
	public Tracer(World world, int depth) {
		super();
		this.world = world;
		this.depth = depth;
	}

	/**
	 * Method: rayFor
	 *
	 * @param ray
	 *            Strahl der ggf. reflektiert wird
	 * @return Color
	 */
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
		return world.backgroundColor;
	}

	@Override
	public String toString() {
		return "Tracer{" + "depth=" + depth + ", world=" + world + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Tracer tracer = (Tracer) o;

		if (depth != tracer.depth)
			return false;
		return !(world != null ? !world.equals(tracer.world) : tracer.world != null);

	}

	@Override
	public int hashCode() {
		int result = depth;
		result = 31 * result + (world != null ? world.hashCode() : 0);
		return result;
	}
}