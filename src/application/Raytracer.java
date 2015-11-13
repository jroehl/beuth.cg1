package application;

import geometries.AxisAlignedBox2;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import ray.Ray;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import camera.Camera;
import camera.PerspectiveCamera;
import color.Color;

public class Raytracer extends Application {

	WritableImage wrImg;
	Text renderingTime;
	Stage primaryStage;
	ImageView imgView;

	Camera camera;
	World world;

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("PNG Creator");

		primaryStage.setWidth(640);
		primaryStage.setHeight(480);

		final Menu fileMenu = new Menu("File");

		final MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(fileMenu);
		menuBar.prefWidthProperty()
				.bind(primaryStage.widthProperty().divide(2));
		final MenuItem save = new MenuItem("Save");
		fileMenu.getItems().add(save);

		final HBox hBox = new HBox();

		renderingTime = new Text();
		renderingTime.setFill(javafx.scene.paint.Color.WHITE);

		final Group group = new Group();
		final ImageView imgView = new ImageView();
		group.getChildren().addAll(imgView, hBox);
		hBox.getChildren().addAll(menuBar, renderingTime);

		primaryStage.setScene(new Scene(group));

		// Plane wird erzeugt

		final Color color1 = new Color(0, 1, 0);
		final Point3 ap = new Point3(0, -1, 0);
		final Normal3 np = new Normal3(0, 1, 0);

		final Plane plane = new Plane(color1, ap, np);

		// Sphere wird erzeugt

		final Color color2 = new Color(1, 0, 0);
		final Point3 cs = new Point3(0, 0, -3);
		final double rs = 0.5;

		final Sphere sphere = new Sphere(color2, cs, rs);

		// Box wird erzeugt

		final Color color3 = new Color(0, 0, 1);
		final Point3 lbf = new Point3(-0.5, 0, -0.5);
		final Point3 run = new Point3(0.5, 1, 0.5);

		final AxisAlignedBox2 box = new AxisAlignedBox2(color3, lbf, run);

		// Trinagle erzeugen

		final Color color4 = new Color(1, 0, 1);
		final Point3 at = new Point3(-0.5, -0.5, -3);
		final Point3 bt = new Point3(0.5, -0.5, -3);
		final Point3 ct = new Point3(0.5, 0.5, -3);

		final Triangle triangle = new Triangle(color4, at, bt, ct);

		// Welt wird erzeugt

		final Color backgroundColor = new Color(0, 0, 0);

		world = new World(backgroundColor);
		// world.add(box);
		world.add(plane);
		world.add(sphere);
		// world.add(triangle);

		// Kamera wird erzeugt

		final Point3 e = new Point3(0, 0, 0);
		final Vector3 g = new Vector3(0, 0, -1);
		final Vector3 t = new Vector3(0, 1, 0);
		final double angle = Math.PI / 4;

		camera = new PerspectiveCamera(e, g, t, angle);

		// 2. Kamera

		// final Point3 e = new Point3(3, 3, 3);
		// final Vector3 g = new Vector3(-3, -3, -3);
		// final Vector3 t = new Vector3(0, 1, 0);
		// final double angle = Math.PI / 4;
		//
		// camera = new PerspectiveCamera(e, g, t, angle);

		drawImage(primaryStage.getWidth(), primaryStage.getHeight(), camera);
		imgView.setImage(wrImg);

		save.setOnAction(event -> {
			final FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Image");
			final FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(
					"JPG files (*.jpg)", "*.JPG");
			final FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(
					"PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters()
					.addAll(extFilterJPG, extFilterPNG);
			final File file = fileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(wrImg, null), "png",
							file);
				} catch (final IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		/**
		 * Über einen AddListener an der HeightProperty und der WidthProperty
		 * der primaryStage wird das neu Zeichnen des Bildes aufgerufen
		 */

		primaryStage.heightProperty().addListener(
				(ChangeEvent) -> {
					drawImage(primaryStage.getWidth(),
							primaryStage.getHeight(), camera);
					imgView.setImage(wrImg);
				});

		primaryStage.widthProperty().addListener(
				(ChangeEvent) -> {
					drawImage(primaryStage.getWidth(),
							primaryStage.getHeight(), camera);
					imgView.setImage(wrImg);
				});

		primaryStage.show();
	}

	/**
	 * Geht von oben nach unten jedes Pixel durch und gibt diesen Farbe
	 *
	 * @param width
	 *            Breite des Bilds
	 * @param height
	 *            Höhe des Bilds
	 */

	private void drawImage(double width, double height, Camera camera) {

		wrImg = new WritableImage((int) width, (int) height);

		final long start = System.nanoTime();

		Ray ray;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ray = camera.rayFor((int) width, (int) height, x, (int) height
						- 1 - y);
				final Color c = world.hit(ray);
				final javafx.scene.paint.Color javaColor = new javafx.scene.paint.Color(
						c.r, c.g, c.b, 1);
				wrImg.getPixelWriter().setColor(x, y, javaColor);
			}
		}
		final long end = System.nanoTime();
		renderingTime
				.setText((" Rendering Time: " + (end - start) / 1000000000.0F));
	}

	/**
	 * Liefert den Farbwert zurück um eine Diagonale Linie in einem schwarzen
	 * Bild zu malen.
	 *
	 * @param x
	 *            , X Coordinate eines Pixels im Bild
	 * @param y
	 *            Y Coordinate eines Pixels im Bild
	 * @return Farbe wie das Pixel eingefärbt werden soll
	 */

	// private Color getColor(Ray ray) {
	//
	// if (ray == null) {
	// throw new IllegalArgumentException("The ray cannot be null!");
	// }
	//
	// final Hit hit = world.hit(ray);
	//
	// if (hit != null) {
	//
	// return color;
	//
	// } else {
	// return world.backgroundColor;
	// }
	// }

	/**
	 * Start-Methode für FX
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}