package camera;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class OrthographicCamera extends Camera {

	public final double s;

	/**
	 *
	 * @param e
	 *            repräsentiert den Ursprung des Strahls
	 * @param g
	 *            repräsentiert die Blickrichtung (vector3(0,0,1))
	 * @param t
	 *            repräsentiert den up-Vektor (vector3(0,1,0))
	 * @param s
	 */
	public OrthographicCamera(final Point3 e, final Vector3 g, final Vector3 t,
			final double s) {
		super(e, g, t);
		this.s = s;

	}

	/**
	 *
	 */
	@Override
	public Ray rayFor(final int w, final int h, final int x, final int y) {
		final double a = w / h;
		final double doub1 = (s * ((x - (w - 1) / 2)) / (w - 1));
		final double doub2 = (s * ((y - (h - 1) / 2)) / (w - 1));
		final Point3 o = e.add((u.mul(doub1).mul(a)).add(v.mul(doub2)));
		return new Ray(o, super.w.mul(-1)); // vector d = vector w * (-1)
	}

	/**
	 *
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
