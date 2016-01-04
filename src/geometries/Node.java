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
		// TODO Auto-generated method stub
		return null;
	}

}
