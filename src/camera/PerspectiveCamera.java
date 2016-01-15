package camera;

import java.util.ArrayList;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point2;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Die Klassen OrthographicCamera und PespectiveCamera stellen jeweils eine
 * perspektivische und orthographische Kamera dar. Die perspektivische Kamera
 * hat als weiteren Parameter den Öffnungswinkel.
 */
public class PerspectiveCamera extends Camera {

	/**
	 * angle - Blickwinkel
	 */
	public final double angle;

	/**
	 * Konstruktor: PerspectiveCamera
	 *
	 * @param e
	 *            repräsentiert den Ursprung des Strahls
	 * @param g
	 *            repräsentiert die Blickrichtung (vector3(0,0,1))
	 * @param t
	 *            repräsentiert den up-Vektor (vector3(0,1,0))
	 * @param angle
	 *            repräsentiert den Winkel
	 */
	public PerspectiveCamera(Point3 e, Vector3 g, Vector3 t, double angle, SamplingPattern p) {
		super(e, g, t, p);
		this.angle = angle;

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
	public ArrayList<Ray> rayFor(int w, int h, int x, int y, SamplingPattern sp) {
		final ArrayList<Ray> raySet = new ArrayList<Ray>();
		final ArrayList<Point2> points = sp.generateSamples(new ArrayList<Point2>(), 9);
		for (int i = 0; i < points.size(); i++) {

			final Vector3 ux = u.mul(x + points.get(i).x - ((w - 1) / 2));
			final Vector3 vy = v.mul(y + points.get(i).y - ((h - 1) / 2));
			final Vector3 r = this.w.mul(-1).mul((h / 2) / Math.tan(angle / 2)).add(ux.add(vy));
			final Ray ray = new Ray(e, r.normalized());;

			raySet.add(ray);
		}
		return raySet;
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
		temp = Double.doubleToLongBits(angle);
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
		final PerspectiveCamera other = (PerspectiveCamera) obj;
		if (Double.doubleToLongBits(angle) != Double.doubleToLongBits(other.angle))
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
		return "PerspectiveCamera [angle=" + angle + "]";
	}
}
