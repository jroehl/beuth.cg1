package geometries;

import material.Material;
import material.SingleColorMaterial;
import ray.Ray;
import textures.SingleColorTexture;
import color.Color;

/**
 * Geometry
 *
 * @author Waschmaschine
 *         <p>
 *         Die Geomotrie-Klasse beinhaltet als Attribut eine Farbe. Darüber
 *         hinaus deklariert sie die Methode hit, welche einen Strahl als
 *         Parameter entgegen nimmt. In einer Implementierung dieser Methode
 *         erfolgt die Schnittberechnung. Eine Implementierung gibt bei mehreren
 *         Schnittpunkten immer den mit dem kleinsten positiven t zurück. Wird
 *         kein Schnittpunkt gefunden, wird null zurückgegeben.
 */
public abstract class Geometry {

	/**
	 * material - das übergebene Materiel enthällt die Textur, welche entweder
	 * Color-Objekte oder Farbinformationen aus einem Bild enthällt
	 */
	public Material material;

	public Geometry() throws IllegalArgumentException {

		this.material = new SingleColorMaterial(new SingleColorTexture(new Color(1, 0, 0)));
	}

	/**
	 * Konstruktor: Geometry
	 *
	 * @param material
	 *            Materiel der Geometrie
	 * @throws IllegalArgumentException
	 */
	public Geometry(Material material) throws IllegalArgumentException {

		if (material == null) {
			throw new IllegalArgumentException("The Color cannot be null!");
		}

		this.material = material;
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
	public abstract Hit hit(Ray ray) throws IllegalArgumentException;

	/**
	 * Ueberschriebene toString-Methode
	 *
	 * @return String Geometry Werte
	 */

	@Override
	public String toString() {
		return "Geometry [material=" + material + "]";
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
		result = prime * result + ((material == null) ? 0 : material.hashCode());
		return result;
	}

	/**
	 * Ueberschriebene equals-Methode
	 *
	 * @param obj
	 *            Objekt das mit der Matrix verglichen wird
	 * @return true | false
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Geometry other = (Geometry) obj;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		return true;
	}
}
