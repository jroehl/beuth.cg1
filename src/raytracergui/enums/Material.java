package raytracergui.enums;

import material.LambertMaterial;
import material.PhongMaterial;
import material.ReflectiveMaterial;
import material.SingleColorMaterial;
import textures.Texture;

/**
 * Created by jroehl on 15.01.16.
 */
public enum Material {
    LAMBERT, PHONG, REFLECTIVE, SINGLECOLOR;

    public material.Material getMaterial(int exponent, Texture ... textures) {
//        printValues(exponent);
        switch (this) {
            case SINGLECOLOR:
                return new SingleColorMaterial(textures[0]);
            case LAMBERT:
                return new LambertMaterial(textures[0]);
            case PHONG:
                try {
                    return new PhongMaterial(textures[0], textures[1], exponent);
                } catch (Exception e) {
                    return null;
                }
            case REFLECTIVE:
                try {
                    return new ReflectiveMaterial(textures[0], textures[1], textures[2], exponent);
                } catch (Exception e) {
                    return null;
                }
            default:
                return null;
        }
    }

    public void printValues(int exponent) {
        System.out.println("###########MATERIAL###########");
        System.out.println(this);
        System.out.println(exponent);
        System.out.println("###########MATERIAL###########");
    }

//    @Override
//    public String toString() {
//        return "Material{" +
//                "exponent=" + exponent +
//                ", textures=" + textures +
//                ", type='" + this.name() + '\'' +
//                '}';
//    }
}
