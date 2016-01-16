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

	public ArrayList<Point2> generateSamples(ArrayList<Point2> points, int numSamples) {
		// zufälliges vertauschen der Zeilen/Spalten

		// 1. werte-Liste von der negativen Hälfte von numSamples geteilt durch
		// 10 an auffüllen und immer um 0.1 erhöhen
		// sp 11: int -(11/2)/10 = -0.5... also -0.5, -0.4, -0.3, ... 0.5
		// dann den Wert an einem zufällige Index herausziehen, ihn durch den
		// letzten in der Liste befindlichen Wert ersetzen und den letzten Wert
		// dann löschen.. das ganze wiederholen, bis werte leer ist

		final ArrayList<Double> werte = new ArrayList<Double>();
		double index = (-numSamples / 2) / 10;
		for (int j = 0; j < numSamples; j++) {
			werte.add(j, index);
			index = index + 0.1;

		}
		for (int i = 0; i < numSamples; i++) {
			final int w = rand.nextInt(werte.size());

			points.add(new Point2(werte.get(w), werte.get(w)));
			werte.set(w, werte.get(werte.size() - 1));
			werte.remove(werte.size() - 1);
		}
		return points;
	}
	// final int n = (int) Math.sqrt(this.numSamples);
	// final float partWidth = 1.0f / (this.numSamples);
	//
	// for (int i = 0; i < numSamples; i++)
	// points.add(new Point2(0, 0));
	//
	// for (int i = 0; i < n; i++)
	// for (int j = 0; j < n; j++) {
	// points.get(i * n + j).x = (i * n + j) * partWidth + (0.0f +
	// rand.nextFloat() * (partWidth - 0.0f));
	// points.get(i * n + j).y = (j * n + i) * partWidth + (0.0f +
	// rand.nextFloat() * (partWidth - 0.0f));
	// }
	//
	// for (int i = 0; i < n; i++)
	// for (int j = 0; j < n; j++) {
	// final int k = rndInt(j, n - 1);
	// final float t = (float) points.get(i * n + j).x;
	// points.get(i * n + j).x = (points.get(i * n + k).x);
	// points.get(i * n + k).x = t;
	// }
	//
	// for (int i = 0; i < n; i++)
	// for (int j = 0; j < n; j++) {
	// final int k = rndInt(j, n - 1);
	// final float t = (float) points.get(i * n + j).y;
	// points.get(i * n + j).y = (points.get(i * n + k).y);
	// points.get(i * n + k).y = t;
	// }
	//
	// return points;
	//
	// }
	//
	// public int rndInt(int min, int max) {
	// final int range = max - min;
	// if (range == 0)
	// return min;
	//
	// return min + rand.nextInt(range);
	// }
}
