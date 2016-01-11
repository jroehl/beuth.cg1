package color;

public class TexCoord2 {

	public final double u;
	public final double v;

	public TexCoord2(double u, double v) {
		this.u = u;
		this.v = v;

	}

	public TexCoord2 add(final TexCoord2 t) {
		return new TexCoord2(u + t.u, v + t.v);
	}

	public TexCoord2 mul(final double n) {
		return new TexCoord2(u * n, v * n);
	}

}
