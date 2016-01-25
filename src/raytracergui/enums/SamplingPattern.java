package raytracergui.enums;

import camera.OneRaySamplingPattern;
import camera.RandomRowsSamplingPattern;
import camera.StatticSamplingPattern;

/**
 * Created by jroehl on 15.01.16.
 */
public enum SamplingPattern {
    ONERAY, RANDOMROWS, STATIC;

    public camera.SamplingPattern getSamplingPattern(int numSamples) {

        switch (this) {
            case ONERAY:
                return new OneRaySamplingPattern(numSamples);
            case RANDOMROWS:
                return new RandomRowsSamplingPattern(numSamples);
            case STATIC:
                return new StatticSamplingPattern(numSamples);
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


