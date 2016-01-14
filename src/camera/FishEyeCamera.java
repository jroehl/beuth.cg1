package camera;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Die Klassen OrthographicCamera und PespectiveCamera stellen jeweils eine
 * perspektivische und orthographische Kamera dar. Die perspektivische Kamera
 * hat als weiteren Parameter den Öffnungswinkel.
 */
public class FishEyeCamera extends Camera {

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
	public FishEyeCamera(Point3 e, Vector3 g, Vector3 t, double angle, SamplingPattern p) {
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
	public Ray rayFor(int w, int h, int x, int y) {

		final float px = 1.0f * (x - 0.5f * w);
		final float py = 1.0f * (y - 0.5f * h);
		Vector3 direction = null;
		final float x2 = 2.0f / ((w * h) * h) * px;
		final float y2 = 2.0f / ((w * h) * w) * py;
		final float rSquared = (float) Math.pow(x2, 2) + (float) Math.pow(y2, 2);
		if (rSquared <= 1) {

			final float r2 = (float) Math.sqrt(rSquared);
			final float psi = r2 * 1.0f * ((float) (Math.PI / 180));
			final float sinPsi = (float) Math.sin(psi);
			final float cosPsi = (float) Math.cos(psi);
			final float sinAlpha = y / r2;
			final float cosAlpha = x / r2;
			direction = new Vector3((sinPsi * cosAlpha), (sinPsi * sinAlpha), cosPsi);
			System.out.println(direction);
		} else {

			direction = new Vector3(0, 0, 0);
		}

		return new Ray(e, direction.normalized());

		// final Vector3 ux = u.mul(x - ((w - 1) / 2));
		// final Vector3 vy = v.mul(y - ((h - 1) / 2));
		// final Vector3 r = this.w.mul(-1).mul((h / 2) / Math.tan(angle /
		// 2)).add(ux.add(vy));
		//
		// return new Ray(e, r.normalized());
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
		final FishEyeCamera other = (FishEyeCamera) obj;
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
