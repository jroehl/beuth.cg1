package raytracergui.container;

import raytracergui.enums.Geometry;
import raytracergui.enums.Material;
import raytracergui.enums.Texture;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jroehl on 14.01.16.
 */
public class GeometryContainer {

    private Material material;
    private Geometry geometry;
    private Texture texture_1;
    private Texture texture_2;
    private Texture texture_3;
    private Object texture_1extra;
    private Object texture_2extra;
    private Object texture_3extra;
    private int exponent = 0;
    private double refractionIndex = 0;
    private String name;
    private geometries.Geometry fixedGeometry = null;
    private HashMap<String, ArrayList> objectValues;

    public GeometryContainer() {
    }

    public GeometryContainer(GeometryContainer geometryContainer) {
        this.name = geometryContainer.name;
        this.material = geometryContainer.material;
        this.geometry = geometryContainer.geometry;
        this.texture_1 = geometryContainer.texture_1;
        this.texture_2 = geometryContainer.texture_2;
        this.texture_3 = geometryContainer.texture_3;
        this.texture_1extra = geometryContainer.texture_1extra;
        this.texture_2extra = geometryContainer.texture_2extra;
        this.texture_3extra = geometryContainer.texture_3extra;
        this.exponent = geometryContainer.exponent;
        this.refractionIndex = geometryContainer.refractionIndex;
        this.fixedGeometry = geometryContainer.fixedGeometry;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void addGeometry(geometries.Geometry geometry) {
        this.fixedGeometry = geometry;
    }

    public void addTextures(HashMap<String, Object[]> textures) {

        Object[] texture_1s = textures.get("texture_0");
        Object[] texture_2s = textures.get("texture_1");
        Object[] texture_3s = textures.get("texture_2");

        if (texture_1s != null) {
            this.texture_1 = (Texture) texture_1s[0];
            this.texture_1extra = texture_1s[1];
        }
        if (texture_2s != null) {
            this.texture_2 = (Texture) texture_2s[0];
            this.texture_2extra = texture_2s[1];
        }
        if (texture_3s != null) {
            this.texture_3 = (Texture) texture_3s[0];
            this.texture_3extra = texture_3s[1];
        }
    }

    public void addMaterial(Material material, int exponent, double refractionIndex) {
        this.material = material;
        this.exponent = exponent;
        this.refractionIndex = refractionIndex;
    }

    public geometries.Geometry getGeometry() {
        if (fixedGeometry != null) {
            return fixedGeometry;
        } else if (geometry == Geometry.OBJECTFILE) {
            switch (material) {
                case SINGLECOLOR:
                case LAMBERT:
                    return (geometry.getGeometry(material.getMaterial(this.exponent, this.refractionIndex, this.texture_1.getTexture(this.texture_1extra)), objectValues));
                case PHONG:
                    return (geometry.getGeometry(material.getMaterial(this.exponent, this.refractionIndex, this.texture_1.getTexture(this.texture_1extra), this.texture_2.getTexture(this.texture_2extra)), objectValues));
                case REFLECTIVE:
                    return (geometry.getGeometry(material.getMaterial(this.exponent, this.refractionIndex, this.texture_1.getTexture(this.texture_1extra), this.texture_2.getTexture(this.texture_2extra), this.texture_3.getTexture(this.texture_3extra)), objectValues));
                case REFRACTIVE:
                    return (geometry.getGeometry(material.getMaterial(this.exponent, this.refractionIndex), objectValues));
                default:
                    return null;
            }
        }
        else {
            switch (material) {
                case SINGLECOLOR:
                case LAMBERT:
                    return (geometry.getGeometry(material.getMaterial(this.exponent, this.refractionIndex, this.texture_1.getTexture(this.texture_1extra))));
                case PHONG:
                    return (geometry.getGeometry(material.getMaterial(this.exponent, this.refractionIndex, this.texture_1.getTexture(this.texture_1extra), this.texture_2.getTexture(this.texture_2extra))));
                case REFLECTIVE:
                    return (geometry.getGeometry(material.getMaterial(this.exponent, this.refractionIndex, this.texture_1.getTexture(this.texture_1extra), this.texture_2.getTexture(this.texture_2extra), this.texture_3.getTexture(this.texture_3extra))));
                case REFRACTIVE:
                    return (geometry.getGeometry(material.getMaterial(this.exponent, this.refractionIndex)));
                default:
                    return null;
            }
        }
    }

    public void printValues() {
        System.out.println("###########GEOMETRY###########");
        System.out.println(this);
        System.out.println(this.name);
        System.out.println("###########GEOMETRY###########");

    }

    private color.Color generateColor(javafx.scene.paint.Color value) {
        return new color.Color(value.getRed(), value.getGreen(), value.getBlue());
    }

    public void addLoadedValues(HashMap<String, ArrayList> objectValues) {
        this.objectValues = objectValues;
    }
}
