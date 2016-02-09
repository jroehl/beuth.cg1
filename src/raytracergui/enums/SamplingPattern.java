package raytracergui.enums;

import camera.OneRaySamplingPattern;
import camera.RandomRowsSamplingPattern;

/**
 * Created by jroehl on 15.01.16.
 */
public enum SamplingPattern {
	ONERAY, RANDOMROWS;

	public camera.SamplingPattern getSamplingPattern(int numSamples) {

		switch (this) {
		case ONERAY:
			return new OneRaySamplingPattern(numSamples);
		case RANDOMROWS:
			return new RandomRowsSamplingPattern(numSamples);
		default:
			return null;
		}
	}

	private void printValue(int numSamples) {
		System.out.println("#########PATTERN#########");
		System.out.println(this);
		System.out.println(numSamples);
		System.out.println("#########PATTERN#########");
	}
}
