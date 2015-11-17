package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

public class SpotLight extends Light {

	private final Vector3 direction;
	private final Point3 position;
	private final double halfAngle;

	public SpotLight(Color color, Vector3 direction, Point3 position,
			double halfAngle) {
		super(color);
		this.direction = direction;
		this.position = position;
		this.halfAngle = halfAngle;
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
