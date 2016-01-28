package geometries;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.TexCoord2;
import material.Material;
import ray.Ray;
import ray.Transform;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jroehl on 26.01.16.
 */
public class ObjectFile extends Geometry {

    private final static String VERTEXPOINTS = "VERTEXPOINTS";
    private final static String VERTEXTEXTUREPOINTS = "VERTEXTEXTUREPOINTS";
    private final static String VERTEXNORMALS = "VERTEXNORMALS";
    private final static String VERTEXFACES = "VERTEXFACES";
    private final static String VERTEXFACESTEXTURE = "VERTEXFACESTEXTURE";
    private final static String VERTEXFACESNORMAL = "VERTEXFACESNORMAL";

    private final ArrayList<Geometry> triangles;
    private final ArrayList<Point3> vertexPoints;
    private final ArrayList<Point3> vertexTexturesPoints;
    private final ArrayList<Normal3> vertexNormals;
    private final ArrayList<Point3> vertexFaces;
    private final ArrayList<Point3> vertexFacesTexture;
    private final ArrayList<Integer> vertexFacesNormal;

    public ObjectFile(Material material, HashMap<String, ArrayList> values) {
        super(material);
        this.triangles = new ArrayList<>();
        this.vertexPoints = values.get(VERTEXPOINTS);
        this.vertexTexturesPoints = values.get(VERTEXTEXTUREPOINTS);
        this.vertexNormals = values.get(VERTEXNORMALS);
        this.vertexFaces = values.get(VERTEXFACES);
        this.vertexFacesTexture = values.get(VERTEXFACESTEXTURE);
        this.vertexFacesNormal = values.get(VERTEXFACESNORMAL);
    }

    public Node getObj() {

        for (int i = 0; i < vertexFaces.size(); i++) {

            final Point3 vertexFace = vertexFaces.get(i);

            Point3 a = new Point3(0, 0, 0);
            Point3 b = new Point3(0, 0, 0);
            Point3 c = new Point3(0, 0, 0);

            try {

                a = vertexPoints.get((int) Math.abs(vertexFace.x) - 1);
                b = vertexPoints.get((int) Math.abs(vertexFace.y) - 1);
                c = vertexPoints.get((int) Math.abs(vertexFace.z) - 1);

            } catch (final ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            Normal3 normal;
            try {
                normal = vertexNormals.get(vertexFacesNormal.get(i) - 1).mul(-1);
            } catch (final Exception e) {
                normal = c.sub(a).x(b.sub(a)).normalized().asNormal();
            }
            triangles.add(new Triangle(material, a, b, c, normal, normal, normal,new TexCoord2(0, 0),new TexCoord2(0, 0),new TexCoord2(0, 0)));
        }

        return new Node(new Transform(), triangles);
    }

    @Override
    public Hit hit(Ray ray) throws IllegalArgumentException {
        return null;
    }
}
