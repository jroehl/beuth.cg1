package textures;

import color.Color;

public class SingleColorTexture implements Texture {

	private final Color c;

	public SingleColorTexture(Color c) {
		this.c = c;
	}

	@Override
	public Color colorFor(double n, double u) {
		// TODO Auto-generated method stub
		return this.c;
	}

}
