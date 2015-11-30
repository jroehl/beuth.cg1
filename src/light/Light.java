package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

/**
 * Light
 *
 * @author Waschmaschine
 *         <p>
 *         Die abstrakte Basisklasse Light speichert die Farbe des Lichts und deklariert die beiden Methoden illuminates und directionFrom.
 *         Die Methode illuminates ermittelt hierbei ob der übergebene Punkt von diesem Licht angestrahlt wird.
 *         Diese Methode wird zunächst für die Begrenzung des Winkels bei SpotLight benötigt.
 *         Die Methode directionFrom gibt für den übergebenen Punkt den Vektor v zurück, der zur Lichtquelle zeigt.
 */
public abstract class Light {

    /**
     * Color - color Objekt des Lichts
     */
    public final Color color;

    /**
     * Konstruktor: Light
     *
     * @param color RGB Color der Lichts
     * @throws IllegalArgumentException
     */
    public Light(Color color) {
        this.color = color;
    }

    /**
     * Method: illuminates(Point3)
     *
     * @param p Übergebenes Point3 - Objekt
     * @return true / false wenn der übergebene Punkt beleuchtet wird
     */
    public abstract boolean illuminates(Point3 p) throws IllegalArgumentException;

    /**
     * Method: directionFrom(Point3)
     *
     * @param p Übergebenes Point3 - Objekt
     * @return Der zur Lichtquelle zeigende Vector3
     * @throws IllegalArgumentException
     */
    public abstract Vector3 directionFrom(Point3 p) throws IllegalArgumentException;

    /**
     * Ueberschriebene toString-Methode
     *
     * @return String Light Werte
     */
    @Override
    public String toString() {
        return "Light{" +
                "color=" + color +
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

        Light light = (Light) o;

        return !(color != null ? !color.equals(light.color) : light.color != null);

    }

    /**
     * Ueberschriebene hashCode-Methode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}
