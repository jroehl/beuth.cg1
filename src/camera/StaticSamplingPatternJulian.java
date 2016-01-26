package camera;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public class StaticSamplingPatternJulian extends SamplingPattern {
	Random rand = new Random();

	public StaticSamplingPatternJulian(int numSamples) {
		super(numSamples);
	}

	@Override
	public Set<Point2> generateSamples() {
		final ArrayList<Point2> points = new ArrayList<Point2>();
		double step;
		final double start;

		if (numSamples <= 1) {
			step = 0.0;

		} else {
			step = 1.0 / (numSamples - 1.0);
		}

		if (numSamples <= 1) {
			start = 0.0;
		} else {
			start = -0.5;
		}

		for (int i = 0; i < numSamples; i++) {
			for (int j = 0; j < numSamples; j++) {
				points.add(new Point2(start + i * step, start + j * step));
			}
		}
		// Collections.shuffle(points);
		return new HashSet<Point2>(points);

	}
}
