package camera;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class OrthographicCamera extends Camera {

	public final double s;

	public OrthographicCamera(final Point3 e, final Vector3 g, final Vector3 t,
			final double s) {
		super(e, g, t);
		this.s = s;

	}

	@Override
	public Ray rayFor(final int w, final int h, final int x, final int y) {
		final double a = h / w;
		final double doub1 = (s * (x - (w - 1) / 2)) / (w - 1);
		final double doub2 = (s * (y - (h - 1) / 2)) / (h - 1);
		final Point3 o = e.add((u.mul(doub1 * a)).add(v.mul(doub2)));
		return new Ray(o, super.w.mul(-1)); // vector d = vector w * (-1)
	}
}
