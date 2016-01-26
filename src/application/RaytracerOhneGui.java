package application;

import geometries.Cylinder;
import geometries.Geometry;
import geometries.Node;
import geometries.Plane;
import geometries.Sphere;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import light.Light;
import light.PointLight;
import material.LambertMaterial;
import material.PhongMaterial;
import ray.Transform;
import ray.World;
import textures.SingleColorTexture;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import camera.Camera;
import camera.PerspectiveCamera;
import camera.RandomRowsSamplingPattern;
import color.Color;

public class RaytracerOhneGui extends Application {

	/**
	 * wrImg - WritableImage Objekt
	 */
	private WritableImage wrImg;

	/**
	 * renderingTime - Text Objekt
	 */
	private Menu renderingTime;

	/**
	 * camera - Kamera Objekt
	 */
	private Camera camera;

	/**
	 * lights - Liste aller Lichter
	 */
	private final ArrayList<Light> lights = new ArrayList<Light>();

	/**
	 * world - Welt Objekt
	 */
	private World world;

	/**
	 * graphics - Liste mit darzustellenden Objecten
	 */
	private final ArrayList<Geometry> geometries = new ArrayList<Geometry>();

	/**
	 * View für die Erzeugung des Images
	 */
	private ImageView imgView;

	/**
	 * @param primaryStage
	 *            primaryStage der FX Applikation
	 * @throws Exception
	 *
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Waschmaschine - Raytracer");

		primaryStage.setWidth(640);
		primaryStage.setHeight(480);

		final Menu menuFile = new Menu("File");

		renderingTime = new Menu();

		final MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		menuBar.getMenus().add(menuFile);

		menuBar.getMenus().add(renderingTime);

		// Save Menu
		final MenuItem newObject = new MenuItem("New");
		final MenuItem load = new MenuItem("Load");
		final MenuItem save = new MenuItem("Save as...");
		final MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().addAll(newObject, load, save, exit);

		final HBox hBox = new HBox();
		imgView = new ImageView();
		final Group group = new Group();
		group.getChildren().addAll(imgView, hBox);
		hBox.getChildren().addAll(menuBar);

		primaryStage.setScene(new Scene(group));

		// ________________________________________________________________________________________________________________________
		// ________________________________________________________________________________________________________________________
		// ________________________________________________________________________________________________________________________

		// node 1
		final Node no = new Node(new Transform().translate(new Point3(0, -3, 0)), new ArrayList<Geometry>());
		no.geos.add(new Plane(new LambertMaterial(new SingleColorTexture(new Color(0.6, 0.6, 0.5)))));
		geometries.add(no);

		// node 2
		// final Node no2 = new Node(new Transform().translate(new Point3(0,
		// -4.3, 0)).rotateX(-0.3), new ArrayList<Geometry>());
		// no2.geos.add(new Cylinder(new ReflectiveMaterial(new Color(1, 0,
		// 0.5), new Color(1, 1, 1), new Color(0.5, 0, 0.5), 64)));
		// geometries.add(no2);

		// // node 3
		// final Node no3 = new Node(new Transform().translate(new Point3(7, -3,
		// 0)).rotateX(-0.3).scale(3, 1.5, 3), new ArrayList<Geometry>());
		// no3.geos.add(new BH(new ReflectiveMaterial(new Color(0.8, 0.9, 0.2),
		// new Color(1, 1, 1), new Color(0.5, 0, 0.5), 64)));
		//
		// geometries.add(no3);

		// node 4
		final Node no4 = new Node(new Transform().translate(new Point3(1, -1.3, 3.3)).rotateY(3.5).rotateX(7).rotateZ(0.9)
				.scale(1.5, 1.5, 0.5), new ArrayList<Geometry>());
		no4.geos.add(new Cylinder(new PhongMaterial(new SingleColorTexture(new Color(0.5, 0.5, 0.5)), new SingleColorTexture(new Color(1,
				1, 1)), 64), new ArrayList<Geometry>()));
		geometries.add(no4);

		// node 5
		// final Node no5 = new Node(new Transform().scale(1, 0.1, 1), new
		// ArrayList<Geometry>());
		// no5.geos.add(new DynamicSphere(new ReflectiveMaterial(new Color(1, 0,
		// 0.5), new Color(1, 1, 1), new Color(0.5, 0, 0.5), 64)));
		// geometries.add(no5);

		// node 6
		// final Node no6 = new Node(new Transform().rotateX(3).translate(new
		// Point3(-1.8, 0, 3)).scale(1, 15, 1), new ArrayList<Geometry>());
		// no6.geos.add(new DynamicSphere(new LambertMaterial(new
		// ImageTexture("/Users/bodowissemann/Desktop/earth.jpg"))));
		// no6.geos.add(new DynamicSphere(new LambertMaterial(new
		// SingleColorTexture(new Color(1, 0, 0)))));
		// geometries.add(no6);

		// // node 7
		// final Node no7 = new Node(new Transform().scale(-1, 0.3,
		// 1).rotateY(0.4), new ArrayList<Geometry>());
		// no7.geos.add(new TrianglePyramid(new PhongMaterial(new
		// SingleColorTexture(new Color(1, 0, 0.5)), new SingleColorTexture(new
		// Color(
		// 1, 1, 1)), 64)));
		// geometries.add(no7);

		// // // Erde
		// final Node no9 = new Node(new Transform().scale(0.5, 0.5,
		// 0.5).translate(new Point3(0, 0, 0)), new ArrayList<Geometry>());
		// no9.geos.add(new Sphere(new LambertMaterial(new
		// ImageTexture("/Users/bodowissemann/Desktop/earth.jpg"))));
		// geometries.add(no9);
		//
		// // // Sonne
		// final Node no10 = new Node(new Transform().translate(new Point3(-1,
		// 0, 0)).rotateY(19.2222), new ArrayList<Geometry>());
		// no10.geos.add(new Triangle(new LambertMaterial(new
		// InterpolatedTexture("/Users/bodowissemann/Desktop/texture_sun_prev.jpg")),
		// new Point3(0, 0, 0), new Point3(1, 1, 0), new Point3(2, 0, 0)));
		// geometries.add(no10);

		// // Scene 1
		// final Node boxNode = new Node(new
		// Transform().rotateY(-0.7).rotateX(0.3).rotateZ(0.5).scale(1, 0.3, 4),
		// new ArrayList<Geometry>());
		// boxNode.geos.add(new AxisAlignedBox(new LambertMaterial(new
		// SingleColorTexture(new Color(0.7, 0.7, 0)))));
		// geometries.add(boxNode);
		// lights.add(new PointLight(new Color(1, 1, 1), new Point3(0, 3, -4),
		// true));

		// // Scene 2
		final Node sphereNode = new Node(new Transform().translate(new Point3(4, 0, 0)).rotateX(0.05), new ArrayList<Geometry>());
		sphereNode.geos.add(new Sphere(new PhongMaterial(new SingleColorTexture(new Color(1, 0, 0)), new SingleColorTexture(new Color(1, 1,
				1)), 64)));
		geometries.add(sphereNode);
		// lights.add(new PointLight(new Color(1, 1, 1), new Point3(11, 0, -2),
		// true));

		// lights.add(new PointLight(new Color(1, 1, 1), new Point3(4, 3, 2),
		// true));

		camera = new PerspectiveCamera(new Point3(1, 5.3, -8), new Vector3(0, -0.8, 1), new Vector3(0, 1, 0), Math.PI / 6,
				new RandomRowsSamplingPattern(20));

		// lights.add(new DirectionalLight(new Color(0, 5, -5), new Vector3(0,
		// 0, -3), true));
		lights.add(new PointLight(new Color(1, 1, 1), new Point3(-11, 5, -28), true));

		// ________________________________________________________________________________________________________________________
		// ________________________________________________________________________________________________________________________
		// ________________________________________________________________________________________________________________________

		rerender(primaryStage);

		{
			// Save As...
			save.setOnAction(event -> {
				final FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Image");
				final FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
				final FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
				fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
				final File file = fileChooser.showSaveDialog(primaryStage);
				if (file != null) {
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(wrImg, null), "png", file);
					} catch (final IOException ex) {
						System.out.println(ex.getMessage());
					}
				}
			});

			// Exit
			exit.setOnAction(event -> {
				primaryStage.close();
			});
		}

		/*
		 * Über einen AddListener an der HeightProperty und der WidthProperty
		 * der primaryStage wird das neu Zeichnen des Bildes aufgerufen
		 */
		primaryStage.heightProperty().addListener((ChangeEvent) -> {
			rerender(primaryStage);
		});
		primaryStage.widthProperty().addListener((ChangeEvent) -> {
			rerender(primaryStage);
		});

		primaryStage.show();
	}
	public void createWorld() {

		final Color backgroundColor = new Color(0, 0, 0);
		world = new World(backgroundColor);

		if (!lights.isEmpty()) {
			for (final Light light : lights) {
				world.addLight(light);
			}
		}

		if (!geometries.isEmpty()) {
			for (final Geometry geo : geometries) {
				world.addGeometry(geo);
			}
		}
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

		for (int y = 0; y < iheight; y++) {
			for (int x = 0; x < iwidth; x++) {

				final Color c = world.hit(camera.rayFor(iwidth, iheight, x, iheight - 1 - y));

				final javafx.scene.paint.Color javaColor = new javafx.scene.paint.Color(c.r, c.g, c.b, 1);
				wrImg.getPixelWriter().setColor(x, y, javaColor);

			}
		}

		imgView.setImage(wrImg);

		final long end = System.nanoTime();
		renderingTime.setText((" Rendering Time: " + (end - start) / 1000000000.0F));
	}
	/**
	 * Rendert das Bild einmal komplett neu indem eine neue World erzeugt wird
	 * und anschließend das Bild neu gezeichnet wird
	 *
	 * @param primaryStage
	 *            Dient dazu das Bild zu erzeugen
	 */
	private void rerender(Stage primaryStage) {
		createWorld();
		drawImage(primaryStage.getWidth(), primaryStage.getHeight(), camera);
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