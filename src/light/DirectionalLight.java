package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

public class DirectionalLight extends Light {

	private final Vector3 direction;

	public DirectionalLight(Color color, Vector3 direction) {
		super(color);
		this.direction = direction;
	}

	@Override
	public boolean illuminates(Point3 p) {
		return true;
	}

	@Override
	public Vector3 directionFrom(Point3 p) {
		return direction.mul(-1).normalized();
	}

}
