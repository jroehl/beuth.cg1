package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;
import ray.Ray;

public class PointLight extends Light {

	private final Point3 pl;

	public PointLight(Color cl, Point3 position) {
		super(cl);
		this.pl = position;
	}

	@Override
	public boolean illuminates(Point3 p) {
		// final Vector3 pl_point = p.sub(pl);
		// return ((pl_point.dot(n) > 1) == true);

		return true;

		// ray im konstruktor übergeben????
	}

	@Override
	public Vector3 directionFrom(Point3 p) {
		return pl.sub(p).normalized();
	}

}
