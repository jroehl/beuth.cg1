package geometries;

import material.Material;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class TrianglePyramid extends Geometry {

	private final Point3 eckeA;
	private final Point3 eckeB;
	private final Point3 eckeC;
	private final Point3 eckeD;
	private final Point3 top;
	double kantenLänge;

	public TrianglePyramid(Material material, Point3 eckeA) throws IllegalArgumentException {
		super(material);
		this.eckeA = eckeA;
		eckeB = new Point3(eckeA.x + 2, eckeA.y, eckeA.z);
		eckeC = new Point3(eckeA.x + 2, eckeA.y, eckeA.z - 2);
		eckeD = new Point3(eckeA.x, eckeA.y, eckeA.z - 2);
		final Vector3 bottomMiddle = (eckeB.sub(eckeA)).mul(0.5).add(eckeD.sub(eckeA)).mul(0.5);
		top = new Point3(bottomMiddle.x, bottomMiddle.y + 3.5, bottomMiddle.z);

	}
	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {
		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		final Triangle a = new Triangle(material, eckeA, eckeB, top);
		final Triangle b = new Triangle(material, eckeB, eckeC, top);
		final Triangle c = new Triangle(material, eckeC, eckeD, top);
		final Triangle d = new Triangle(material, eckeD, eckeA, top);
		final Triangle bottom1 = new Triangle(material, eckeA, eckeB, eckeD);
		final Triangle bottom2 = new Triangle(material, eckeB, eckeC, eckeD);

		final Triangle[] triangles = {a, b, c, d, bottom1, bottom2};

		for (final Triangle tri : triangles) {
			// kleinstes t suchen.. damit Hit returnen
		}

		// Müssen nun alle über die world erzeugt werden und dann jeweils der
		// hit abgefragt werden - evtl ArrayList<Triangle> zurück geben?
		// Oder hier für jedes Dreieck die Hits abfragen??
		return null;
	}
}
