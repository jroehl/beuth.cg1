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
		return null;
	}

}
