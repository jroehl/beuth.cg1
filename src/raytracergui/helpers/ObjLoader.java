package raytracergui.helpers;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by jroehl on 26.01.16.
 */
public class ObjLoader {

    private static HashMap<String, ArrayList> returnValues;
    private File file;

    private final static String VERTEXTEXTURE = "vt";
    private final static String VERTEXNORMAL = "vn";
    private final static String VERTEX = "v";
    private final static String FACE = "f";

    private final static String WHITESPACE = " ";
    private final static String SEPARATOR = "/";

    private final static String VERTEXPOINTS = "VERTEXPOINTS";
    private final static String VERTEXTEXTUREPOINTS = "VERTEXTEXTUREPOINTS";
    private final static String VERTEXNORMALS = "VERTEXNORMALS";
    private final static String VERTEXFACES = "VERTEXFACES";
    private final static String VERTEXFACESTEXTURE = "VERTEXFACESTEXTURE";
    private final static String VERTEXFACESNORMAL = "VERTEXFACESNORMAL";

    private boolean vtExists;
    private boolean vnExists;


    public ObjLoader(File file) {
        this.file = file;
        this.returnValues = new HashMap<>();

        this.returnValues.put(VERTEXPOINTS, new ArrayList<Point3>());
        this.returnValues.put(VERTEXTEXTUREPOINTS, new ArrayList<Point3>());
        this.returnValues.put(VERTEXNORMALS, new ArrayList<Normal3>());
        this.returnValues.put(VERTEXFACES, new ArrayList<Point3>());
        this.returnValues.put(VERTEXFACESTEXTURE, new ArrayList<Point3>());
        this.returnValues.put(VERTEXFACESNORMAL, new ArrayList<Integer>());
    }

    private static ArrayList vertexFaces() {
        return returnValues.get(VERTEXFACES);
    }

    private static ArrayList vertexFacesTexture() {
        return returnValues.get(VERTEXFACESTEXTURE);
    }

    private static ArrayList vertexFacesNormal() {
        return returnValues.get(VERTEXFACESNORMAL);
    }

    private static ArrayList vertexPoints() {
        return returnValues.get(VERTEXPOINTS);
    }

    private static ArrayList vertexTexturesPoints() {
        return returnValues.get(VERTEXPOINTS);
    }

    private static ArrayList vertexNormals() {
        return returnValues.get(VERTEXNORMALS);
    }

    public HashMap<String, ArrayList> readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseObjInfo(line.trim());
            }
            return returnValues;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseObjInfo(String line) {
        if (line.startsWith(VERTEXTEXTURE)) {
            processVertexTexture(line);
        } else if (line.startsWith(VERTEXNORMAL)) {
            processNormals(line);
        } else if (line.startsWith(VERTEX)) {
            processVertex(line);
        } else if (line.startsWith(FACE)) {
            processFace(line);
        }
    }

    private void processFace(String line) {

        String[] partPoints = line.substring(FACE.length()).trim().split(WHITESPACE);

        int v[] = new int[partPoints.length];
        int vt[] = new int[partPoints.length];
        int vn[] = new int[partPoints.length];

        for (int i = 0; i < partPoints.length; i++) {
            String[] coords = partPoints[i].split(SEPARATOR);
            v[i] = Integer.parseInt(coords[0], 10);
            if (coords.length > 1) {
                if (coords[1].length() != 0) {
                    this.vtExists = true;
                    vt[i] = Integer.parseInt(coords[1], 10);
                }
                if (coords.length == 3) {
                    this.vnExists = true;
                    vn[i] = Integer.parseInt(coords[2], 10);
                }
            }
        }

        vertexFaces().add(new Point3(v[0], v[1], v[2]));

        if (this.vtExists) {
            vertexFacesTexture().add(new Point3(vt[0], vt[1], vt[2]));
        }
        if (this.vnExists) {
            vertexFacesNormal().add(vn[0]);
        }

    }

    private void processNormals(String line) {
        Scanner scanner = new Scanner(line.substring(VERTEXNORMAL.length()).trim());
        scanner.useLocale(Locale.US);
        final ArrayList<Float> floats = new ArrayList<>();
        while (scanner.hasNext()) {
            floats.add(scanner.nextFloat());
        }
        try {
            vertexNormals().add(new Normal3(floats.get(0), floats.get(1), floats.get(2)));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            vertexNormals().add(new Normal3(floats.get(0), floats.get(1), 0.0));
        }
    }

    private void processVertexTexture(String line) {
        Scanner scanner = new Scanner(line.substring(VERTEXTEXTURE.length()).trim());
        scanner.useLocale(Locale.US);
        final ArrayList<Float> floats = new ArrayList<>();
        while (scanner.hasNext()) {
            floats.add(scanner.nextFloat());
        }
        try {
            vertexTexturesPoints().add(new Point3(floats.get(0), floats.get(1), floats.get(2)));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            vertexTexturesPoints().add(new Point3(floats.get(0), floats.get(1), 0.0));
        }
    }

    private void processVertex(String line) {
        Scanner scanner = new Scanner(line.substring(VERTEX.length()).trim());
        scanner.useLocale(Locale.US);
        final ArrayList<Float> floats = new ArrayList<>();
        while (scanner.hasNext()) {
            floats.add(scanner.nextFloat());
        }
        vertexPoints().add(new Point3(floats.get(0), floats.get(1), floats.get(2)));
    }
}
