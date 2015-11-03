package geometries;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class Sphere extends Geometry {

	public final Point3 c;
	public final double r;

	public Sphere(Color color, Point3 c, double r) {
		super(color);
		this.c = c;
		this.r = r;

	}

	@Override
	public Hit hit(Ray r) {
		return null;

	}

}
