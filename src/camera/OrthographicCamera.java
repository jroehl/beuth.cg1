package camera;

import java.util.HashSet;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import ray.Ray;

/**
 * Die Klassen OrthographicCamera und PespectiveCamera stellen jeweils eine perspektivische und orthographische Kamera
 * dar. Die orthographische Kamera hat als weiteren Parameter den Skalierungfaktor der Bildebene.
 */
public class OrthographicCamera extends Camera {

	/**
	 * skalierungsfaktor s - double
	 */
	public final double s;

	/**
	 * p - SamplingPattern
	 */
	public final SamplingPattern p;

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
	 * Liefert den Strahl für den übergebenen Pixel
	 *
	 * @param w
	 *            Breite des Bildes
	 * @param h
	 *            Höhe des Bildes
	 * @param x
	 *            X-Koordinate des Pixels für den der Strahl generiert werden soll.
	 * @param y
	 *            Y-Koordinate des Pixels für den der Strahl generiert werden soll.
	 * @return Set<Ray>
	 */
	@Override
	public Set<Ray> rayFor(final int w, final int h, final int x, final int y) {
		final Set<Ray> raySet = new HashSet<Ray>();
		final Set<Point2> points = this.p.generateSamples();

		for (final Point2 po : points) {
			final double a = (double) w / h;
			final double doub1 = ((x + po.x - (w - 1) / 2)) / (w - 1);
			final double doub2 = ((y + po.y - (h - 1) / 2)) / (h - 1);

			final Point3 o = e.add((u.mul(doub1).mul(a).mul(s)).add(v.mul(doub2).mul(s)));

			final Ray ray = new Ray(o, super.w.mul(-1.0));
			raySet.add(ray);
		}
		return raySet;
	}

	@Override
	public String toString() {
		return "OrthographicCamera [s=" + s + ", p=" + p + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		long temp;
		temp = Double.doubleToLongBits(s);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrthographicCamera other = (OrthographicCamera) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (Double.doubleToLongBits(s) != Double.doubleToLongBits(other.s))
			return false;
		return true;
	}
}