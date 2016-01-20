package camera;

import java.util.ArrayList;
import java.util.Random;

import Matrizen_Vektoren_Bibliothek.Point2;

public class SamplingPattern {
	Random rand = new Random();
	final ArrayList<Point2> points;
	final int numSamples;

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
		final double growValue = 1 / numSamples;
		double startValue = -0.5;

		for (int j = 0; j < numSamples; j++) {
			werte.add(j, startValue);
			startValue = startValue + growValue;

		}
		for (int i = 0; i < numSamples; i++) {
			final int w = rand.nextInt(werte.size());

			points.add(new Point2(werte.get(w), werte.get(w)));
			werte.set(w, werte.get(werte.size() - 1));
			werte.remove(werte.size() - 1);
		}
		return points;
	}

}
