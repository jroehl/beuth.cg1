package geometries;

import ray.Ray;
import Matrizen_Vektoren_Bibliothek.Point3;
import color.Color;

public class Sphere extends Geometry {

	public final Point3 center;
	public final double radius;

	public Sphere(Color color, Point3 center, double radius) {
		super(color);
		this.center = center;
		this.radius = radius;

	}

	@Override
	public Hit hit(Ray ray) {

		final double a = ray.direction.dot(ray.direction);
		final double b = ray.direction.dot(ray.origin.sub(center).mul(2));
		final double c = (ray.origin.sub(center).dot(ray.origin.sub(center))) - (this.radius*this.radius);

//		unter der wurzel
		final double d = (b*b) - (4 * a * c);

		if (d > 0) {
			final double t1 = (-b + Math.sqrt(d)) / (2 * a);
			final double t2 = (-b - Math.sqrt(d)) / (2 * a);

			double t = 0;

			if(t2 < 0 && t1 < 0){
				t = Math.max(t1, t2);
			}

			if(t2 > 0 && t1 > 0){
				t = Math.min(t1,t2);
			}
			if(t2 > 0 && t1 < 0){
				t = t2;
			}
			if(t2 < 0 && t1 > 0){
				t = t1;
			}
			if(t > 0){
				return new Hit(t, ray, this);
			}

		}

		return null;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Sphere sphere = (Sphere) o;

		if (Double.compare(sphere.radius, radius) != 0) return false;
		return !(center != null ? !center.equals(sphere.center) : sphere.center != null);

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = center != null ? center.hashCode() : 0;
		temp = Double.doubleToLongBits(radius);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Sphere{" +
				"center=" + center +
				", radius=" + radius +
				'}';
	}
}
