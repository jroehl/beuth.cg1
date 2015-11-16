package geometries;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

/**
 * Plane
 *
 * @author Waschmaschine
 *         <p>
 *         Die von Geometry abgeleitete Klasse Plane implementiert
 *         die Methode hit entsprechend der Formeln und Algorithmen zur Schnittberechnung.
 */
public class Plane extends Geometry {

    /**
     * a - Point3 Objekt der Ebene
     */
    public final Point3 a;
    /**
     * n - Normal3 Objekt der Ebene
     */
    public final Normal3 n;

    /**
     * Konstruktor: Plane
     *
     * @param color color Objekt der Geometrie
     * @param a     Point3 Objekt der Ebene
     * @param n     Normal3 Objekt der Ebene
     * @throws IllegalArgumentException
     */
    public Plane(Color color, Point3 a, Normal3 n) throws IllegalArgumentException {
        super(color);

        if (a == null) {
            throw new IllegalArgumentException("The a cannot be null!");
        }
        if (n == null) {
            throw new IllegalArgumentException("The n cannot be null!");
        }

        this.a = a;
        this.n = n;

    }

    /**
     * Method: hit(ray)
     *
     * @param ray Ray Objekt
     * @return Hit / null
     * Bei einem Treffer wird das generierte Hit Objekt zurückgegeben und null vice versa
     * @throws IllegalArgumentException
     */
    @Override
    public Hit hit(Ray ray) throws IllegalArgumentException {

        if (ray == null) {
            throw new IllegalArgumentException("The Ray cannot be null!");
        }

        // denonimator / nenner
        final double nenner = ray.direction.dot(n);

        if (nenner != 0) {
            final double t = n.dot(a.sub(ray.origin)) / nenner; // ich glaube
            // hier war ein
            // fehle
            if (t > 0) {
                return new Hit(t, ray, this);
            }
        }
        return null;
    }

    /**
     * Ueberschriebene equals-Methode
     *
     * @param o Objekt das mit der Matrix verglichen wird
     * @return true | false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final Plane plane = (Plane) o;

        if (a != null ? !a.equals(plane.a) : plane.a != null)
            return false;
        return !(n != null ? !n.equals(plane.n) : plane.n != null);

    }

    /**
     * Ueberschriebene hashCode-Methode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (n != null ? n.hashCode() : 0);
        return result;
    }

    /**
     * Ueberschriebene toString-Methode
     *
     * @return String Plane Werte
     */
    @Override
    public String toString() {
        return "Plane{" + "a=" + a + ", n=" + n + '}';
    }
}
