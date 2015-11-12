package ray;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class Ray {

	public final Point3 origin; // Ursprung
	public final Vector3 direction;// Richtung

	/**
	 * Der Konstruktor dieser Klasse nimmt als Parameter den Ursprung und die
	 * Richtung des Strahls entgegen.
	 *
	 * @param origin
	 *            repräsentiert den übergebenen Ursprung des Strahls
	 * @param direction
	 *            repräsentiert die übergebene Richtung des Strahls
	 */
	public Ray(Point3 origin, Vector3 direction) {
		this.origin = origin;
		this.direction = direction;
	}

	/**
	 * Der Strahl hat die Methode at. Diese nimmt als Parameter ein 𝑡 entgegen
	 * und gibt den entsprechenden Punkt. (p=o+td)
	 *
	 * @param t
	 *            repräsentiert den Parameter t, über welchen ein Punkt auf
	 *            einem vektor definiert wird
	 * @return
	 */
	public Point3 at(double t) {
		return origin.add(direction.mul(t));

    }

	/**
	 * Darüber hinaus hat der Strahl die Methode tOf, welche einen Punkt als
	 * Parameter annimmt und das entsprechende 𝑡 zurückgibt.
	 *
	 * t = (p-o)/d
	 *
	 * @param point
	 *            repräsentiert den übergebenen Punkt p
	 * @return double t - den Faktor, mit welchem der Vektor multipliziert
	 *         wurde, um einen bestimmten Punkt zu markieren
	 */
	public double tOf(Point3 point) {
		return point.sub(origin).magnitude / direction.magnitude;

    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
		final Ray other = (Ray) obj;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ray [origin=" + origin + ", direction=" + direction + "]";
	}

}
