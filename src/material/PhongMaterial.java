package material;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import geometries.Hit;
import light.Light;
import ray.World;
import color.Color;

public class PhongMaterial extends Material {
    private final Color diffuse;
    private final Color specular;
    private final int exponent;

    public PhongMaterial(Color diffuse, Color specular, int exponent) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.exponent = exponent;
    }

    @Override
    public Color colorFor(Hit hit, World world) {

        Color returnColor = diffuse.mul(world.ambient);

        for (Light light : world.lights) {

            Color lightColor = light.color;

            final Vector3 lightVector = light.directionFrom(
                    hit.ray.at(hit.t)).normalized();

            final Vector3 reflectedVector = lightVector.reflectedOn(hit.n);

            final Vector3 e = (hit.ray.direction.mul(-1)).normalized();

            final double max = Math.max(0.0, lightVector.dot(hit.n));

            returnColor = returnColor.add(diffuse.mul(lightColor).mul(max));

            returnColor = returnColor.add(specular.mul(lightColor.mul(Math.pow(Math.max(0.0, reflectedVector.dot(e)), exponent))));

        }

        return returnColor;


    }

}
