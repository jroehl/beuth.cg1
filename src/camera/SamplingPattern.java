package camera;

import java.util.ArrayList;
import java.util.Random;

import Matrizen_Vektoren_Bibliothek.Point2;

public class SamplingPattern {
	Random rand = new Random();
	final ArrayList<Point2> points;
	private final int numSamples;

	public SamplingPattern(ArrayList<Point2> points, int numSamples) {
		this.points = points;
		this.numSamples = numSamples;
	}

	public void generateSamples(ArrayList<Point2> points, int numSamples) {

		final int n = (int) Math.sqrt(numSamples);
		final float partWidth = 1.0f / (numSamples);

		for (int i = 0; i < numSamples; i++)
			points.add(new Point2(0, 0));

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				points.get(i * n + j).x = (i * n + j) * partWidth + (0.0f + rand.nextFloat() * (partWidth - 0.0f));
				points.get(i * n + j).y = (j * n + i) * partWidth + (0.0f + rand.nextFloat() * (partWidth - 0.0f));
			}

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				final int k = rndInt(j, n - 1);
				final float t = (float) points.get(i * n + j).x;
				points.get(i * n + j).x = (points.get(i * n + k).x);
				points.get(i * n + k).x = t;
			}

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				final int k = rndInt(j, n - 1);
				final float t = (float) points.get(i * n + j).y;
				points.get(i * n + j).y = (points.get(i * n + k).y);
				points.get(i * n + k).y = t;
			}

	}
	public int rndInt(int min, int max) {
		final int range = max - min;
		if (range == 0)
			return min;

		return min + rand.nextInt(range);
	}
}
