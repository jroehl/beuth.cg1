package textures;

import color.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InterpolatedTexture implements Texture {

	private final String path;
	private final File file;
	private BufferedImage img;

	public InterpolatedTexture(String path) {

		this.path = path;
		this.file = null;
		img = null;
		// Users/bodowissemann/Documents/GIT/Waschmaschine_CG_1/src
		try {
			// final String pathAct = System.getProperty("user.dir") +
			// "/Texturen/" + path;

			final File file = new File(path);

			img = ImageIO.read(file);
			// System.out.println(img);

		} catch (final IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public InterpolatedTexture(File file) {

		this.file = file;
		this.path = null;
		img = null;
		try {
			img = ImageIO.read(this.file);
		} catch (final IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Color colorFor(double n, double u) {
		final double xCoord = ImageTexture.getRelativeCoord(n);
		final double yCoord = ImageTexture.getRelativeCoord(u);

		final double x = xCoord * (this.img.getWidth() - 1);
		// siehe Vorgehen Raytracer: final Ray ray = camera.rayFor(iwidth,
		// iheight, x, iheight - 1 - y);
		final double y = (this.img.getHeight() - 1) - (yCoord * (img.getHeight() - 1));

		final double xInt = x - Math.floor(x);
		final double yInt = y - Math.floor(y);

		final Color colorA = ImageTexture.getColorOfPosition(this.img, (int) Math.floor(x), (int) Math.floor(y));
		final Color colorB = ImageTexture.getColorOfPosition(this.img, (int) Math.ceil(x), (int) Math.floor(y));
		final Color colorC = ImageTexture.getColorOfPosition(this.img, (int) Math.floor(x), (int) Math.ceil(y));
		final Color colorD = ImageTexture.getColorOfPosition(this.img, (int) Math.ceil(x), (int) Math.ceil(y));

		final Color colorAB = colorA.mul(1.0 - xInt).add(colorB.mul(xInt));
		final Color colorCD = colorC.mul(1.0 - xInt).add(colorD.mul(xInt));

		return colorAB.mul(1.0 - yInt).add(colorCD.mul(yInt));

	}

	public static double getRelativeCoord(final double in) {
		double out = in % 1.0;

		if (out < 0.0) {
			out = out + 1.0;
		}

		// System.out.println(in + " / " + in % 1.0 + "/////////" + out);
		return out;
	}

	public static Color getColorOfPosition(final BufferedImage image, final int x, final int y) {
		final java.awt.Color c = new java.awt.Color(image.getRGB(x, y));

		final double red = ((double) c.getRed() / 256) + 0.0001;
		final double green = ((double) c.getGreen() / 256) + 0.0001;
		final double blue = ((double) c.getBlue() / 256) + 0.0001;

		return new Color(red, green, blue);
	}

}
