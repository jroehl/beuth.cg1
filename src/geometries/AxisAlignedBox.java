package geometries;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class AxisAlignedBox extends Geometry {

	public final Point3 lbf;
	public final Point3 run;

	public AxisAlignedBox(Color color, Point3 lbf, Point3 run) {
		super(color);
		this.lbf = lbf;
		this.run = run;
	}

	@Override
	public Hit hit(Ray r) {
		return null;
	}

}
