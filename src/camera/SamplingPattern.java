package camera;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import Matrizen_Vektoren_Bibliothek.Point2;

public class SamplingPattern {
	Random rand = new Random();
	final Set<Point2> points;
	int numSamples;

	public SamplingPattern(Set<Point2> points, int numSamples) {
		this.points = points;
		if (numSamples < 1) {
			numSamples = 1;
		}
		this.numSamples = numSamples;
	}

	public Set<Point2> generateSamples() {
		// zufälliges vertauschen der Zeilen/Spalten

		// bei ungerader Sampleanzahl: numSamples - 1, numSamples/2 - eine der
		int numSamplesJust;
		final double growValue = 1.0 / numSamples;

		// System.out.println(growValue);
		final ArrayList<Double> werte = new ArrayList<Double>();
		if (numSamples % 2 != 0) {
			numSamplesJust = numSamples - 1;
		} else {
			numSamplesJust = numSamples;
		}

		// erster Punkt ist 0,0.. dann nach positiv und nach negativ jeweils den
		// Faktor (1/Anzahl gewünschter Samples) verrechnen
		double startValue = 0.0;

		for (int k = 0; k < numSamplesJust / 2; k++) {
			werte.add(k, startValue);
			// System.out.println(werte.get(k));

			// System.out.println(werte.get(k));
			startValue = startValue - growValue;
			// System.out.println(startValue);

		}

		for (int j = 0; j < (numSamplesJust) / 2; j++) {
			// System.out.println(werte.get(j));
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
