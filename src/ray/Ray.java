package ray;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Repräsentiert einen Strahl (Ray)
 *
 * @author Waschmaschine
 */
public class Ray {

	/**
	 * origin - Point3 Ursprung des Strahls
	 */
	public final Point3 origin;
	/**
	 * direction - Point3 Richtung des Strahls
	 */
	public final Vector3 direction;

	/**
	 * Der Konstruktor dieser Klasse nimmt als Parameter den Ursprung und die Richtung des Strahls entgegen.
	 *
	 * @param origin
	 *            repräsentiert den übergebenen Ursprung des Strahls
	 * @param direction
	 *            repräsentiert die übergebene Richtung des Strahls
	 * @throws IllegalArgumentException
	 */
	public Ray(Point3 origin, Vector3 direction) throws IllegalArgumentException {

		if (origin == null) {
			throw new IllegalArgumentException("The origin cannot be null!");
		}
		if (direction == null) {
			throw new IllegalArgumentException("The direction cannot be null!");
		}

		this.origin = origin;
		this.direction = direction;
	}

	/**
	 * Der Strahl hat die Methode at. Diese nimmt als Parameter ein t entgegen und gibt den entsprechenden Punkt.
	 * (p=o+td)
	 *
	 * @param t
	 *            repräsentiert den Parameter t, über welchen ein Punkt auf einem vektor definiert wird
	 * @return
	 */
	public Point3 at(double t) {
		return origin.add(direction.mul(t));

	}

	/**
	 * Darüber hinaus hat der Strahl die Methode tOf, welche einen Punkt als Parameter annimmt und das entsprechende t
	 * zuueückgibt.
	 * <p>
	 * t = (p-o)/d
	 *
	 * @param point
	 *            repräsentiert den übergebenen Punkt p
	 * @return double t - den Faktor, mit welchem der Vektor multipliziert wurde, um einen bestimmten Punkt zu markieren
	 * @throws IllegalArgumentException
	 */
	public double tOf(Point3 point) throws IllegalArgumentException {
		if (point == null) {
			throw new IllegalArgumentException("The Point cannot be null!");
		}
		return (point.sub(origin).dot(direction)) / direction.dot(direction);
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