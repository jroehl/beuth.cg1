package geometries;

import java.util.ArrayList;

import material.Material;
import ray.Ray;
import ray.Transform;
import Matrizen_Vektoren_Bibliothek.Point3;

public class Cylinder extends Geometry {

	private final Material material;
	public ArrayList<Geometry> cylinderParts;

	public Cylinder(Material material, ArrayList<Geometry> cylinderParts) {
		this.material = material;
		this.cylinderParts = cylinderParts;
		// final CylinderBody body = ;

		cylinderParts.add(new CylinderBody(this.material));

		final ArrayList<Geometry> geos = new ArrayList<Geometry>();
		geos.add(new Disc(this.material));
		final Node top = new Node(new Transform().translate(new Point3(0, 2, 0)), geos);
		final Node bottom = new Node(new Transform().translate(new Point3(0, -2, 0)).rotateX(Math.PI), geos);
		cylinderParts.add(top);
		cylinderParts.add(bottom);

	}

	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {
		double t = Double.MAX_VALUE;
		Hit hitLow = null;

		for (final Geometry g : cylinderParts) {

			final Hit hit = g.hit(ray);
			if (hit != null) {
				if (hit.t < t && hit.t > 0.0001) {
					t = hit.t;
					hitLow = hit;
				}
			}

		}
		if (hitLow != null) {

			return new Hit(hitLow.t, ray, hitLow.geo, hitLow.n, hitLow.tex);
		}
		return null;
	}

}
