package raytracergui.enums;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import camera.OrthographicCamera;
import camera.PerspectiveCamera;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import raytracergui.helpers.CalcHelper;

/**
 * Created by jroehl on 15.01.16.
 */
public enum Camera {

    ORTHOGRAPHIC, PERSPECTIVE;

    private String name = this.name();

    private final String EYEVIEWX = "labelEx";
    private final String EYEVIEWY = "labelEy";
    private final String EYEVIEWZ = "labelEz";
    private final String GAZEVIEWX = "labelGx";
    private final String GAZEVIEWY = "labelGy";
    private final String GAZEVIEWZ = "labelGz";
    private final String UPVECTORX = "labelUx";
    private final String UPVECTORY = "labelUy";
    private final String UPVECTORZ = "labelUz";
    private final String EXTRA = "labelValExtra";

    private SamplingPattern samplingPattern = SamplingPattern.ONERAY;
    private int numSamples = 0;

    private final CalcHelper calcHelper = new CalcHelper();
    private final String[] array = {EYEVIEWX, EYEVIEWY, EYEVIEWZ, GAZEVIEWX, GAZEVIEWY, GAZEVIEWZ, UPVECTORX, UPVECTORY, UPVECTORZ, EXTRA};
    private final Double[] initialValues = {calcHelper.getMultipliedValue(-0.5), calcHelper.getMultipliedValue(3.3), calcHelper.getMultipliedValue(-8.0), calcHelper.getMultipliedValue(0.0), calcHelper.getMultipliedValue(-0.4), calcHelper.getMultipliedValue(1.0), calcHelper.getMultipliedValue(0.0), calcHelper.getMultipliedValue(1.0), calcHelper.getMultipliedValue(0.0), calcHelper.getMultipliedValue(4.0)};


    private ObservableMap<String, SimpleDoubleProperty> cameraValues;

    Camera() {
        this.cameraValues = FXCollections.observableHashMap();
        for (int i = 0; i < array.length; i++) {
            this.cameraValues.put(array[i], new SimpleDoubleProperty());
            this.cameraValues.get(array[i]).set(initialValues[i]);
        }
    }

    public camera.Camera getCamera() {
        switch (this) {
            case ORTHOGRAPHIC:
                return new OrthographicCamera(this.getEyeView(), this.getGazeView(), this.getUpVector(), this.getExtra(), samplingPattern.getSamplingPattern(this.getNumSamples()));
            case PERSPECTIVE:
                return new PerspectiveCamera(this.getEyeView(), this.getGazeView(), this.getUpVector(), Math.PI / this.getExtra(), samplingPattern.getSamplingPattern(this.getNumSamples()));
            default:
                return null;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSamplingPaterns(SamplingPattern samplingPattern) {
        this.samplingPattern = samplingPattern;
    }

    public SamplingPattern getSamplingPattern() {
        return this.samplingPattern;
    }

    public SimpleDoubleProperty get(String key) {
        return this.cameraValues.get(key);
    }

    public double getValue(String key) {
        return this.cameraValues.get(key).getValue();
    }

    public void setValue(String key, Double value) {
        this.cameraValues.get(key).set(value);
    }

    public double getCorrectedValue(String key) {
        return calcHelper.getDividedValue(this.getValue((key)));
    }

    public Point3 getEyeView() {
        return new Point3(this.getCorrectedValue(EYEVIEWX), this.getCorrectedValue(EYEVIEWY), this.getCorrectedValue(EYEVIEWZ));
    }

    public Vector3 getGazeView() {
        return new Vector3(this.getCorrectedValue(GAZEVIEWX), this.getCorrectedValue(GAZEVIEWY), this.getCorrectedValue(GAZEVIEWZ));
    }

    public Vector3 getUpVector() {
        return new Vector3(this.getCorrectedValue(UPVECTORX), this.getCorrectedValue(UPVECTORY), this.getCorrectedValue(UPVECTORZ));
    }

    public Double getExtra() {
        return this.getCorrectedValue(EXTRA);
    }

    public int getNumSamples() {
        return numSamples;
    }

    public void setNumSamples(int numSamples) {
        this.numSamples = numSamples;
    }


}



