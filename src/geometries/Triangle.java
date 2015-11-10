package geometries;

import Matrizen_Vektoren_Bibliothek.Mat3x3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class Triangle extends Geometry {

	public final Point3 a;
	public final Point3 b;
	public final Point3 c;

	public Triangle(Color color, Point3 a, Point3 b, Point3 c) {
		super(color);
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public Hit hit(Ray ray) {

		final Mat3x3 matA = new Mat3x3( a.x - b.x, a.x - c.x, ray.direction.x,
				a.y - b.y, a.y - c.y, ray.direction.y,
				a.z - b.z, a.z - c.z, ray.direction.z);

		final Vector3 vec = a.sub(ray.origin);

		final double beta = matA.changeCol1(vec).determinant / matA.determinant;
		final double gamma = matA.changeCol2(vec).determinant / matA.determinant;

		final double t = matA.changeCol3(vec).determinant / matA.determinant;

		if (beta < 0 || gamma < 0 || (beta + gamma) > 1 || t < 0) {
			return null;
		} else {
			return new Hit(t, ray, color);
		}

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Triangle triangle = (Triangle) o;

		if (a != null ? !a.equals(triangle.a) : triangle.a != null) return false;
		if (b != null ? !b.equals(triangle.b) : triangle.b != null) return false;
		return !(c != null ? !c.equals(triangle.c) : triangle.c != null);

	}

	@Override
	public int hashCode() {
		int result = a != null ? a.hashCode() : 0;
		result = 31 * result + (b != null ? b.hashCode() : 0);
		result = 31 * result + (c != null ? c.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Triangle{" +
				"a=" + a +
				", b=" + b +
				", c=" + c +
				'}';
	}
}
