package application;

import geometries.AxisAlignedBox;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;

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
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import material.LambertMaterial;
import ray.Ray;
import ray.World;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;
import camera.Camera;
import camera.OrthographicCamera;
import camera.PerspectiveCamera;
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
	 * graphics - Liste mit darzustellenden Objecten
	 */
	ArrayList<Geometry> graphics = new ArrayList<Geometry>();

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

		final Menu menuFile = new Menu("File");
		final Menu menuGraph = new Menu("Graphics");
		final Menu menuCamera = new Menu("Camera");
		final Menu menuSettings = new Menu("Settings");

		final MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menuFile);
		menuBar.getMenus().add(menuGraph);
		menuBar.getMenus().add(menuCamera);
		menuBar.getMenus().add(menuSettings);
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty().divide(2));

		// Save Menu
		final MenuItem save = new MenuItem("Save as...");
		final MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().add(save);
		menuFile.getItems().add(exit);

		// Graphics Menu
		final RadioMenuItem axisAlignedBox = new RadioMenuItem("AxisAlignedBox");
		final RadioMenuItem triangle = new RadioMenuItem("Triangle");
		final RadioMenuItem plane = new RadioMenuItem("Plane");
		final RadioMenuItem sphere0 = new RadioMenuItem("Sphere 0");
		final RadioMenuItem sphere1 = new RadioMenuItem("Sphere 1");
		final RadioMenuItem sphere2 = new RadioMenuItem("Sphere 2");
		menuGraph.getItems().add(axisAlignedBox);
		menuGraph.getItems().add(triangle);
		menuGraph.getItems().add(plane);
		menuGraph.getItems().add(sphere0);
		menuGraph.getItems().add(sphere1);
		menuGraph.getItems().add(sphere2);

		// Camera Menu
		final RadioMenuItem orthographicCamera = new RadioMenuItem("Orthographic Camera");
		final RadioMenuItem perspectiveCamera = new RadioMenuItem("Perspective Camera");
		menuCamera.getItems().add(orthographicCamera);
		menuCamera.getItems().add(perspectiveCamera);

		// Settings
		final MenuItem backgroundColor = new MenuItem("Background Color");
		menuSettings.getItems().add(backgroundColor);

		final HBox hBox = new HBox();

		renderingTime = new Text();
		renderingTime.setFill(javafx.scene.paint.Color.WHITE);

		final Group group = new Group();
		final ImageView imgView = new ImageView();
		group.getChildren().addAll(imgView, hBox);
		hBox.getChildren().addAll(menuBar, renderingTime);

		primaryStage.setScene(new Scene(group));

		createWorld(graphics);
		// Default Kamera
		camera = new OrthographicCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 3);
		rerender(primaryStage, imgView);

		// Menu - File
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

		// Menu - Graphics
		{
			// AlignBox
			generateGraphics(primaryStage, imgView, axisAlignedBox, new AxisAlignedBox(new LambertMaterial(new Color(0, 0, 1)), new Point3(
					-0.5, 0, -0.5), new Point3(0.5, 1, 0.5)));

			// Plane
			generateGraphics(primaryStage, imgView, plane, new Plane(new LambertMaterial(new Color(0, 1, 0)), new Point3(0, -1, 0),
					new Normal3(0, 1, 0)));

			// Spheren
			generateGraphics(primaryStage, imgView, sphere0, new Sphere(new LambertMaterial(new Color(1, 0, 0)), new Point3(0, 0, -3), 0.5));
			generateGraphics(primaryStage, imgView, sphere1,
					new Sphere(new LambertMaterial(new Color(1, 0, 0)), new Point3(-1, 0, -3), 0.5));
			generateGraphics(primaryStage, imgView, sphere2, new Sphere(new LambertMaterial(new Color(1, 0, 0)), new Point3(1, 0, -6), 0.5));

			// Triangle
			generateGraphics(primaryStage, imgView, triangle, new Triangle(new LambertMaterial(new Color(1, 0, 1)), new Point3(-0.5, 0.5,
					-3), new Point3(0.5, 0.5, -3), new Point3(0.5, -0.5, -3)));
		}

		// Menu - Camera
		{
			// Orthographic Camera
			orthographicCamera.setOnAction(event -> {
				// 1. Kamera - PerspectiveCamera mit geradem Blick
					camera = new OrthographicCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 3);

					orthographicCamera.setSelected(true);
					perspectiveCamera.setSelected(false);

					rerender(primaryStage, imgView);
				});

			// Perspective Camera
			perspectiveCamera.setOnAction(event -> {
				// 2. Kamera für die AxisAlignedBox
					camera = new PerspectiveCamera(new Point3(3, 3, 3), new Vector3(-3, -3, -3), new Vector3(0, 1, 0), Math.PI / 4);

					orthographicCamera.setSelected(false);
					perspectiveCamera.setSelected(true);

					rerender(primaryStage, imgView);
				});
		}

		// Menu - Backgrounsd
		{

		}

		/*
		 * Über einen AddListener an der HeightProperty und der WidthProperty
		 * der primaryStage wird das neu Zeichnen des Bildes aufgerufen
		 */
		primaryStage.heightProperty().addListener((ChangeEvent) -> {
			rerender(primaryStage, imgView);
		});
		primaryStage.widthProperty().addListener((ChangeEvent) -> {
			rerender(primaryStage, imgView);
		});

		primaryStage.show();
	}

	/**
	 * Hilfsmethode welche die Menueinträge für die einzelnen Objekte generiert.
	 *
	 * @param primaryStage
	 * @param imgView
	 * @param menuItem
	 * @param geometry
	 */
	private void generateGraphics(Stage primaryStage, final ImageView imgView, final RadioMenuItem menuItem, final Geometry geometry) {
		menuItem.setOnAction(event -> {
			if (graphics.contains(geometry)) {
				graphics.remove(geometry);
				menuItem.setSelected(false);
			} else {
				graphics.add(geometry);
				menuItem.setSelected(true);
			}

			createWorld(graphics);
			rerender(primaryStage, imgView);
		});
	}

	/**
	 * Method: createWorld
	 * <p>
	 * Erzeugt die Welt und die Objekte für den Test
	 */
	public void createWorld(ArrayList<Geometry> graphics) {

		final Color backgroundColor = new Color(0, 0, 0);
		world = new World(backgroundColor);

		for (final Geometry obj : graphics) {
			world.addGeometry(obj);
		}
	}

	/**
	 * Method: createCamera
	 * <p>
	 * Erzeugt die Kameras für den Test
	 */
	private void createCamera() {
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
				final javafx.scene.paint.Color javaColor = new javafx.scene.paint.Color(c.r, c.g, c.b, 1);
				wrImg.getPixelWriter().setColor(x, y, javaColor);
			}
		}
		final long end = System.nanoTime();
		renderingTime.setText((" Rendering Time: " + (end - start) / 1000000000.0F));
	}

	/**
	 * Rendert das Bild einmal komplett neu
	 */
	private void rerender(Stage primaryStage, ImageView imgView) {
		drawImage(primaryStage.getWidth(), primaryStage.getHeight(), camera);
		imgView.setImage(wrImg);
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