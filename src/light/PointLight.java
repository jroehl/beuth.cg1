package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

public class PointLight extends Light {

	private final Point3 pl;

	public PointLight(Color cl, Point3 position) {
		super(cl);
		this.pl = position;
	}

	@Override
	public boolean illuminates(Point3 p) {
		final Vector3 pl_point = p.sub(pl);

		return ((pl_point.dot(n)) == true);
	}

	@Override
	public Vector3 directionFrom(Point3 p) {
		return null;
	}

}
