package geometries;

import ray.Ray;
import color.Color;

/**
 * Geometry
 *
 * @author Waschmaschine
 *         <p>
 *         Die Geomotrie-Klasse beinhaltet als Attribut eine Farbe. Darüber hinaus deklariert sie die Methode hit, welche einen Strahl als Parameter entgegen nimmt.
 *         In einer Implementierung dieser Methode erfolgt die Schnittberechnung. Eine Implementierung gibt bei mehreren Schnittpunkten immer den mit dem kleinsten positiven t zurück.
 *         Wird kein Schnittpunkt gefunden, wird null zurückgegeben.
 */
public abstract class Geometry {

    /**
     * Color - color Objekt der Geometrie
     */
    public final Color color;

    /**
     * Konstruktor: Geometry
     *
     * @param color RGB Color der Geometrie
     * @throws IllegalArgumentException
     */
    public Geometry(Color color) throws IllegalArgumentException {

        if (color == null) {
            throw new IllegalArgumentException("The Color cannot be null!");
        }

        this.color = color;
    }

    /**
     * Method: hit(ray)
     *
     * @param ray Ray Objekt
     * @return Hit / null
     * Bei einem Treffer wird das generierte Hit Objekt zurückgegeben und null vice versa
     * @throws IllegalArgumentException
     */
    public abstract Hit hit(Ray ray) throws IllegalArgumentException;

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

        final Geometry geometry = (Geometry) o;

        return !(color != null ? !color.equals(geometry.color) : geometry.color != null);

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

    /**
     * Ueberschriebene toString-Methode
     *
     * @return String Geometry Werte
     */
    @Override
    public String toString() {
        return "Geometry{" +
                "color=" + color +
                '}';
    }
}


