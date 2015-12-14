package application;

import geometries.Geometry;
import geometries.Hit;
import ray.Ray;
import ray.World;
import color.Color;

/**
 * Ein Tracer ist ein Objekt, welches eine Funktion zum Raytracen zur Verfügung stellt (die Funktion Cr*Fr[(Pr, Rd)]
 * mit: Rd = D+2(-D.dot(N)).mul(N)))
 */
public class Tracer {

    /**
     * depth - int Rekursionstiefe
     * world - World Objekt
     */
    public int depth;
    private final World world;

    // private final Ray ray;

    /**
     * Konstruktor: Tracer
     *
     * @param world Das World Objekt
     * @param depth int Rekursionstiefe
     */
    public Tracer(World world, int depth) {// Ray ray
        super();
        this.world = world;
        this.depth = depth;

        // this.ray = ray;
    }

    /**
     * Method: rayFor
     *
     * @param ray Strahl der ggf. reflektiert wird
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

    /**
     * Ueberschriebene toString-Methode
     *
     * @return String Tracer Werte
     */
    @Override
    public String toString() {
        return "Tracer{" +
                "depth=" + depth +
                ", world=" + world +
                '}';
    }

    /*
     * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tracer tracer = (Tracer) o;

        if (depth != tracer.depth) return false;
        return !(world != null ? !world.equals(tracer.world) : tracer.world != null);

    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = depth;
        result = 31 * result + (world != null ? world.hashCode() : 0);
        return result;
    }
}
