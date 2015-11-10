package geometries;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class Plane extends Geometry {

	public final Point3 a;
	public final Normal3 n;

	public Plane(Color color, Point3 a, Normal3 n) {
		super(color);
		this.a = a;
		this.n = n;

	}

	@Override
	public Hit hit(Ray ray) {

//		denonimator / nenner
		double d = ray.direction.dot(n);

		if(d != 0) {
			double t = a.sub(ray.origin).dot(n) / d;
			if (t < 0) {
				return null;
			}
			return new Hit(t, ray, this);
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Plane plane = (Plane) o;

		if (a != null ? !a.equals(plane.a) : plane.a != null) return false;
		return !(n != null ? !n.equals(plane.n) : plane.n != null);

	}

	@Override
	public int hashCode() {
		int result = a != null ? a.hashCode() : 0;
		result = 31 * result + (n != null ? n.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Plane{" +
				"a=" + a +
				", n=" + n +
				'}';
	}
}
