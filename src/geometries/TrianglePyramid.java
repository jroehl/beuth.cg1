package geometries;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;
import material.*;
import ray.Ray;
import textures.SingleColorTexture;

import java.util.Random;

public class TrianglePyramid extends Geometry {

	private final Point3 eckeA;
	private final Point3 eckeB;
	private final Point3 eckeC;
	private final Point3 eckeD;
	private final Point3 top;
	double kantenLänge;
	private final Random r = new Random();
	private final Material mat1;
	private final Material mat2;
	private final Material mat3;
	private final Material mat4;
	private final Material mat5;
	private final Material mat6;

	public TrianglePyramid(Material material) throws IllegalArgumentException {
		super(material);
		this.eckeA = new Point3(0, 0, 0);
		eckeB = new Point3(eckeA.x + 3, eckeA.y, eckeA.z);

		eckeC = new Point3(eckeA.x + 3, eckeA.y, eckeA.z - 3);
		eckeD = new Point3(eckeA.x, eckeA.y, eckeA.z - 3);
		final Vector3 bottomMiddle = ((eckeB.sub(eckeA)).add(eckeC.sub(eckeB))).mul(0.5);
		top = new Point3(bottomMiddle.x, bottomMiddle.y + 3, bottomMiddle.z);

		mat1 = switchColor(material);
		mat2 = switchColor(material);
		mat3 = switchColor(material);
		mat4 = switchColor(material);
		mat5 = switchColor(material);
		mat6 = switchColor(material);

		// System.out.println("A: " + eckeA.x + "," + eckeA.y + "," + eckeA.z);
		// System.out.println("B: " + eckeB.x + "," + eckeB.y + "," + eckeB.z);
		// System.out.println("B: " + top.x + "," + top.y + "," + top.z);
	}
	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {
		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

//		final Triangle a = new Triangle(mat1, eckeA, eckeB, top, new Normal3(-1, 1, 1), new Normal3(1, 1, 1), new Normal3(0, 1, 0));
//		final Triangle b = new Triangle(mat2, eckeB, eckeC, top, new Normal3(1, 1, 1), new Normal3(1, 1, -1), new Normal3(0, 1, 0));
//		final Triangle c = new Triangle(mat3, eckeC, eckeD, top, new Normal3(1, 1, -1), new Normal3(-1, 1, -1), new Normal3(0, 1, 0));
//		final Triangle d = new Triangle(mat4, eckeD, eckeA, top, new Normal3(-1, 1, -1), new Normal3(-1, 1, 1), new Normal3(0, 1, 0));
//		final Triangle bottom1 = new Triangle(mat5, eckeA, eckeB, eckeD, new Normal3(0, -1, 0), new Normal3(0, -1, 0),
//				new Normal3(0, -1, 0));
//		final Triangle bottom2 = new Triangle(mat6, eckeB, eckeC, eckeD, new Normal3(0, -1, 0), new Normal3(0, -1, 0),
//				new Normal3(0, -1, 0));
//
//		final Triangle[] triangles = {a, b, c, d, bottom1, bottom2};
//		Hit h = null;
//
//		for (final Triangle tri : triangles) {
//			final Hit h2 = tri.hit(ray);
//			if (h2 != null) {
//				if (h2.t > 0.0000001 && (h == null || h.t < h2.t)) {
//					h = h2;
//				}
//				return h;
//			}
//		}

		return null;
	}

	public final Material switchColor(final Material material) {
		if (material instanceof SingleColorMaterial) {
			return new SingleColorMaterial(new SingleColorTexture(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble())));
		}

		if (this.material instanceof LambertMaterial) {
			return new LambertMaterial(new SingleColorTexture(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble())));
		}

		if (this.material instanceof PhongMaterial) {
			return new PhongMaterial(new SingleColorTexture(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble())),
					new SingleColorTexture(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble())), 64);
		}
		if (this.material instanceof ReflectiveMaterial) {
			return new ReflectiveMaterial(new SingleColorTexture(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble())),
					new SingleColorTexture(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble())), new SingleColorTexture(new Color(
							r.nextDouble(), r.nextDouble(), r.nextDouble())), 64);
		}
		return null;
	}
}
