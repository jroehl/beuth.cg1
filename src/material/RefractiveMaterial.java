package material;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import application.Tracer;
import color.Color;
import geometries.Hit;
import ray.Ray;
import ray.World;

/**
 * Created by jroehl on 27.01.16.
 */
public class RefractiveMaterial extends Material {


    private double indexOfRefraction;
    final double u = 0.000000001;

    public RefractiveMaterial(double indexOfRefraction) {
        this.indexOfRefraction = indexOfRefraction;
    }

    @Override
    public Color colorFor(final Hit hit, final World world,
                          final Tracer tracer) {

        Normal3 normal = hit.n;

        final Vector3 e = hit.ray.direction.mul(-1).reflectedOn(normal);

        double eta;
        if (e.dot(normal) < 0) {
            eta = indexOfRefraction / world.refractionIndex;
            normal = normal.mul(-1);
        } else {
            eta = world.refractionIndex / indexOfRefraction;
        }

        final double cosTheta = normal.dot(e);

        final double h = 1 - (eta * eta) * (1 - cosTheta * cosTheta);

        if (h < 0) {
            return tracer.reflectedColors(new Ray(hit.ray.at(hit.t - u), e));
        } else {

            final double costhetat = Math.sqrt(h);
            final Vector3 t = hit.ray.direction.mul(eta).sub(normal.mul(costhetat - eta * cosTheta));

            final double r0 = Math.pow((world.refractionIndex - indexOfRefraction) / (world.refractionIndex + indexOfRefraction), 2);
            final double rr = r0 + (1 - r0) * Math.pow(1 - cosTheta, 5);
            final double tt = 1 - rr;

            return tracer.reflectedColors(new Ray(hit.ray.at(hit.t - u), e)).mul(rr)
                    .add(tracer.reflectedColors(new Ray(hit.ray.at(hit.t + u), t)).mul(tt));
        }


    }
}

