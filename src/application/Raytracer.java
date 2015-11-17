package application;

import geometries.AxisAlignedBox;
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

import material.Material;
import material.SingleColorMaterial;
import ray.Ray;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import camera.Camera;
import color.Color;

public class Raytracer extends Application {

	/**
	 * wrImg - WritableImage Objekt
	 */
	WritableImage wrImg;
	/**
	 * renderingTime - Text Objekt
	 */
	Text renderingTime;

	/**
	 * camera - Kamera Objekt
	 */
	Camera camera;
	/**
	 * world - Welt Objekt
	 */
	World world;

	/**
	 * @param primaryStage
	 *            primaryStage der FX Applikation
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("PNG Creater");

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

		createWorld();
		createCamera();

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
	 * Method: createWorld
	 * <p>
	 * Erzeugt die Welt und die Objekte für den Test
	 */
	public void createWorld() {
		// Plane wird erzeugt

		final Material mat1 = new SingleColorMaterial(new Color(0, 1, 0));
		final Point3 ap = new Point3(0, -1, 0);
		final Normal3 np = new Normal3(0, 1, 0);

		final Plane plane = new Plane(mat1, ap, np);

		// Sphere wird erzeugt

		final Material mat2 = new SingleColorMaterial(new Color(1, 0, 0));
		final Point3 cs = new Point3(0, 0, -3);
		final double rs = 0.5;

		final Sphere sphere = new Sphere(mat1, cs, rs);

		// Spheres1 & 2 werden erzeugt

		final Point3 c1s = new Point3(-1, 0, -3);
		final Point3 c2s = new Point3(1, 0, -6);

		final Sphere sphere1 = new Sphere(mat2, c1s, rs);
		final Sphere sphere2 = new Sphere(mat2, c2s, rs);

		// Box wird erzeugt
		final Material mat3 = new SingleColorMaterial(new Color(0, 0, 1));
		final Point3 lbf = new Point3(-0.5, 0, -0.5);
		final Point3 run = new Point3(0.5, 1, 0.5);

		final AxisAlignedBox box = new AxisAlignedBox(mat3, lbf, run);

		// Triangle erzeugen

		final Material mat4 = new SingleColorMaterial(new Color(1, 0, 1));
		final Point3 at = new Point3(-0.5, 0.5, -3);
		final Point3 bt = new Point3(0.5, 0.5, -3);
		final Point3 ct = new Point3(0.5, -0.5, -3);

		final Triangle triangle = new Triangle(mat4, at, bt, ct);

		// Welt wird erzeugt

		final Color backgroundColor = new Color(0, 0, 0);

		world = new World(backgroundColor);
		// world.addGeometry(box);
		// world.addGeometry(plane);
		// world.addGeometry(sphere);
		// world.addGeometry(triangle);
		world.addGeometry(sphere1);
		world.addGeometry(sphere2);
	}

	/**
	 * Method: createCamera
	 * <p>
	 * Erzeugt die Kameras für den Test
	 */
	public void createCamera() {

		// 1. Kamera - PerspectiveCamera mit geradem Blick

		final Point3 e = new Point3(0, 0, 0);
		final Vector3 g = new Vector3(0, 0, -1);
		final Vector3 t = new Vector3(0, 1, 0);
		final double angle = Math.PI / 4;

		// camera = new OrthographicCamera(e, g, t, 3);
		// camera = new PerspectiveCamera(e, g, t, angle);

		// 2. Kamera für die AxisAlignedBox

		// final Point3 e = new Point3(3, 3, 3);
		// final Vector3 g = new Vector3(-3, -3, -3);
		// final Vector3 t = new Vector3(0, 1, 0);
		// final double angle = Math.PI / 4;
		//
		// camera = new PerspectiveCamera(e, g, t, angle);

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

		final int iwidth = (int) width;
		final int iheight = (int) height;

		wrImg = new WritableImage(iwidth, iheight);

		final long start = System.nanoTime();

		Ray ray;

		for (int y = 0; y < iheight; y++) {
			for (int x = 0; x < iwidth; x++) {
				ray = camera.rayFor(iwidth, iheight, x, iheight - 1 - y);
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
	 * Start-Methode für FX
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}