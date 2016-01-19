package raytracergui.enums;

import geometries.*;
import material.Material;

/**
 * Created by jroehl on 15.01.16.
 */
public enum Geometry {
    AXISALIGNEDBOX, CYLINDER, DISC, PLANE, SPHERE, TRIANGLE, TRIANGLEPYRAMID;

    public geometries.Geometry getGeometry(Material material) {
        switch (this) {
            case AXISALIGNEDBOX:
                return new AxisAlignedBox(material);
            case CYLINDER:
                return new Cylinder(material);
            case DISC:
                return new Disc(material);
            case PLANE:
                return new Plane(material);
            case SPHERE:
                return new Sphere(material);
            case TRIANGLE:
                return new Triangle(material);
            case TRIANGLEPYRAMID:
                return new TrianglePyramid(material);
            default:
                return null;
        }
    }

//    @Override
//    public String toString() {
//        return "Geometry{" +
//                "name='" + this.name + '\'' +
//                ", type='" + this.name() + '\'' +
//                '}';
//    }
}


