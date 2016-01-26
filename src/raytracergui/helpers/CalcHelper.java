package raytracergui.helpers;

/**
 * Created by jroehl on 17.01.16.
 */
public class CalcHelper {

    private final int DIVIDEND;

    public CalcHelper(int dividend) {
        this.DIVIDEND = dividend;
    }

    public CalcHelper() {
        this.DIVIDEND = 4;
    }

    public double getDividedValue(Double value) {
        return Math.round((value / DIVIDEND) * 10.0) / 10.0;
    }

    public double getMultipliedValue(Double value) {
        return value * DIVIDEND;
    }
}
