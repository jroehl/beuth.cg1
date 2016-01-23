package raytracergui.container;

import Matrizen_Vektoren_Bibliothek.Point3;
import geometries.Geometry;
import geometries.Node;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ray.Transform;
import raytracergui.CalcHelper;
import raytracergui.dataclasses.HierarchyData;

import java.util.*;

/**
 * Created by jroehl on 13.01.16.
 */
public class NodeContainer implements HierarchyData<NodeContainer>, Cloneable {

    private NodeContainer parent = null;

    public NodeContainer(String name) {
        this.name = name;
        this.nodeValues = FXCollections.observableHashMap();
        for (String key : KEYS) {
            this.nodeValues.put(key, new SimpleDoubleProperty());
        }
        this.geometries = new HashMap<>();
        this.setScale(0.0, 0.0, 0.0);
        this.setTranslate(0.0, 0.0, 0.0);
        this.setTransformX(0.0);
        this.setTransformY(0.0);
        this.setTransformZ(0.0);
    }

    public NodeContainer(NodeContainer n) {
        this.name = n.getName();
        this.nodeValues = FXCollections.observableHashMap();
        for (String key : KEYS) {
            this.nodeValues.put(key, new SimpleDoubleProperty(n.get(key).getValue()));
        }
        this.geometries = new HashMap<>();
        for (GeometryContainer geometryContainer : n.getGeometryMap().values()) {
            GeometryContainer clone = new GeometryContainer(geometryContainer);
            this.geometries.put(clone.getName(), clone);
        }
    }

    private final String LABELTRANSX = "labelTransX";
    private final String LABELTRANSY = "labelTransY";
    private final String LABELTRANSZ = "labelTransZ";
    private final String LABELSCALEX = "labelScaleX";
    private final String LABELSCALEY = "labelScaleY";
    private final String LABELSCALEZ = "labelScaleZ";
    private final String LABELTRANSLATEX = "labelTranslateX";
    private final String LABELTRANSLATEY = "labelTranslateY";
    private final String LABELTRANSLATEZ = "labelTranslateZ";

    private final CalcHelper calcHelper = new CalcHelper();

    private String name;

    private final String[] KEYS = {LABELTRANSX, LABELTRANSY, LABELTRANSZ, LABELSCALEX, LABELSCALEY, LABELSCALEZ, LABELTRANSLATEX, LABELTRANSLATEY, LABELTRANSLATEZ};
    private ObservableMap<String, SimpleDoubleProperty> nodeValues;

    private ObservableList<NodeContainer> children = FXCollections.observableArrayList();

    private HashMap<String, GeometryContainer> geometries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeometryContainer getGeometry(String key) {
        return this.geometries.get(key);
    }

    public HashMap<String, GeometryContainer> getGeometryMap() {
        return this.geometries;
    }

    public Node getNode() {
        return new Node(new Transform().rotateY(this.getTransformX()).rotateX(this.getTransformY()).rotateZ(this.getTransformZ()).scale(this.getScaleX(), this.getScaleY(), this.getScaleZ()).translate(new Point3(this.getTranslateX(), this.getTranslateY(), this.getTranslateZ())), getGeometryList());
    }

    private ArrayList<Geometry> getGeometryList() {
        ArrayList<Geometry> geos = new ArrayList<>();
        if (!this.geometries.isEmpty()) {
            Iterator it = this.geometries.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if (pair.getValue() instanceof GeometryContainer) {
                    GeometryContainer g = (GeometryContainer) pair.getValue();
                    geos.add(g.getGeometry());
                }
            }
        }
        for (NodeContainer n : this.children) {
            geos.add(n.getNode());
        }

        return geos;
    }

    public void addGeometry(GeometryContainer geoCont) {
        this.geometries.put(geoCont.getName(), geoCont);
    }

    public void changeGeometry(GeometryContainer geoCont) {
    }

    public void removeGeometry(String geoName) {
        this.geometries.remove(geoName);
    }

    public Double getValue(String key) {
        return nodeValues.get(key).getValue();
    }

    public SimpleDoubleProperty get(String key) {
        return nodeValues.get(key);
    }

    public void setValue(String key, Double value) {
        this.nodeValues.get(key).set(value);
    }

    public double getCorrectedValue(String key) {
        return calcHelper.getDividedValue(this.getValue(key));
    }

    public Double getTransformX() {
        return this.getCorrectedValue(LABELTRANSX);
    }

    public Double getTransformY() {
        return this.getCorrectedValue(LABELTRANSY);
    }

    public Double getTransformZ() {
        return this.getCorrectedValue(LABELTRANSZ);
    }

    public void setTransformX(Double transformX) {
        this.nodeValues.get(LABELTRANSX).set(transformX);
    }

    public void setTransformY(Double transformY) {
        this.nodeValues.get(LABELTRANSY).set(transformY);
    }

    public void setTransformZ(Double transformZ) {
        this.nodeValues.get(LABELTRANSZ).set(transformZ);
    }

    public void setScale(double x, double y, double z) {
        this.nodeValues.get(LABELSCALEX).set(x);
        this.nodeValues.get(LABELSCALEY).set(y);
        this.nodeValues.get(LABELSCALEZ).set(z);
    }

    public Double getScaleX() {
        return this.getCorrectedValue(LABELSCALEX);
    }

    public Double getScaleY() {
        return this.getCorrectedValue(LABELSCALEY);
    }

    public Double getScaleZ() {
        return this.getCorrectedValue(LABELSCALEZ);
    }

    public void setTranslate(double x, double y, double z) {
        this.nodeValues.get(LABELTRANSLATEX).set(x);
        this.nodeValues.get(LABELTRANSLATEY).set(y);
        this.nodeValues.get(LABELTRANSLATEZ).set(z);
    }

    public Double getTranslateX() {
        return this.getCorrectedValue(LABELTRANSLATEX);
    }

    public Double getTranslateY() {
        return this.getCorrectedValue(LABELTRANSLATEY);
    }

    public Double getTranslateZ() {
        return this.getCorrectedValue(LABELTRANSLATEZ);
    }


    @Override
    public String toString() {
        return name;
//        return "NodeContainer{" +
//                "name='" + name + '\'' +
//                ", LABELTRANSX='" + LABELTRANSX + '\'' +
//                ", LABELTRANSY='" + LABELTRANSY + '\'' +
//                ", LABELTRANSY='" + LABELTRANSZ + '\'' +
//                ", LABELSCALEX='" + LABELSCALEX + '\'' +
//                ", LABELSCALEY='" + LABELSCALEY + '\'' +
//                ", LABELSCALEZ='" + LABELSCALEZ + '\'' +
//                ", KEYS=" + Arrays.toString(KEYS) +
//                ", nodeValues=" + nodeValues +
//                ", geometries=" + geometries +
//                '}';
    }

    @Override
    public ObservableList<NodeContainer> getChildren() {
        return this.children;
    }

    @Override
    public void addChild(NodeContainer n) {
        this.children.add(n);
    }

    public void removeChild(NodeContainer n) {
        for (NodeContainer x: this.children) {
            if (x.equals(n)) {
                this.children.remove(x);
            }
        }
    }

    public NodeContainer getParent() {
        return this.parent;
    }

    public void setParent(NodeContainer parent) {
        this.parent = parent;
    }

}



