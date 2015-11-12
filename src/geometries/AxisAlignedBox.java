package geometries;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

import java.util.HashSet;

public class AxisAlignedBox extends Geometry {

    public final Point3 lbf;
    public final Point3 run;

    public AxisAlignedBox(Color color, Point3 lbf, Point3 run) {
        super(color);
        this.lbf = lbf;
        this.run = run;
    }

    @Override
    public Hit hit(Ray ray) {

        final Hit[] xHits = new Hit[]{new Plane(color, new Point3(lbf.x, lbf.y, lbf.z), new Normal3(run.x, run.y, run.z)).hit(ray)};
//        final Hit[] yHits = new Hit[]{Plane.hit(ray), bottom.hit(r)};
//        final Hit[] zHits = new Hit[]{front.hit(r), far.hit(r)};

        final HashSet<Hit> hits = new HashSet<Hit>();

        for (
                int i = 0;
                i < 2; i++)

        {
            if (xHits[i] != null) {
                final Point3 p = ray.at(xHits[i].t);
                if (p.y >= lbf.y && p.y <= run.y && p.z >= lbf.z && p.z <= run.z) hits.add(xHits[i]);
            }
        }

        for (
                int i = 0;
                i < 2; i++)

        {
            if (yHits[i] != null) {
                final Point3 p = ray.at(yHits[i].t);
                if (p.x >= lbf.x && p.x <= run.x && p.z >= lbf.z && p.z <= run.z) hits.add(yHits[i]);
            }
        }

        for (
                int i = 0;
                i < 2; i++)

        {
            if (zHits[i] != null) {
                final Point3 p = ray.at(zHits[i].t);
                if (p.x >= lbf.x && p.x <= run.x && p.y >= lbf.y && p.y <= run.y) hits.add(zHits[i]);
            }
        }


        double t = Double.MAX_VALUE;

        Hit returnHit = null;

        for (
                Hit hit
                : hits)

        {
            if (hit == null) continue;
            if (hit.t < t && t > 0 && hit.t > 0) {
                t = hit.t;
                returnHit = hit;
            }
        }

        return returnHit;

    }
}
