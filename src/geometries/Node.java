package geometries;

import java.util.ArrayList;

import material.Material;
import ray.Ray;
import ray.Transform;

public class Node extends Geometry {

	private final Transform trans;
	private final ArrayList<Geometry> geos;

	public Node(Material material, Transform trans, ArrayList<Geometry> geos) throws IllegalArgumentException {
		super(material);
		this.trans = trans;
		this.geos = geos;

	}

	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {
		final Ray transformedRay = trans.mul(ray);
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
