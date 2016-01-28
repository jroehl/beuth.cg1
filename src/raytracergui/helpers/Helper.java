package raytracergui.helpers;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleDoubleProperty;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by jroehl on 17.01.16.
 */
public class Helper {

    public Helper() {
    }

    public static double sliderVal(double storedValue, double newValue) {

        double calcVal;

        if (newValue > 0) {
            if (newValue < 0.1) {
                calcVal = 0.005;
            } else if (newValue < 0.2) {
                calcVal = 0.01;
            } else if (newValue < 0.4) {
                calcVal = 0.1;
            } else if (newValue < 0.8) {
                calcVal = 0.4;
            } else {
                calcVal = 1;
            }
        } else {
            if (newValue > -0.1) {
                calcVal = -0.005;
            } else if (newValue > -0.2) {
                calcVal = -0.01;
            } else if (newValue > -0.4) {
                calcVal = -0.1;
            } else if (newValue > -0.8) {
                calcVal = -0.4;
            } else {
                calcVal = -1;
            }
        }

        try {
            return round(storedValue + calcVal);
        } catch (ParseException e) {
            System.out.println(e);
            return storedValue + calcVal;
        }
    }

    public static double round(double d) throws ParseException {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
        Number number = format.parse(df.format(d));
        return number.doubleValue();
    }

    public static StringBinding createBinding(SimpleDoubleProperty simpleDoubleProperty) {
        return new StringBinding() {
            {
                bind(simpleDoubleProperty);
            }

            @Override
            protected String computeValue() {
                return Double.toString(simpleDoubleProperty.getValue());
            }
        };
    }
}
