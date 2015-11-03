package camera;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class PerspectiveCamera extends Camera {

	public final double angle;

	public PerspectiveCamera(Point3 e, Vector3 g, Vector3 t, double angle) {
		super(e, g, t);
		this.angle = angle;

	}

	@Override
	public Ray rayFor(int w, int h, int x, int y) {

		final Vector3 v1 = super.w.mul((h / 2) / Math.tan(angle)).mul(-1);
		final Vector3 v2 = super.u.mul(x - ((w - 1) / 2));
		final Vector3 v3 = super.v.mul(y - ((h - 1) / 2));
		final Vector3 r = v3.add(v2).add(v1);
		final Vector3 d = r.mul(1 / r.magnitude);
		return new Ray(super.e, d);
	}
}
