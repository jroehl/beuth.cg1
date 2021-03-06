package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;
import geometries.Geometry;
import geometries.Hit;
import ray.Ray;
import ray.World;

/**
 * PointLight.
 *
 * Strahlt gleichmäßig in alle Richtungen wie eine Lampe mit einer festen Position
 */
public class PointLight extends Light {

	/**
	 * Position an der sich das Licht befindet.
	 */
	private final Point3 pl;

	/**
	 * Konstruktor: PointLight
	 *
	 * @param position
	 *            Position (Point3) des Lichts
	 * @param color
	 *            Farbe (Color) des Lichts
	 * @param castsShadows
	 *            boolean - ob Gegenstand einen Schatten wirft
	 * @throws IllegalArgumentException
	 */
	public PointLight(Color color, Point3 position, boolean castsShadows) throws IllegalArgumentException {

		super(color, castsShadows);

		if (position == null) {
			throw new IllegalArgumentException("position cannot be null!");
		}

		this.pl = position;
	}

	/**
	 * Method: illuminates(Point3)
	 *
	 * @param p
	 *            Übergebenes Point3 - Objekt
	 * @param world
	 *            - ob World Objekt
	 * @return true / false wenn der übergebene Punkt beleuchtet wird
	 * @throws IllegalArgumentException
	 */
	@Override
	public boolean illuminates(Point3 p, World world) throws IllegalArgumentException {
		if (p == null) {
			throw new IllegalArgumentException("The point3 cannot be null!");
		}

		if (castsShadows) {
			final Ray r = new Ray(p, directionFrom(p));

			final double tMax = r.tOf(pl);
			final double tMin = 0.0001;
			double t2 = 0;
			for (final Geometry g : world.objs) {

				final Hit h = g.hit(r);
				if (h != null) {

					t2 = h.t;
				}
				if (t2 >= tMin && t2 <= tMax) {

					return false;

				}
			}
		}
		return true;
	}

	/**
	 * Method: directionFrom(Point3)
	 *
	 * @param p
	 *            Übergebenes Point3 - Objekt
	 * @return Der zur Lichtquelle zeigende Vector3
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector3 directionFrom(Point3 p) throws IllegalArgumentException {
		if (p == null) {
			throw new IllegalArgumentException("The point3 cannot be null!");
		}
		return pl.sub(p).normalized();
	}

	@Override
	public String toString() {
		return "PointLight [pl=" + pl + ", cl=" + color + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((pl == null) ? 0 : pl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PointLight other = (PointLight) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (pl == null) {
			if (other.pl != null)
				return false;
		} else if (!pl.equals(other.pl))
			return false;
		return true;
	}
}