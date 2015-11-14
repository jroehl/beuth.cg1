package geometries;

import java.util.ArrayList;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class AxisAlignedBox extends Geometry {

    private final Point3 lbf;
    private final Point3 run;

    public AxisAlignedBox(Color color, Point3 lbf, Point3 run) {
        super(color);
        this.lbf = lbf;
        this.run = run;
    }

    @Override
    public Hit hit(Ray ray) {

        final Plane b1 = new Plane(color, lbf, new Normal3(-1, 0, 0));
        final Plane b2 = new Plane(color, lbf, new Normal3(0, -1, 0));
        final Plane b3 = new Plane(color, lbf, new Normal3(0, 0, -1));

        final Plane f1 = new Plane(color, run, new Normal3(1, 0, 0));
        final Plane f2 = new Plane(color, run, new Normal3(0, 1, 0));
        final Plane f3 = new Plane(color, run, new Normal3(0, 0, 1));

        final Plane[] planes = {b1, b2, b3, f1, f2, f3};

        double tf = -1;

        for (final Plane plane : planes) {

            if (ray.origin.sub(plane.a).dot(plane.n) > 0) {
                // denonimator / nenner
                final double d = ray.direction.dot(plane.n);

                if (d != 0) {
                    final double t = plane.a.sub(ray.origin).dot(plane.n) / d;

                    if (t > tf) {
                        tf = t;
                    }
                }
            }
        }


        Hit hit = new Hit(tf, ray, this);
        final Point3 p = hit.ray.at(hit.t);
        final double eps = 0.00001;

        if ((lbf.x <= p.x + eps && p.x <= run.x + eps) &&
                (lbf.y <= p.y + eps && p.y <= run.y + eps) &&
                (lbf.z <= p.z + eps && p.z <= run.z + eps)
                ) {
            return hit;
        }

        return null;

    }

    @Override
    public String toString() {
        return "AxisAlignedBox{" +
                "lbf=" + lbf +
                ", run=" + run +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AxisAlignedBox that = (AxisAlignedBox) o;

        if (lbf != null ? !lbf.equals(that.lbf) : that.lbf != null) return false;
        return !(run != null ? !run.equals(that.run) : that.run != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lbf != null ? lbf.hashCode() : 0);
        result = 31 * result + (run != null ? run.hashCode() : 0);
        return result;
    }
}