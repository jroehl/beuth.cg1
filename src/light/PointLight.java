package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;
import ray.Ray;

/**
 * PointLight
 *
 * @author Waschmaschine
 *         <p>
 *         Strahlt gleichmäßig in alle Richtungen wie eine Lampe mit einer festen Position
 */
public class PointLight extends Light {

    private final Point3 pl;

    /**
     * Konstruktor: PointLight
     *
     * @param position Point3 des Lichts
     * @param cl       Color des Lichts
     * @throws IllegalArgumentException
     */
    public PointLight(Color cl, Point3 position) throws IllegalArgumentException {
        super(cl);
        if (position == null) {
            throw new IllegalArgumentException("The position cannot be null!");
        }
        this.pl = position;
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
        return pl.sub(p).normalized();
    }

    /**
     * Ueberschriebene toString-Methode
     *
     * @return String PointLight Werte
     */
    @Override
    public String toString() {
        return "PointLight{" +
                "pl=" + pl +
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

        PointLight that = (PointLight) o;

        return !(pl != null ? !pl.equals(that.pl) : that.pl != null);

    }

    /**
     * Ueberschriebene hashCode-Methode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (pl != null ? pl.hashCode() : 0);
        return result;
    }
}
