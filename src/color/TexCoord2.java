package color;

/**
 *
 * Schnittstelle zwischen der Oberfläche im Raytracer und der Geometrie
 *
 * @author waschmaschine
 *
 *         Die Klasse texCoord2 stellt jeweils eine Koordinate der textur da
 *
 */

public class TexCoord2 {
	/**
	 * @ param u double-Wert u
	 */
	public final double u;

	/**
	 * @ param u double-Wert v
	 */
	public final double v;

	public TexCoord2(double u, double v) {
		this.u = u;
		this.v = v;

	}

	/**
	 * Methode add(TexCoord2)
	 *
	 * @param t
	 *            double-Werte der übergeben texCoord2 werden benötigt
	 * @return neue texCoord2 mit verrechneten Werten u und v
	 */
	public TexCoord2 add(final TexCoord2 t) {
		return new TexCoord2(u + t.u, v + t.v);
	}

	/**
	 * Methode mul(TexCoord2)
	 *
	 * @param t
	 *            double-Werte der übergeben texCoord2 werden benötigt
	 * @return neue texCoord2 mit verrechneten Werten u und v
	 */

	public TexCoord2 mul(final double n) {
		return new TexCoord2(u * n, v * n);
	}

	/**
	 * überschriebene hashCode-Methode
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(u);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(v);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * überschriebene equals-Methode
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
		final TexCoord2 other = (TexCoord2) obj;
		if (Double.doubleToLongBits(u) != Double.doubleToLongBits(other.u))
			return false;
		if (Double.doubleToLongBits(v) != Double.doubleToLongBits(other.v))
			return false;
		return true;
	}

	/**
	 * überschriebene toString-Methode
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TexCoord2 [u=" + u + ", v=" + v + "]";
	}

}
