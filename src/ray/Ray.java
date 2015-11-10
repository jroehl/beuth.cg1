package ray;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class Ray {

    public final Point3 origin;
    public final Vector3 direction;

    public Ray(Point3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Point3 at(double t) {

        final Point3 result = origin.add(direction.mul(t));
        return result;

    }

    public double tOf(Point3 p) {

        final double result = (p.sub(origin).dot(direction)) / direction.dot(direction);
        return result;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ray ray = (Ray) o;

        if (origin != null ? !origin.equals(ray.origin) : ray.origin != null) return false;
        return !(direction != null ? !direction.equals(ray.direction) : ray.direction != null);

    }

    @Override
    public int hashCode() {
        int result = origin != null ? origin.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "origin=" + origin +
                ", direction=" + direction +
                '}';
    }
}
