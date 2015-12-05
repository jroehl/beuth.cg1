package camera;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Die abstrakte Basisklasse Camera speichert die drei Vektoren u, v und w,
 * welche vom Konstruktor entgegen genommen werden. Im Konstruktor werden
 * hieraus die Vektoren u,v und w berechnet, welche ebenfalls als öffentliche
 * Attribute zur Verfügung gestellt werden. Die abstrakte Klasse Camera
 * deklariert die Methode rayFor, welche für einen bestimmten Pixel den Strahl
 * zurückgibt. Die Parameter w und h geben hierbei die Höhe und Breite des
 * Bildes an. Die Parameter x und y geben die Koordinaten des Pixels an, für den
 * der Strahl generiert werden soll.
 */
public abstract class Camera {
	public final Point3 e;
	public final Vector3 g;
	public final Vector3 t;
	public final Vector3 u;
	public final Vector3 v;
	public final Vector3 w;

	/**
	 * Konstruktor: Camera
	 *
	 * @param e
	 *            Point3 der Kamera
	 * @param g
	 *            Vector3 der Kamera
	 * @param t
	 *            Vector3 der Kamera
	 * @throws IllegalArgumentException
	 */
	public Camera(final Point3 e, final Vector3 g, final Vector3 t) throws IllegalArgumentException {

		if (e == null) {
			throw new IllegalArgumentException("The Point  e cannot be null!");
		}
		if (g == null) {
			throw new IllegalArgumentException("The Vector g cannot be null!");
		}
		if (t == null) {
			throw new IllegalArgumentException("The Vector t cannot be null!");
		}

		this.e = e;
		this.g = g;
		this.t = t;

		this.w = g.normalized().mul(-1);
		this.u = ((t.x(w)).mul((t.x(w)).magnitude)).normalized();
		this.v = w.x(u);

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
	public Ray rayFor(final int w, final int h, final int x, final int y) {
		return null;
	}

	@Override
	public String toString() {
		return "Camera{" + "e=" + e + ", g=" + g + ", t=" + t + ", u=" + u + ", v=" + v + ", w=" + w + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Camera camera = (Camera) o;

		if (e != null ? !e.equals(camera.e) : camera.e != null)
			return false;
		if (g != null ? !g.equals(camera.g) : camera.g != null)
			return false;
		if (t != null ? !t.equals(camera.t) : camera.t != null)
			return false;
		if (u != null ? !u.equals(camera.u) : camera.u != null)
			return false;
		if (v != null ? !v.equals(camera.v) : camera.v != null)
			return false;
		return !(w != null ? !w.equals(camera.w) : camera.w != null);

	}

	@Override
	public int hashCode() {
		int result = e != null ? e.hashCode() : 0;
		result = 31 * result + (g != null ? g.hashCode() : 0);
		result = 31 * result + (t != null ? t.hashCode() : 0);
		result = 31 * result + (u != null ? u.hashCode() : 0);
		result = 31 * result + (v != null ? v.hashCode() : 0);
		result = 31 * result + (w != null ? w.hashCode() : 0);
		return result;
	}
}
