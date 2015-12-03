package application;

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

    public Tracer(World world, int depth) {
        super();
        this.world = world;
        this.depth = depth;
    }

    public Color reflectedColors(Ray ray) {

        if (ray == null) {
            throw new IllegalArgumentException("The ray cannot be null!");
        }
        if (depth <= 0) {
            return world.backgroundColor;
        }
        Hit hit = world.getHit(ray);
        if (hit != null) {
            return hit.geo.material.colorFor(hit, world, new Tracer(world, depth - 1)); // hier
        }
        return world.backgroundColor;

    }

}
