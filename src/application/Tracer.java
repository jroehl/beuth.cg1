package application;

import ray.Ray;
import ray.World;
import color.Color;

/**
 * Created by Waschmaschine on 01.12.15. umzusentzende Formel: Cr*Fr[(Pr, Rd)]
 * mit: Rd = D+2(-D.dot(N)).mul(N)
 */
public class Tracer {

	private final int depth;
	private final World world;
	private Color cr;

	public Tracer(World world, int depth) {
		this.world = world;
		this.depth = depth;

	}
	public Color reflectedColors(Ray ray, Color cr, int depth) {

		if (depth == 0) {
			return world.ambient;
		}
		cr = cr.mul(world.hit(ray));

		return reflectedColors(new Ray(), cr, depth--); // hier den neuen Strahl
														// mit an der Oberfläche
														// des Objektes
														// reflektierter
														// Richtung erzeugen!!
														// Aber wie?

	}

}
