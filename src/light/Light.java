package light;

import ray.World;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

/**
 * Light
 *
 * @author Waschmaschine
 *         <p>
 *         Die abstrakte Basisklasse Light speichert die Farbe des Lichts und
 *         deklariert die beiden Methoden illuminates und directionFrom. Die
 *         Methode illuminates ermittelt hierbei ob der übergebene Punkt von
 *         diesem Licht angestrahlt wird. Diese Methode wird zunächst für die
 *         Begrenzung des Winkels bei SpotLight benötigt. Die Methode
 *         directionFrom gibt für den übergebenen Punkt den Vektor v zurück, der
 *         zur Lichtquelle zeigt.
 */
public abstract class Light {

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (castsShadows ? 1231 : 1237);
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Light other = (Light) obj;
        if (castsShadows != other.castsShadows)
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        return true;
    }

    public Color color;
    public boolean castsShadows;

    public Light(Color color, boolean castsShadows) {
        this.color = color;
        this.castsShadows = castsShadows;
    }

    /**
     * Method: illuminates(Point3)
     *
     * @param p     Übergebenes Point3 - Objekt
     * @param world - ob World Objekt
     * @return true / false wenn der übergebene Punkt beleuchtet wird
     */
    public abstract boolean illuminates(Point3 p, World world) throws IllegalArgumentException;

    /**
     * Method: directionFrom(Point3)
     *
     * @param p Übergebenes Point3 - Objekt
     * @return Der zur Lichtquelle zeigende Vector3
     * @throws IllegalArgumentException
     */
    public abstract Vector3 directionFrom(Point3 p) throws IllegalArgumentException;

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Light [color=" + color + ", castsShadows=" + castsShadows + "]";
    }
}