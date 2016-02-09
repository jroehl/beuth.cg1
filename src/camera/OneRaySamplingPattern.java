package camera;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public class OneRaySamplingPattern extends SamplingPattern {
	Random rand = new Random();

	public OneRaySamplingPattern(int numSamples) {
		super(numSamples);
	}

	@Override
	public Set<Point2> generateSamples() {

		final Set<Point2> staticPoints = new HashSet<Point2>();
		staticPoints.add(new Point2(0, 0));
		return staticPoints;
	}
}