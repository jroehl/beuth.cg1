package camera;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class OrthographicCamera extends Camera {

	public final double s;

	public OrthographicCamera(Point3 e, Vector3 g, Vector3 t, double s) {
		super(e, g, t);
		this.s = s;

	}

	@Override
	public Ray rayFor(int w, int h, int x, int y) {
		return null;
	}

}
