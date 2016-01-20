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

		// bei ungerader Sampleanzahl: numSamples - 1, numSamples/2 - eine der
		int numSamplesJust;
		final ArrayList<Double> werte = new ArrayList<Double>();
		if (numSamples % 2 != 0) {
			numSamplesJust = numSamples - 1;
		} else {
			numSamplesJust = numSamples;
		}

		final double growValue = 1.0 / numSamples;

		double startValue = 0.0;
		// erster Punkt ist 0,0.. dann nach positiv und nach negativ jeweils den
		// Faktor (1/Anzahl gewünschter Samples) verrechnen
		for (int k = 0; k < numSamplesJust / 2; k++) {
			werte.add(k, startValue);
			startValue = startValue - growValue;

		}

		for (int j = 0; j < (numSamples + 1) / 2; j++) {
			werte.add(j, startValue);
			startValue = startValue + growValue;

		}

		for (int i = 0; i < numSamples; i++) {

			final int w = rand.nextInt(werte.size());
			// zufällige Zahl aus den Werten holen
			points.add(new Point2(werte.get(w), werte.get(w)));
			// mit dieser Zahl einen neuen Point2 erzeugen
			if (w != werte.size() - 1) {

				// die entnommene zahl durch die letzte der Liste ersetzen
				werte.set(w, werte.get(werte.size() - 1));
				// letzte Zahl löschen
				werte.remove(werte.size() - 1);
			}
		}
		return points;
	}

}
