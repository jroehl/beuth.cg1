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

	public Camera(final Point3 e, final Vector3 g, final Vector3 t) {

		this.e = e;
		this.g = g;
		this.t = t;

		this.w = g.normalized().mul(-1);
//		this.w = (g.mul(1 / g.magnitude));
		this.u = ((t.x(w)).mul((t.x(w)).magnitude));
		this.v = w.x(u);
	}
	public Ray rayFor(final int w, final int h, final int x, final int y) {
		return null;
	}

}
