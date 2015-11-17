package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

public class PointLight extends Light {

	private final Point3 position;

	public PointLight(Color color, Point3 position) {
		super(color);
		this.position = position;
	}

	@Override
	public boolean illuminates(Point3 p) {
		return false;
	}

	@Override
	public Vector3 directionFrom(Point3 p) {
		return null;
	}

}
