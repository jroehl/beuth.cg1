package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

/**
 * DirectionalLight
 *
 * @author Waschmaschine
 *         <p>
 *         Das directional light stellt eine unendlich weit entfernte Lichtquelle dar. Die Richtung bleibt dabei überall gleich.
 */
public class DirectionalLight extends Light {

    private final Vector3 direction;

    /**
     * Konstruktor: DirectionalLight
     *
     * @param color     RGB Color der Lichts
     * @param direction Vector3 des Lichts
     * @throws IllegalArgumentException
     */
    public DirectionalLight(Color color, Vector3 direction) throws IllegalArgumentException {
        super(color);
        if (direction == null) {
            throw new IllegalArgumentException("The direction cannot be null!");
        }
        this.direction = direction;
    }

    /**
     * Method: illuminates(Point3)
     *
     * @param p Übergebenes Point3 - Objekt
     * @return true / false wenn der übergebene Punkt beleuchtet wird
     * @throws IllegalArgumentException
     */
    @Override
    public boolean illuminates(Point3 p) throws IllegalArgumentException {
        if (p == null) {
            throw new IllegalArgumentException("The point3 cannot be null!");
        }
        return true;
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
            throw new IllegalArgumentException("The point3 cannot be null!");
        }
        return direction.mul(-1).normalized();
    }

    /**
     * Ueberschriebene toString-Methode
     *
     * @return String DirectionalLight Werte
     */
    @Override
    public String toString() {
        return "DirectionalLight{" +
                "direction=" + direction +
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

        DirectionalLight that = (DirectionalLight) o;

        return !(direction != null ? !direction.equals(that.direction) : that.direction != null);

    }

    /**
     * Ueberschriebene hashCode-Methode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }
}
