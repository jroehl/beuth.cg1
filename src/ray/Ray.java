package ray;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class Ray {

	public final Point3 o;
	public final Vector3 d;

	public Ray(Point3 o, Vector3 d) {
		this.o = o;
		this.d = d;
	}

	public Point3 at(double t) {
		return o;

	}

	public double tOf(Point3 p) {
		return 0;

	}

}
