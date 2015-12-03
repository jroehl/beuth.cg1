package light;

import ray.Ray;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import color.Color;

/**
 * DirectionalLight
 *
 * @author Waschmaschine
 *         <p>
 *         Das directional light stellt eine unendlich weit entfernte
 *         Lichtquelle dar. Die Richtung bleibt dabei überall gleich.
 */
public class DirectionalLight extends Light {

	private final Vector3 direction;

	/**
	 * Konstruktor: DirectionalLight
	 *
	 * @param color
	 *            RGB Color der Lichts
	 * @param direction
	 *            Vector3 des Lichts
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
	 * @return true / false wenn der übergebene Punkt beleuchtet wird
	 * @throws IllegalArgumentException
	 */
	@Override
	public boolean illuminates(Point3 p, World world) throws IllegalArgumentException {
		if (p == null) {
			throw new IllegalArgumentException("The point3 cannot be null!");
		}
		if (castsShadows) {
			if(world.hitHit(new Ray(p, directionFrom(p))) == null){
				return true;
			}
			else{
				return false;
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

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String DirectionalLight Werte
	 */
	@Override
	public String toString() {
		return "DirectionalLight [direction=" + direction + ", color=" + color + "]";
	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param obj
	 *            Objekt das mit der Matrix verglichen wird
	 * @return true | false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DirectionalLight other = (DirectionalLight) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		return true;
	}

	/**
	 * Ueberschriebene hashCode-Methode
	 *
	 * @return int hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		return result;
	}
}
