package camera;

import java.util.HashSet;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import ray.Ray;

/**
 * Die Klassen OrthographicCamera und PespectiveCamera stellen jeweils eine perspektivische und orthographische Kamera
 * dar. Die perspektivische Kamera hat als weiteren Parameter den Öffnungswinkel.
 */
public class PerspectiveCamera extends Camera {

	/**
	 * angle - Blickwinkel
	 */
	public final double angle;
	public final SamplingPattern p;

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
		this.p = p;
	}

	/**
	 * Method: rayFor
	 *
	 * @param w
	 *            Breite des Bilds
	 * @param h
	 *            Höhe des Bilds
	 * @param x
	 *            X-Koordinate des Pixels für den der Strahl generiert werden soll.
	 * @param y
	 *            Y-Koordinate des Pixels für den der Strahl generiert werden soll.
	 * @return null
	 */
	@Override
	public Set<Ray> rayFor(int w, int h, int x, int y) {
		final Set<Ray> raySet = new HashSet<Ray>();
		final Set<Point2> points = this.p.generateSamples();

		for (final Point2 po : points) {
			final double x1 = x + po.x;
			final double y1 = y + po.y;

			final Vector3 ux = u.mul(x1 - ((w - 1.0) / 2.0));
			final Vector3 vy = v.mul(y1 - ((h - 1.0) / 2.0));
			final Vector3 r = this.w.mul(-1.0).mul((h / 2.0) / Math.tan(angle / 2.0)).add(ux.add(vy));

			final Ray ray = new Ray(e, r.normalized());
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