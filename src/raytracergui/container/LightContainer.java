package raytracergui.container;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import light.DirectionalLight;
import light.PointLight;
import light.SpotLight;
import raytracergui.enums.Light;
import raytracergui.helpers.Helper;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

/**
 * Created by jroehl on 14.01.16.
 */
public class LightContainer {

    private String name;
    private Light type;
    private final String DIRECTIONX = "labelDirX";
    private final String DIRECTIONY = "labelDirY";
    private final String DIRECTIONZ = "labelDirZ";
    private final String POSITIONX = "labelPosX";
    private final String POSITIONY = "labelPosY";
    private final String POSITIONZ = "labelPosZ";
    private final String COLOR = "color";
    private final String HALFANGLE = "halfangle";
    private final String CASTSSHADOW = "castsShadow";

    private final ObservableMap<Object, Object> lightValues;

    private final Helper helper;

    public LightContainer(Light type) {
        this.type = type;
        this.helper = new Helper();
        this.lightValues = FXCollections.observableHashMap();
        this.lightValues.put(DIRECTIONX, new SimpleDoubleProperty(+0.0));
        this.lightValues.put(DIRECTIONY, new SimpleDoubleProperty(+0.0));
        this.lightValues.put(DIRECTIONZ, new SimpleDoubleProperty(+0.0));
        this.lightValues.put(POSITIONX, new SimpleDoubleProperty(+0.0));
        this.lightValues.put(POSITIONY, new SimpleDoubleProperty(+0.0));
        this.lightValues.put(POSITIONZ, new SimpleDoubleProperty(+0.0));
        this.lightValues.put(COLOR, new Color(1, 1, 1));
        this.lightValues.put(HALFANGLE, new SimpleDoubleProperty(+16.0));
        this.lightValues.put(CASTSSHADOW, new SimpleBooleanProperty(true));
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Light getType() {
        return this.type;
    }

    public Object get(String key) {
        return this.lightValues.get(key);
    }

    public double getValue(String key) {
        try {
            final SimpleDoubleProperty s = (SimpleDoubleProperty) this.lightValues.get(key);
            return s.getValue();
        } catch (final Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public void setValue(String key, double value) {
        this.lightValues.put(key, new SimpleDoubleProperty(value));
    }

    public void setDoubleValue(String key, SimpleDoubleProperty value) {
        this.lightValues.put(key, value);
    }

    public SimpleDoubleProperty getDoubleValue(String key) {
        try {
            return (SimpleDoubleProperty) this.lightValues.get(key);
        } catch (final Exception e) {
            return null;
        }
    }

    public Color getColor() {
        return (Color) this.lightValues.get(COLOR);
    }

    public void setColor(javafx.scene.paint.Color value) {
        this.lightValues.put(COLOR, generateColor(value));
    }

    private color.Color generateColor(javafx.scene.paint.Color value) {
        return new color.Color(value.getRed(), value.getGreen(), value.getBlue());
    }

    public Vector3 getDirection() {
        return new Vector3(this.getValue(DIRECTIONX), this.getValue(DIRECTIONY), this.getValue(DIRECTIONZ));
    }

    public Point3 getPosition() {
        return new Point3(this.getValue(POSITIONX), this.getValue(POSITIONY), this.getValue(POSITIONZ));
    }

    public boolean getCastsShadow() {
        final SimpleBooleanProperty s = (SimpleBooleanProperty) lightValues.get(CASTSSHADOW);
        return s.getValue();
    }

    public SimpleBooleanProperty getCastsShadowProperty() {
        return (SimpleBooleanProperty) lightValues.get(CASTSSHADOW);
    }

    public double getHalfAngle() {
        return this.getValue(HALFANGLE);
    }

    public void setCastsShadow(boolean selected) {
        this.lightValues.put(CASTSSHADOW, new SimpleBooleanProperty(selected));
    }

    public void setHalfAngle(double value) {
        this.lightValues.put(HALFANGLE, new SimpleDoubleProperty(value));
    }

    public light.Light getLight() {
//		 printValues();
        switch (type) {
            case DIRECTIONAL:
                return new DirectionalLight(this.getColor(), this.getDirection(), this.getCastsShadow());
            case POINT:
                return new PointLight(this.getColor(), this.getPosition(), this.getCastsShadow());
            case SPOT:
                return new SpotLight(this.getColor(), this.getDirection(), this.getPosition(), Math.PI / this.getHalfAngle(), this.getCastsShadow());
            default:
                return null;
        }
    }

    public void printValues() {
        System.out.println("###########LIGHT###########");
        System.out.println(type);
        System.out.println(this.name);
        switch (type) {
            case DIRECTIONAL:
                System.out.println(this.getColor());
                System.out.println(this.getDirection());
                System.out.println(this.getCastsShadow());
                break;
            case POINT:
                System.out.println(this.getColor());
                System.out.println(this.getPosition());
                System.out.println(this.getCastsShadow());
                break;
            case SPOT:
                System.out.println(this.getColor());
                System.out.println(this.getDirection());
                System.out.println(this.getPosition());
                System.out.println(this.getHalfAngle());
                System.out.println(this.getCastsShadow());
                break;
        }
        System.out.println(this.name);
        System.out.println(type);
        System.out.println("###########LIGHT###########");
    }

    public void setType(Light type) {
        this.type = type;
    }
}
