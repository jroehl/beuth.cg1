package geometries;

import ray.Ray;
import color.Color;

public abstract class Geometry {

    public final Color color;

    public Geometry(Color color) {
        this.color = color;
    }

    public abstract Hit hit(Ray ray);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geometry geometry = (Geometry) o;

        return !(color != null ? !color.equals(geometry.color) : geometry.color != null);

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "color=" + color +
                '}';
    }
}


