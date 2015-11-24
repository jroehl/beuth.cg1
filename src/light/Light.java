package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

public abstract class Light {

	public final Color color;

	public Light(Color color) {
		this.color = color;
	}

	public abstract boolean illuminates(Point3 p);
	public abstract Vector3 directionFrom(Point3 p);

}
