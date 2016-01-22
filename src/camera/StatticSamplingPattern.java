package camera;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public class StatticSamplingPattern extends SamplingPattern {
	Random rand = new Random();

	public StatticSamplingPattern(int numSamples) {
		super(numSamples);

	}

	@Override
	public Set<Point2> generateSamples() {

		final double growValue = 1.0 / numSamples;

		final ArrayList<Double> werte = new ArrayList<Double>();
		final Set<Point2> points = new HashSet<Point2>();
		double startValue = -0.5;

		if (numSamples > 1) {
			for (int k = 0; k < numSamples; k++) {

				werte.add(startValue);

				startValue = startValue + growValue;

			}
		} else {
			werte.add(0.0);
		}

		final int wSize = werte.size();

		for (int i = 0; i < wSize; i++) {

			final Point2 p = new Point2(werte.get(i), werte.get(i));

			points.add(p);

		}

		return points;
	}

}
