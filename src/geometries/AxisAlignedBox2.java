package geometries;

import java.util.ArrayList;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class AxisAlignedBox2 extends Geometry {

	private final Point3 lbf;
	private final Point3 run;

	public AxisAlignedBox2(Color color, Point3 lbf, Point3 run) {
		super(color);
		this.lbf = lbf;
		this.run = run;
	}

	@Override
	public Hit hit(Ray ray) {
		final ArrayList<Plane> planes = new ArrayList<Plane>();

		final Plane b1 = new Plane(color, lbf, new Normal3(-1, 0, 0));
		final Plane b2 = new Plane(color, lbf, new Normal3(0, -1, 0));
		final Plane b3 = new Plane(color, lbf, new Normal3(0, 0, -1));

		final Plane f1 = new Plane(color, run, new Normal3(1, 0, 0));
		final Plane f2 = new Plane(color, run, new Normal3(0, 1, 0));
		final Plane f3 = new Plane(color, run, new Normal3(0, 0, 1));

		if (ray.origin.sub(b1.a).dot(b1.n) > 0) {
			planes.add(b1);
		}

		if (ray.origin.sub(b2.a).dot(b2.n) > 0) {
			planes.add(b2);
		}

		if (ray.origin.sub(b3.a).dot(b3.n) > 0) {
			planes.add(b3);
		}

		if (ray.origin.sub(f1.a).dot(f1.n) > 0) {
			planes.add(f1);
		}

		if (ray.origin.sub(f2.a).dot(f2.n) > 0) {
			planes.add(f2);
		}

		if (ray.origin.sub(f3.a).dot(f3.n) > 0) {
			planes.add(f3);
		}

		// final ArrayList<Double> factor = new ArrayList<Double>();

		Hit farestHit = null;

		for (final Plane plane : planes) {
			// denonimator / nenner
			final double d = ray.direction.dot(plane.n);

			if (d != 0) {
				final double t = plane.a.sub(ray.origin).dot(plane.n) / d;

				if (farestHit == null || farestHit.t < t) {

					farestHit = new Hit(t, ray, this);
				}
			}
		}

		return farestHit;

	}
	// double tf = -1;
	//
	// for (final Plane plane : planes) {
	// // denonimator / nenner
	// final double d = ray.direction.dot(plane.n);
	//
	// // if (d != 0) {
	// final double t = plane.a.sub(ray.origin).dot(plane.n) / d;
	//
	// if (tf < t) {
	// tf = t;
	// }
	// }
	// // }
	// System.out.println(tf);
	// if (tf <= 0) {
	// return null;
	// }
	//
	// return new Hit(tf, ray, this);
	// }

	private Hit checkSize(final Hit hit) {
		if (hit != null) {
			final Point3 pHit = hit.ray.at(hit.t);
			if ((lbf.x <= pHit.x && pHit.x <= run.x)
					&& (lbf.y <= pHit.y && pHit.y <= run.y)
					&& (lbf.z <= pHit.z && pHit.z <= run.z))
				return hit;
		}
		return null;
	}

}
