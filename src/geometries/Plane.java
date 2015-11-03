package geometries;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class Plane extends Geometry {

	public final Point3 a;
	public final Normal3 n;

	public Plane(Color color, Point3 a, Normal3 n) {
		super(color);
		this.a = a;
		this.n = n;

	}

	@Override
	public Hit hit(Ray r) {
		return null;

	}
}
