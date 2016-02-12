package camera;

import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public abstract class SamplingPattern {

	final int numSamples;

	public SamplingPattern(int numSamples) {

		if (numSamples < 1) {
			numSamples = 1;
		}
		this.numSamples = numSamples;
	}

	public abstract Set<Point2> generateSamples();
}
