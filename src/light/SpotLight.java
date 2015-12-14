package light;

import geometries.Geometry;
import geometries.Hit;
import ray.Ray;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

/**
 * SpotLight
 *
 * @author Waschmaschine
 *         <p>
 *         Feste Position, eine Hauptrichtung, strahlt innerhalb eines
 *         bestimmten Winkels
 */
public class SpotLight extends Light {

    private final Vector3 direction;
    private final Point3 position;
    private final double halfAngle;

    /**
     * Konstruktor: SpotLight
     *
     * @param position     Point3 des Lichts
     * @param color        Color des Lichts
     * @param direction    Vector3 des Lichts
     * @param halfAngle    double Winkel des Lichts
     * @param castsShadows boolean - ob Gegenstand einen Schatten wirft
     * @throws IllegalArgumentException
     */
    public SpotLight(Color color, Vector3 direction, Point3 position, double halfAngle, boolean castsShadows)
            throws IllegalArgumentException {

        super(color, castsShadows);

        if (direction == null) {
            throw new IllegalArgumentException("The direction cannot be null!");
        }

        if (position == null) {
            throw new IllegalArgumentException("The position cannot be null!");
        }
        this.direction = direction;
        this.position = position;
        this.halfAngle = halfAngle;
    }

    /**
     * Method: illuminates(Point3)
     *
     * @param p     Übergebenes Point3 - Objekt
     * @param world - ob World Objekt
     * @return true / false wenn der übergebene Punkt beleuchtetwird
     * @throws IllegalArgumentException
     */
    @Override
    public boolean illuminates(Point3 p, World world) throws IllegalArgumentException {

        if (p == null) {
            throw new IllegalArgumentException("The Point cannot be null!");
        }

        if (castsShadows) {
            final Ray r = new Ray(p, directionFrom(p));
            for (final Geometry g : world.objs) {

                if (g.hit(r) == null) {
                    if (Math.sin(position.sub(p).normalized().x(direction.normalized()).magnitude) <= halfAngle) {
                        final Hit hit = world.getHit(new Ray(p, directionFrom(p)));
                        if (hit != null) {
                            final double t1 = (position.sub(p).magnitude) / (directionFrom(p).magnitude);
                            if (hit.t > t1) {
                                return false;
                            }
                        }
                        if (hit == null) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Method: directionFrom(Point3)
     *
     * @param p Übergebenes Point3 - Objekt
     * @return Der zur Lichtquelle zeigende Vector3
     * @throws IllegalArgumentException
     */
    @Override
    public Vector3 directionFrom(Point3 p) throws IllegalArgumentException {
        if (p == null) {
            throw new IllegalArgumentException("The Point cannot be null!");
        }
        return direction.mul(-1.0).normalized();
    }

    /**
     * Ueberschriebene toString-Methode
     *
     * @return String SpotLight Werte
     */
    @Override
    public String toString() {
        return "SpotLight [direction=" + direction + ", position=" + position + ", halfAngle=" + halfAngle + ", color=" + color + "]";
    }

    /**
     * Ueberschriebene hashCode-Methode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((direction == null) ? 0 : direction.hashCode());
        long temp;
        temp = Double.doubleToLongBits(halfAngle);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    /**
     * Ueberschriebene equals-Methode
     *
     * @param o Objekt das mit der Matrix verglichen wird
     * @return true | false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SpotLight other = (SpotLight) obj;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (direction == null) {
            if (other.direction != null)
                return false;
        } else if (!direction.equals(other.direction))
            return false;
        if (Double.doubleToLongBits(halfAngle) != Double.doubleToLongBits(other.halfAngle))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }

}