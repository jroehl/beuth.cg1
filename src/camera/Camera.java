package camera;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public abstract class Camera {
	public final Point3 e;
	public final Vector3 g;
	public final Vector3 t;
	public final Vector3 u;
	public final Vector3 v;
	public final Vector3 w;

	public Camera(Point3 e, Vector3 g, Vector3 t) {
		this.e = e;
		this.g = g;
		this.t = t;
	}

	public Ray rayFor(int w, int h, int x, int y) {
		return null;
	}

}
