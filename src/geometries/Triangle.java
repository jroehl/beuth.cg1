package geometries;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class Triangle extends Geometry {

	public final Point3 a;
	public final Point3 b;
	public final Point3 c;

	public Triangle(Color color, Point3 a, Point3 b, Point3 c) {
		super(color);
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public Hit hit(Ray r) {
		return null;

	}

}
