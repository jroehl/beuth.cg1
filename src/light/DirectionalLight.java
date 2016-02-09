package light;

import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;
import geometries.Geometry;
import geometries.Hit;
import ray.Ray;
import ray.World;

/**
 * DirectionalLight
 *
 * Das directional light stellt eine unendlich weit entfernte Lichtquelle dar. Die Richtung bleibt dabei überall gleich.
 */
public class DirectionalLight extends Light {

	/**
	 * Richtung aus der das Licht kommt
	 */
	private final Vector3 direction;

	/**
	 * Konstruktor: DirectionalLight
	 *
	 * @param color
	 *            RGB Color der Lichts
	 * @param direction
	 *            Vector3 des Lichts
	 * @param castsShadows
	 *            boolean - ob Gegenstand einen Schatten wirft
	 * @throws IllegalArgumentException
	 */
	public DirectionalLight(Color color, Vector3 direction, boolean castsShadows) throws IllegalArgumentException {
		super(color, castsShadows);
		if (direction == null) {
			throw new IllegalArgumentException("The direction cannot be null!");
		}
		this.direction = direction;
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

			final double tMax = Double.MAX_VALUE;
			final double tMin = 0.00001;
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
		return direction.mul(-1).normalized();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DirectionalLight other = (DirectionalLight) obj;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirectionalLight [direction=" + direction + ", color=" + color + ", castsShadows=" + castsShadows + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		return result;
	}
}