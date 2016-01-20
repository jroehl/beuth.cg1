package camera;

import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public abstract class SamplingPatternAbstract {

	private final Set<Point2> points;
	private final int numSamples;

	public SamplingPatternAbstract(Set<Point2> points, int numSamples) {
		this.points = points;
		this.numSamples = numSamples;
	}

	public abstract Set<Point2> generateSamples();

}
