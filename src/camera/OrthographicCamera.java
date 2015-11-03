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

		return null;
	}
}
