package raytracergui;

import raytracergui.helpers.ObjLoader;

import java.io.File;

/**
 * Created by jroehl on 26.01.16.
 */
public class TestObjLoader {

    public static void main(String[] args) {

        File file = new File("/Users/jroehl/Downloads/cart.obj");

        ObjLoader objLoader = new ObjLoader(file);

        objLoader.readFile();
    }

}
