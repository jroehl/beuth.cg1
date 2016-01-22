package camera;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public class StaticSamplingPattern2 extends SamplingPattern {
	Random rand = new Random();

	public StaticSamplingPattern2(int numSamples) {
		super(numSamples);
	}

	@Override
	public Set<Point2> generateSamples() {

		final Set<Point2> staticPoints = new HashSet<Point2>();
		staticPoints.add(new Point2(0, 0));
		staticPoints.add(new Point2(-0.3, -0.3));
		staticPoints.add(new Point2(0.3, 0.3));
		staticPoints.add(new Point2(-0.2, -0.5));
		staticPoints.add(new Point2(0.2, 0.2));
		staticPoints.add(new Point2(-0.5, -0.3));
		staticPoints.add(new Point2(0.5, 0.5));
		staticPoints.add(new Point2(-0.4, -0.4));
		staticPoints.add(new Point2(0.4, 0.4));
		return staticPoints;

	}

}
