package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

public abstract class Light {

	private final Color color;

	public Light(Color color) {
		this.color = color;
	}

	public boolean illuminates(Point3 p) {
		return false;
	}

	public Vector3 directionFrom(Point3 p) {
		return null;
	}

	public Color getColor() {
		return color;
	}
}
