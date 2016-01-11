package textures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import color.Color;

public class ImageTexture implements Texture {

	private final String path;
	private BufferedImage img;

	public ImageTexture(String path) {

		this.path = path;
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
	@Override
	public Color colorFor(double n, double u) {
		final double xCoord = ImageTexture.getRelativeCoord(n);
		final double yCoord = ImageTexture.getRelativeCoord(u);

		final double x = xCoord * (this.img.getWidth() - 1);
		// siehe Vorgehen Raytracer: final Ray ray = camera.rayFor(iwidth,
		// iheight, x, iheight - 1 - y);
		final double y = (this.img.getHeight() - 1) - (yCoord * (img.getHeight() - 1));

		return ImageTexture.getColorOfPosition(this.img, (int) Math.round(x), (int) Math.round(y));
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
