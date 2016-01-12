package camera;

import java.util.ArrayList;
import java.util.Random;

import Matrizen_Vektoren_Bibliothek.Point3;

public class SamplingPattern {
	Random rand = new Random();
	final ArrayList<Point3> points;
	public SamplingPattern(ArrayList<Point3> points) {
		this.points = points;

	}

	public Point3[][] samplePoints(ArrayList<Point3> points) {

		final Point3[][] pointsArrRandom = new Point3[points.size()][points.size()];
		for (int i = 0; i < points.size(); i++) {
			final Point3[] pointsArr = new Point3[points.size()];
			pointsArr[i] = points.get(i);

		}

		return null;

	}

}
