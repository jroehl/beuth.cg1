/**
 * Created by jroehl on 10.11.15.
 */

import color.Color;
import geometries.Geometry;
import geometries.Hit;
import ray.Ray;

import java.util.ArrayList;


public class World {

    public final ArrayList<Color> colorList;

    public final ArrayList<Geometry> geoList;

    public final Color backgroundColor;


    public World(final ArrayList<Geometry> geos,final ArrayList<Color> colors) {
        backgroundColor = new Color(0,0,0);
        geoList = geos;
        colorList = colors;
    }

    public Hit hit(final Ray ray) {

        final ArrayList<Hit> hits = new ArrayList<Hit>();

        for (Geometry geo : geoList) {
            if (geo.hit(ray) != null) {
                hits.add(geo.hit(ray));
            }
        }

        if (hits.isEmpty()) {
            return null;
        } else {
            double tmin = Double.MAX_VALUE;

            Hit returnHit = null;
            for (Hit hit : hits) {
                if (hit.t < tmin) {
                    tmin = hit.t;
                    if (tmin < 0){
                        tmin = 0;
                    }
                    returnHit = hit;
                }
            }
            return returnHit;
        }

    }
}