package ray;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class Ray {

	public final Point3 o; // Ursprung
	public final Vector3 d;// Richtung

	/**
	 * Der Konstruktor dieser Klasse nimmt als Parameter den Ursprung und die
	 * Richtung des Strahls entgegen.
	 *
	 * @param o
	 *            repräsentiert den übergebenen Ursprung des Strahls
	 * @param d
	 *            repräsentiert die übergebene Richtung des Strahls
	 */
	public Ray(Point3 o, Vector3 d) {
		this.o = o;
		this.d = d;
	}

	/**
	 * Der Strahl hat die Methode at. Diese nimmt als Parameter ein 𝑡 entgegen
	 * und gibt den entsprechenden Punkt.
	 *
	 * @param t
	 *            repräsentiert den Parameter t, über welchen ein Punkt auf
	 *            einem vektor definiert wird
	 * @return
	 */
	public Point3 at(double t) {
		return o.add(d.mul(t));

	}

	/**
	 * Darüber hinaus hat der Strahl die Methode tOf, welche einen Punkt als
	 * Parameter annimmt und das entsprechende 𝑡 zurückgibt.
	 *
	 * t = (p-o)/d
	 *
	 * @param p
	 *            repräsentiert den übergebenen Punkt p
	 * @return double t - den Faktor, mit welchem der Vektor multipliziert
	 *         wurde, um einen bestimmten Punkt zu markieren
	 */
	public double tOf(Point3 p) {
		return p.sub(o).magnitude / d.magnitude;

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
		result = prime * result + ((d == null) ? 0 : d.hashCode());
		result = prime * result + ((o == null) ? 0 : o.hashCode());
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
		if (d == null) {
			if (other.d != null)
				return false;
		} else if (!d.equals(other.d))
			return false;
		if (o == null) {
			if (other.o != null)
				return false;
		} else if (!o.equals(other.o))
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
		return "Ray [o=" + o + ", d=" + d + "]";
	}

}
