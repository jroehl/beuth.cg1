package application;

import geometries.DynamicSphere;
import geometries.Geometry;
import geometries.Node;
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

import light.DirectionalLight;
import light.Light;
import light.PointLight;
import material.ReflectiveMaterial;
import ray.Ray;
import ray.Transform;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import camera.Camera;
import camera.FishEye;
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
		// final Node no = new Node(new Transform().rotateX(0.7).translate(new
		// Point3(0, -5, 0)), new ArrayList<Geometry>());
		// no.geos.add(new Plane(new ReflectiveMaterial(new Color(0, 0.5, 0.5),
		// new Color(1, 1, 1), new Color(0.5, 0, 0.5), 64)));
		// geometries.add(no);

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
		// final Node no4 = new Node(new Transform().translate(new Point3(11,
		// -1.3, 3.3)).rotateY(0.6), new ArrayList<Geometry>());
		// no4.geos.add(new Cylinder(new ReflectiveMaterial(new Color(1, 0.5,
		// 0), new Color(1, 1, 1), new Color(0.5, 0, 0.5), 64)));
		// geometries.add(no4);

		// node 5
		final Node no5 = new Node(new Transform().scale(1, 0.1, 1), new ArrayList<Geometry>());
		no5.geos.add(new DynamicSphere(new ReflectiveMaterial(new Color(1, 0, 0.5), new Color(1, 1, 1), new Color(0.5, 0, 0.5), 64)));
		geometries.add(no5);

		// node 6
		final Node no6 = new Node(new Transform().scale(2.2, 7.2, 2.2).translate(new Point3(3, -2.3, 1.8)).rotateX(0.7),
				new ArrayList<Geometry>());
		no6.geos.add(new Sphere(new ReflectiveMaterial(new Color(0.1, 1, 0.5), new Color(1, 1, 1), new Color(0.5, 0, 0.5), 64)));
		geometries.add(no6);

		// // node 7
		// final Node no7 = new Node(new Transform().scale(2.2, 2.2,
		// 2.2).translate(new Point3(-0.5, 2.3, 0.8)).rotateX(0.3),
		// new ArrayList<Geometry>());
		// no7.geos.add(new DynamicSphere(new ReflectiveMaterial(new Color(1, 0,
		// 0.5), new Color(1, 1, 1), new Color(0.5, 0, 0.5), 64)));
		// geometries.add(no7);

		// node 8
		// final Node no8 = new Node(new Transform().scale(2, 2,
		// 2).translate(new Point3(3, 0.2, 3)).rotateX(0.3), new
		// ArrayList<Geometry>());
		// no8.geos.add(new AxisAlignedBox(new ReflectiveMaterial(new Color(0,
		// 0.5, 1), new Color(1, 1, 1), new Color(0.7, 0.2, 0.3), 64)));
		// geometries.add(no8);

		camera = new FishEye(new Point3(7, 10, -8), new Vector3(0, -1.8, 1), new Vector3(0, 1, 0), Math.PI / 2);
		lights.add(new PointLight(new Color(1, 1, 1), new Point3(0, -8, -8), true));
		lights.add(new DirectionalLight(new Color(1, 1, 1), new Vector3(-0.1, -2, -2), true));
		// lights.add(new PointLight(new Color(1, 1, 1), new Point3(0.9, 17, 2),
		// true));

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
				final Ray ray = camera.rayFor(iwidth, iheight, x, iheight - 1 - y);
				final Color c = world.hit(ray);
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