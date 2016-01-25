package raytracergui.enums;

import Matrizen_Vektoren_Bibliothek.Point3;
import geometries.*;
import material.Material;

/**
 * Created by jroehl on 15.01.16.
 */
public enum Geometry {
    AXISALIGNEDBOX, BH, CYLINDER, CYLINDERBODY, DISC, PLANE, SPHERE, DYNAMICSPHERE, TRIANGLE, TRIANGLEPYRAMID;

    public geometries.Geometry getGeometry(Material material) {
        switch (this) {
            case AXISALIGNEDBOX:
                return new AxisAlignedBox(material);
            case BH:
                return new BH(material);
            case CYLINDER:
                return new Cylinder(material);
            case CYLINDERBODY:
                return new CylinderBody(material);
            case DISC:
                return new Disc(material);
            case PLANE:
                return new Plane(material);
            case SPHERE:
                return new Sphere(material);
            case DYNAMICSPHERE:
                return new DynamicSphere(material);
            case TRIANGLE:
                return new Triangle(material, new Point3(0,0,0), new Point3(0,0,0), new Point3(0,0,0));
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


