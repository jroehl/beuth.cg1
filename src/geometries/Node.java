package geometries;

import java.util.ArrayList;

import ray.Ray;
import ray.Transform;

public class Node extends Geometry {

	private final Transform trans;
	public ArrayList<Geometry> geos;

	public Node(Transform trans, ArrayList<Geometry> geos) throws IllegalArgumentException {
		this.trans = trans;
		this.geos = geos;

	}

	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {
		final Ray transformedRay = trans.mul(ray); // gibt transformierten Ray
													// zurück
		double t = Double.MAX_VALUE;
		Hit hitLow = null;

		for (final Geometry g : geos) {

			final Hit hit = g.hit(transformedRay);
			if (hit != null) {
				if (hit.t < t && hit.t > 0.0001) {
					t = hit.t;
					hitLow = hit;
				}
			}

		}
		if (hitLow != null) {

			return new Hit(hitLow.t, hitLow.ray, hitLow.geo, trans.mul(hitLow.n));
		}
		return null;
	}

}
