package ray;

import Matrizen_Vektoren_Bibliothek.Mat4x4;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

public class Transform {

	private final Mat4x4 m; // Transformationsmatrix
	private final Mat4x4 i;// Inverse von m

	public Transform() {
		this.m = new Mat4x4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1); // Einheitsmatrix
		this.i = m;
	}

	public Transform(Mat4x4 m, Mat4x4 i) {
		this.m = m;
		this.i = i;
	}

	// _________________________________________________________________________________
	// Translationsmatrix:
	// 1 0 0 -x
	// 0 1 0 -y
	// 0 0 1 -z
	// 0 0 0 1
	public Transform translate(Point3 p) {
		final Transform t = new Transform(new Mat4x4(1, 0, 0, p.x, 0, 1, 0, p.y, 0, 0, 1, p.z, 0, 0, 0, 1), new Mat4x4(1, 0, 0, -p.x, 0, 1,
				0, -p.y, 0, 0, 1, -p.z, 0, 0, 0, 1));

		return new Transform(m.mul(t.m), i.mul(t.i));
	}

	// _________________________________________________________________________________
	// Skalierungssmatrix:
	// 1/x 0 0 0
	// 0 1/y 0 0
	// 0 0 1/z 0
	// 0 0 0 1
	public Transform scale(double x, double y, double z) {
		final Transform t = new Transform(new Mat4x4(x, 0, 0, 0, 0, y, 0, 0, 0, 0, z, 0, 0, 0, 0, 1),

		new Mat4x4(1.0 / x, 0, 0, 0, 0, 1.0 / y, 0, 0, 0, 0, 1.0 / z, 0, 0, 0, 0, 1));

		return new Transform(m.mul(t.m), i.mul(t.i));
	}

	// _________________________________________________________________________________
	// Rotatioinsmatrix_X:
	// 1 0 0 0
	// 0 cos(a) sin(a) 0
	// 0 -sin(a) cos(a) 0
	// 0 0 0 1
	public Transform rotateX(double angle) {
		final Transform t = new Transform(new Mat4x4(1, 0, 0, 0, 0, Math.cos(angle), -Math.sin(angle), 0, 0, Math.sin(angle),
				Math.cos(angle), 0, 0, 0, 0, 1), new Mat4x4(1, 0, 0, 0, 0, Math.cos(angle), Math.sin(angle), 0, 0, -Math.sin(angle),
				Math.cos(angle), 0, 0, 0, 0, 1));

		return new Transform(m.mul(t.m), i.mul(t.i));
	}

	// _________________________________________________________________________________
	// Rotatioinsmatrix_Y:
	// cos(a) 0 -sin(a) 0
	// 0 1 0 0
	// sin(a) 0 cos(a) 0
	// 0 0 0 1
	public Transform rotateY(double angle) {
		final Transform t = new Transform(new Mat4x4(Math.cos(angle), 0, Math.sin(angle), 0, 0, 1, 0, 0, -Math.sin(angle), 0,
				Math.cos(angle), 0, 0, 0, 0, 1), new Mat4x4(Math.cos(angle), 0, -Math.sin(angle), 0, 0, 1, 0, 0, Math.sin(angle), 0,
				Math.cos(angle), 0, 0, 0, 0, 1));
		return new Transform(m.mul(t.m), i.mul(t.i));
	}

	// _________________________________________________________________________________
	// Rotatioinsmatrix_Z:
	// cos(a) sin(a) 0 0
	// -sin(a) cos(a) 0 0
	// 0 0 1 0
	// 0 0 0 1
	public Transform rotateZ(double angle) {
		final Transform t = new Transform(new Mat4x4(Math.cos(angle), -Math.sin(angle), 0, 0, Math.sin(angle), Math.cos(angle), 0, 0, 0, 0,
				1, 0, 0, 0, 0, 1), new Mat4x4(Math.cos(angle), Math.sin(angle), 0, 0, -Math.sin(angle), Math.cos(angle), 0, 0, 0, 0, 1, 0,
				0, 0, 0, 1));

		return new Transform(m.mul(t.m), i.mul(t.i));
	}

	// _________________________________________________________________________________
	// Transformation der Normalen
	public Normal3 mul(Normal3 n) {
		return i.transposed().mul(new Vector3(n.x, n.y, n.z)).normalized().asNormal();
	}

	// _________________________________________________________________________________
	// Transformation des Strahls

	public Ray mul(Ray r) {
		return new Ray(i.mul(r.origin), i.mul(r.direction));
	}
}
