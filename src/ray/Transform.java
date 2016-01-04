package ray;

import Matrizen_Vektoren_Bibliothek.Mat4x4;

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

	public Transform translate(double x, double y, double z) {
		return null;
	}

	public Transform scale(double x, double y, double z) {
		return null;
	}

	public Transform rotateX(double angle) {
		return null;
	}

	public Transform rotateY(double angle) {
		return null;
	}

	public Transform rotateZ(double angle) {
		return null;
	}

}
