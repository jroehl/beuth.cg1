package camera;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public class StaticSamplingPattern extends SamplingPatternAbstract {
	Random rand = new Random();

	public StaticSamplingPattern(Set<Point2> points, int numSamples) {
		super(points, numSamples);
	}

	public Set<Point2> generateSamples() {

		final Set<Point2> staticPoints = new HashSet<Point2>();
		staticPoints.add(new Point2(0, 0));
		return staticPoints;

	}

}
