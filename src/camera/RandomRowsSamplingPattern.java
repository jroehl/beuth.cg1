package camera;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public class RandomRowsSamplingPattern extends SamplingPattern {
	Random rand = new Random();

	public RandomRowsSamplingPattern(int numSamples) {
		super(numSamples);

	}

	@Override
	public Set<Point2> generateSamples() {

		final double growValue = 1.0 / numSamples;

		final ArrayList<Double> werte = new ArrayList<Double>();
		final Set<Point2> points = new HashSet<Point2>();
		double startValue = -0.5;
		// System.out.println("startVaue:");
		for (int k = 0; k < numSamples; k++) {
			// System.out.println(startValue + "startValue");
			werte.add(startValue);

			startValue = startValue + growValue;

		}

		// Collections.shuffle(werte);

		final int wSize = werte.size();
		// System.out.println("x & y des jeweiligen Punktes");
		for (int i = 0; i < wSize; i++) {

			final Point2 p = new Point2(werte.get(i) + (rand.nextDouble() / numSamples), werte.get(i) + (rand.nextDouble() / numSamples));
			// System.out.println(p.x + "-" + p.y);
			points.add(p);

		}
		// System.out.println(":::::::::::::::::");
		return points;
	}

}
