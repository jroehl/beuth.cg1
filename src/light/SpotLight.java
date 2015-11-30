package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;
import geometries.Hit;
import ray.Ray;

/**
 * SpotLight
 *
 * @author Waschmaschine
 *         <p>
 *         Feste Position, eine Hauptrichtung, strahlt innerhalb eines bestimmten Winkels
 */
public class SpotLight extends Light {

    private final Vector3 direction;
    private final Point3 position;
    private final double halfAngle;

    /**
     * Konstruktor: SpotLight
     *
     * @param position  Point3 des Lichts
     * @param color     Color des Lichts
     * @param direction Vector3 des Lichts
     * @param halfAngle double Winkel des Lichts
     * @throws IllegalArgumentException
     */
    public SpotLight(Color color, Vector3 direction, Point3 position,
                     double halfAngle) {
        super(color);
        if (direction == null) {
            throw new IllegalArgumentException("The direction cannot be null!");
        }
        this.direction = direction;
        if (position == null) {
            throw new IllegalArgumentException("The position cannot be null!");
        }
        this.position = position;
        this.halfAngle = halfAngle;
    }

    /**
     * Method: illuminates(Point3)
     *
     * @param p Übergebenes Point3 - Objekt
     * @return true / false wenn der übergebene Punkt beleuchtet wird
     * @throws IllegalArgumentException
     */
    @Override
    public boolean illuminates(Point3 p) {

        if (p == null) {
            throw new IllegalArgumentException("The Point cannot be null!");
        }

        if (Math.sin(position.sub(p).normalized().x(direction.normalized()).magnitude) <= halfAngle) {
            return true;
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
    public Vector3 directionFrom(Point3 p) {
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
        return "SpotLight{" +
                "direction=" + direction +
                ", position=" + position +
                ", halfAngle=" + halfAngle +
                '}';
    }

    /**
     * Ueberschriebene equals-Methode
     *
     * @param o Objekt das mit der Matrix verglichen wird
     * @return true | false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SpotLight spotLight = (SpotLight) o;

        if (Double.compare(spotLight.halfAngle, halfAngle) != 0) return false;
        if (direction != null ? !direction.equals(spotLight.direction) : spotLight.direction != null) return false;
        return !(position != null ? !position.equals(spotLight.position) : spotLight.position != null);

    }

    /**
     * Ueberschriebene hashCode-Methode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        temp = Double.doubleToLongBits(halfAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
