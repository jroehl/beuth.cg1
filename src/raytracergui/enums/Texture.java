package raytracergui.enums;

import textures.ImageTexture;
import textures.InterpolatedTexture;
import textures.SingleColorTexture;

import java.io.File;

/**
 * Created by jroehl on 15.01.16.
 */
public enum Texture {
	IMAGE, INTERPOLATEDIMAGE, SINGLECOLOR;

	public textures.Texture getTexture(Object object) {

//		printValue(object);

		switch (this) {
			case IMAGE :
				if (object instanceof File)
					return new ImageTexture((File) object);
			case INTERPOLATEDIMAGE :
				if (object instanceof File)
					return new InterpolatedTexture((File) object);
			case SINGLECOLOR :
				if (object instanceof javafx.scene.paint.Color)
					return new SingleColorTexture(generateColor((javafx.scene.paint.Color) object));
			default :
				return null;
		}
	}

	private void printValue(Object object) {
		System.out.println("#########TEXTURE#########");
		System.out.println(this);
		try {
			System.out.println(object);
		} catch (final Exception e) {
			System.out.println(generateColor((javafx.scene.paint.Color) object));
		}
		System.out.println("#########TEXTURE#########");
	}

	private color.Color generateColor(javafx.scene.paint.Color value) {
		return new color.Color(value.getRed(), value.getGreen(), value.getBlue());
	}
}
