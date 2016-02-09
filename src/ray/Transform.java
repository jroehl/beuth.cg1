package ray;

import Matrizen_Vektoren_Bibliothek.Mat4x4;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 *
 * @author waschmaschine
 *
 *         Das Transformobjekt implementiert alle zur Transformierung von Objekten benötigten Methoden Es erwartet im
 *         Konstruktor zwei Mat4x4: ein Mat4x4 udn ihre Inverse
 *
 */

public class Transform {

	/**
	 * die übergebene Transformationsmatrix
	 */

	public final Mat4x4 m;

	/**
	 * die Inverse der Transformationsmatrix
	 */
	public final Mat4x4 i;

	/**
	 * Konstruktor: transform
	 *
	 * werden keine Matrizen übergeben, wird die Transformationsmatrix mit der Einheitsmatrix initialisiert und die
	 * Inverse davon (ebenfalls die Einheitsmatrix) für i eingesetzt
	 */
	public Transform() {
		this.m = new Mat4x4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); // Einheitsmatrix
		this.i = m;
	}

	/**
	 * Konstruktor: transform(Mat4x4, Mat4x4)
	 *
	 * @param m
	 *            Transformationsmatrix
	 * @param i
	 *            Inverse der Transformationsmatrix
	 */
	private Transform(Mat4x4 m, Mat4x4 i) {
		this.m = m;
		this.i = i;
	}

	/**
	 * Methode translate(Point3)
	 *
	 * @param p
	 *            übergebener Point3, an welchen das Objekt verschoben werden soll
	 * @return veränderte Matrix und Inverse dieser Matrix
	 */
	public Transform translate(Point3 p) {
		final Transform t = new Transform(new Mat4x4(1, 0, 0, p.x, 0, 1, 0, p.y, 0, 0, 1, p.z, 0, 0, 0, 1),
				new Mat4x4(1, 0, 0, -p.x, 0, 1, 0, -p.y, 0, 0, 1, -p.z, 0, 0, 0, 1));

		return new Transform(m.mul(t.m), t.i.mul(i));
	}

	/**
	 * Methode scale(doublex, doubley, doublez)
	 *
	 * @param x
	 *            Scalieringsfaktor für die X-Achse
	 *
	 * @param y
	 *            Scalieringsfaktor für die Y-Achse
	 *
	 * @param z
	 *            Scalieringsfaktor für die Z-Achse
	 * @return veränderte Matrix und Inverse dieser Matrix
	 */
	public Transform scale(double x, double y, double z) {
		final Transform t = new Transform(new Mat4x4(x, 0, 0, 0, 0, y, 0, 0, 0, 0, z, 0, 0, 0, 0, 1),
				new Mat4x4(1.0 / x, 0, 0, 0, 0, 1.0 / y, 0, 0, 0, 0, 1.0 / z, 0, 0, 0, 0, 1));

		return new Transform(m.mul(t.m), t.i.mul(i));
	}

	/**
	 * Methode scale(doublex, doubley, doublez)
	 *
	 * @param angle
	 *            Winkel für die Rotation um die X-Achse
	 *
	 * @return veränderte Matrix und Inverse dieser Matrix
	 */
	public Transform rotateX(double angle) {
		final Transform t = new Transform(
				new Mat4x4(1, 0, 0, 0, 0, Math.cos(angle), -Math.sin(angle), 0, 0, Math.sin(angle), Math.cos(angle), 0, 0, 0, 0, 1),
				new Mat4x4(1, 0, 0, 0, 0, Math.cos(angle), Math.sin(angle), 0, 0, -Math.sin(angle), Math.cos(angle), 0, 0, 0, 0, 1));
		return new Transform(m.mul(t.m), t.i.mul(i));
	}

	/**
	 * Methode scale(doublex, doubley, doublez)
	 *
	 * @param angle
	 *            Winkel für die Rotation um die Y-Achse
	 *
	 * @return veränderte Matrix und Inverse dieser Matrix
	 */
	public Transform rotateY(double angle) {
		final Transform t = new Transform(
				new Mat4x4(Math.cos(angle), 0, Math.sin(angle), 0, 0, 1, 0, 0, -Math.sin(angle), 0, Math.cos(angle), 0, 0, 0, 0, 1),
				new Mat4x4(Math.cos(angle), 0, -Math.sin(angle), 0, 0, 1, 0, 0, Math.sin(angle), 0, Math.cos(angle), 0, 0, 0, 0, 1));
		return new Transform(m.mul(t.m), t.i.mul(i));
	}

	/**
	 * Methode scale(doublex, doubley, doublez)
	 *
	 * @param angle
	 *            Winkel für die Rotation um die Z-Achse
	 *
	 * @return veränderte Matrix und Inverse dieser Matrix
	 */
	public Transform rotateZ(double angle) {
		final Transform t = new Transform(
				new Mat4x4(Math.cos(angle), -Math.sin(angle), 0, 0, Math.sin(angle), Math.cos(angle), 0, 0, 0, 0, 1, 0, 0, 0, 0, 1),
				new Mat4x4(Math.cos(angle), Math.sin(angle), 0, 0, -Math.sin(angle), Math.cos(angle), 0, 0, 0, 0, 1, 0, 0, 0, 0, 1));

		return new Transform(m.mul(t.m), t.i.mul(i));
	}

	/**
	 * Methode mul(Normal3)
	 *
	 * @param n
	 *            übergebene Normal3
	 * @return transformierte Normale des jeweiligen Punktes
	 */
	public Normal3 mul(Normal3 n) {
		return i.transposed().mul(new Vector3(n.x, n.y, n.z)).normalized().asNormal();
	}

	/**
	 * Methode mul(Ray)
	 *
	 * @param n
	 *            übergebener Ray
	 * @return transformierter Ray
	 */
	public Ray mul(Ray r) {
		return new Ray(i.mul(r.origin), i.mul(r.direction));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((m == null) ? 0 : m.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Transform other = (Transform) obj;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		if (m == null) {
			if (other.m != null)
				return false;
		} else if (!m.equals(other.m))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transform [m=" + m + ", i=" + i + "]";
	}
}