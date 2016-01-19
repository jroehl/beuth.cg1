package geometries;

import material.Material;
import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Mat3x3;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.TexCoord2;

/**
 * Triangle
 *
 * @author Waschmaschine
 *         <p>
 *         Die von Geometry abgeleitete Klasse Triangle implementiert die
 *         Methode hit entsprechend der Formeln und Algorithmen zur
 *         Schnittberechnung.
 */
public class Triangle extends Geometry {

	/**
	 * a - Point3 Objekt des Triangle
	 */
	private final Point3 a;
	/**
	 * b - Point3 Objekt des Triangle
	 */
	private final Point3 b;
	/**
	 * c - Point3 Objekt des Triangle
	 */
	private final Point3 c;

	public final TexCoord2 texCoordA;

	public final TexCoord2 texCoordB;

	public final TexCoord2 texCoordC;

	private Normal3 na;
	private Normal3 nb;
	private Normal3 nc;
	private double alpha;
	private double beta;
	private double gamma;

	/**
	 * Konstruktor: Triangle
	 *
	 * @param material
	 *            Material des Objekts (enthält Textur, welche
	 *            Color-Informationen enthält)
	 * @param a
	 *            Point3 Objekt des Triangle
	 * @param b
	 *            Point3 Objekt des Triangle
	 * @param c
	 *            Point3 Objekt des Triangle
	 * @throws IllegalArgumentException
	 */
	// Konstruktor ohne übergebene Normalen - es wird die Normale zur
	// Dreiecksoberfläche errechnet und diese dann für die drei Eck-Normalen
	// eingesetzt - wir bekommen ein ebenes Dreieck
	public Triangle(Material material, Point3 a, Point3 b, Point3 c) throws IllegalArgumentException {
		super(material);

		if (a == null) {
			throw new IllegalArgumentException("The a cannot be null!");
		}
		if (b == null) {
			throw new IllegalArgumentException("The b cannot be null!");
		}
		if (c == null) {
			throw new IllegalArgumentException("The c cannot be null!");
		}

		this.a = a;
		this.b = b;
		this.c = c;

		final Normal3 n0 = createNormalToSurface();

		this.na = n0;
		this.nb = n0;
		this.nc = n0;

		this.texCoordA = new TexCoord2(na.x, -na.z);
		this.texCoordB = new TexCoord2(nb.x, -nb.z);
		this.texCoordC = new TexCoord2(nc.x, -nc.z);
	}

	public Triangle(Material material) throws IllegalArgumentException {
		this(material, new Point3(0,0,0), new Point3(0,0,0), new Point3(0,0,0));
	}

	/**
	 * Konstruktor: Triangle
	 *
	 * @param material
	 *            Material des Objekts (enthält Textur, welche
	 *            Color-Informationen enthält)
	 * @param a
	 *            Point3 Objekt des Triangle
	 * @param b
	 *            Point3 Objekt des Triangle
	 * @param c
	 *            Point3 Objekt des Triangle
	 * @param na
	 *            Normal3 zur Ecke a
	 * @param nb
	 *            Normal3 zur Ecke b
	 * @param nc
	 *            Normal3 zur Ecke c
	 * @param texCoordA
	 *            TexturKoordinate zur Ecke a
	 * @param texCoordB
	 *            TexturKoordinate zur Ecke b
	 * @param texCoordC
	 *            TexturKoordinate zur Ecke c
	 * @throws IllegalArgumentException
	 */
	public Triangle(Material material, Point3 a, Point3 b, Point3 c, Normal3 na, Normal3 nb, Normal3 nc, final TexCoord2 texCoordA,
			final TexCoord2 texCoordB, final TexCoord2 texCoordC) throws IllegalArgumentException {
		this(material, a, b, c);
		this.na = na;
		this.nb = nb;
		this.nc = nc;

	}

	/**
	 * Method: hit(ray)
	 *
	 * @param ray
	 *            Ray Objekt
	 * @return Hit / null Bei einem Treffer wird das generierte Hit Objekt
	 *         zurückgegeben und null vice versa
	 * @throws IllegalArgumentException
	 */
	@Override
	public Hit hit(Ray ray) throws IllegalArgumentException {

		if (ray == null) {
			throw new IllegalArgumentException("The Ray cannot be null!");
		}

		final Mat3x3 matA = new Mat3x3(a.x - b.x, a.x - c.x, ray.direction.x, a.y - b.y, a.y - c.y, ray.direction.y, a.z - b.z, a.z - c.z,
				ray.direction.z);

		final Vector3 vec = a.sub(ray.origin);

		beta = matA.changeCol1(vec).determinant / matA.determinant;
		gamma = matA.changeCol2(vec).determinant / matA.determinant;

		alpha = 1 - beta - gamma;

		final double t = matA.changeCol3(vec).determinant / matA.determinant;

		if ((beta > 0.0 && gamma > 0.0) && (beta + gamma) <= 1) {
			final TexCoord2 texCoord = texCoordA.mul(alpha).add(texCoordB).mul(beta).add(texCoordC).mul(gamma);
			return new Hit(t, ray, this, createNormalToPoint(), texCoord);
		}
		return null;
	}

	private Normal3 createNormalToSurface() {
		final Vector3 v = this.b.sub(this.a);
		final Vector3 w = this.c.sub(this.a);

		final Normal3 normalToSurface = v.x(w).normalized().asNormal();

		return normalToSurface;
	}

	private Normal3 createNormalToPoint() {
		final Normal3 nPoint = na.mul(alpha).add(nb.mul(beta)).add(nc.mul(gamma));
		return nPoint;
	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param o
	 *            Objekt das mit der Matrix verglichen wird
	 * @return true | false
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Triangle triangle = (Triangle) o;

		if (a != null ? !a.equals(triangle.a) : triangle.a != null)
			return false;
		if (b != null ? !b.equals(triangle.b) : triangle.b != null)
			return false;
		return !(c != null ? !c.equals(triangle.c) : triangle.c != null);

	}

	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */
	@Override
	public int hashCode() {
		int result = a != null ? a.hashCode() : 0;
		result = 31 * result + (b != null ? b.hashCode() : 0);
		result = 31 * result + (c != null ? c.hashCode() : 0);
		return result;
	}

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Geometry Werte
	 */
	@Override
	public String toString() {
		return "Triangle{" + "a=" + a + ", b=" + b + ", c=" + c + '}';
	}
}
