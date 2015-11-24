package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;
import geometries.Hit;
import ray.Ray;

public class SpotLight extends Light {

    private final Vector3 direction;
    private final Point3 position;
    private final double halfAngle;

    public SpotLight(Color color, Vector3 direction, Point3 position,
                     double halfAngle) {
        super(color);
        this.direction = direction;
        this.position = position;
        this.halfAngle = halfAngle;
    }

    @Override
    public boolean illuminates(Point3 p) {

        if (p == null) {
            throw new IllegalArgumentException("The Point cannot be null!");
        }

//		if (Math.sin(position.sub(p).normalized().x(direction).magnitude) <= halfAngle) {
//			return true;
//		}

        if (Math.cos(p.sub(position).normalized().dot(direction)) <= halfAngle) {
            return true;
        }

        return false;

    }

    @Override
    public Vector3 directionFrom(Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("The Point cannot be null!");
        }
        return direction.mul(-1.0).normalized();
    }

}
