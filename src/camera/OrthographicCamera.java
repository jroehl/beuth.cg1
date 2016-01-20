package camera;

import java.util.HashSet;
import java.util.Set;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point2;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Die Klassen OrthographicCamera und PespectiveCamera stellen jeweils eine
 * perspektivische und orthographische Kamera dar. Die orthographische Kamera
 * hat als weiteren Parameter den Skalierungfaktor der Bildebene.
 */
public class OrthographicCamera extends Camera {

	/**
	 * s - double
	 */
	public final double s;
	final SamplingPattern p;

	/**
	 * Konstruktor: OrthographicCamera
	 *
	 * @param e
	 *            repräsentiert den Ursprung des Strahls
	 * @param g
	 *            repräsentiert die Blickrichtung (vector3(0,0,1))
	 * @param t
	 *            repräsentiert den up-Vektor (vector3(0,1,0))
	 * @param s
	 */
	public OrthographicCamera(final Point3 e, final Vector3 g, final Vector3 t, final double s, final SamplingPattern p) {
		super(e, g, t, p);
		this.s = s;
		this.p = p;

	}

	/**
	 * Method: rayFor
	 *
	 * @param w
	 *            int der Kamera
	 * @param h
	 *            int der Kamera
	 * @param x
	 *            int der Kamera
	 * @param y
	 *            int der Kamera
	 * @return null
	 */
	@Override
	public Set<Ray> rayFor(final int w, final int h, final int x, final int y) {
		final Set<Ray> raySet = new HashSet<Ray>();
		final Set<Point2> points = this.p.generateSamples();
		for (final Point2 po : points) {
			final double a = (double) w / h;
			// zu den x - y- Werten des jeweiligen Pixels werden die x-&y-Werte
			// des jeweiligen Point2 addiert (diese liegen immer zw -0.5 und 0.5
			final double doub1 = ((x + po.x - (w - 1) / 2)) / (w - 1);
			final double doub2 = ((y + po.y - (h - 1) / 2)) / (h - 1);
			final Point3 o = e.add((u.mul(doub1).mul(a).mul(s)).add(v.mul(doub2).mul(s)));
			final Ray ray = new Ray(o, super.w.mul(-1.0)); // vector d = vector
															// w * (-1)
			raySet.add(ray);
		}
		return raySet;

	}
	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String OrthographicCamera Werte
	 */
	@Override
	public String toString() {
		return "OrthographicCamera [s=" + s + "]";
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
		long temp;
		temp = Double.doubleToLongBits(s);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		final OrthographicCamera other = (OrthographicCamera) obj;
		if (Double.doubleToLongBits(s) != Double.doubleToLongBits(other.s))
			return false;
		return true;
	}
}
