package material;

import application.Tracer;
import color.Color;
import geometries.Hit;
import ray.World;

/**
 * Created by Waschmaschine on 01.12.15.
 */
public class ReflectiveMaterial extends Material {
    @Override
    public Color colorFor(Hit hit, World world, Tracer tracer) {
        return null;
    }
}
