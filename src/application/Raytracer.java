package application;

import geometries.AxisAlignedBox;
import geometries.Geometry;
import geometries.Node;
import geometries.Plane;
import geometries.Sphere;
import geometries.TrianglePyramid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import light.DirectionalLight;
import light.Light;
import light.PointLight;
import light.SpotLight;
import material.LambertMaterial;
import material.PhongMaterial;
import material.ReflectiveMaterial;
import material.SingleColorMaterial;
import ray.Ray;
import ray.Transform;
import ray.World;
import textures.SingleColorTexture;
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
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Waschmaschine - Raytracer");

		primaryStage.setWidth(640);
		primaryStage.setHeight(480);

		final Menu menuFile = new Menu("File");
		final Menu menuGraph = new Menu("Graphics");
		final Menu menuCamera = new Menu("Camera");
		final Menu menuLight = new Menu("Light");
		final Menu menuSettings = new Menu("Settings");
		final Menu menuNode = new Menu("Node");
		renderingTime = new Menu();

		final MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		menuBar.getMenus().add(menuFile);
		menuBar.getMenus().add(menuGraph);
		menuBar.getMenus().add(menuCamera);
		menuBar.getMenus().add(menuLight);
		menuBar.getMenus().add(menuSettings);
		menuBar.getMenus().add(menuNode);
		menuBar.getMenus().add(renderingTime);

		// Save Menu
		final MenuItem newObject = new MenuItem("New");
		final MenuItem load = new MenuItem("Load");
		final MenuItem save = new MenuItem("Save as...");
		final MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().addAll(newObject, load, save, exit);

		// Graphics Menu
		final Menu axisAlignedBox = new Menu("AxisAlignedBox");
		final Menu pyramid = new Menu("Pyramid");
		final Menu triangle = new Menu("Triangle");
		final Menu plane = new Menu("Plane");
		final Menu sphere0 = new Menu("Sphere 0");
		final Menu sphere1 = new Menu("Sphere 1");
		final Menu sphere2 = new Menu("Sphere 2");

		menuGraph.getItems().addAll(axisAlignedBox, pyramid, triangle, plane, sphere0, sphere1, sphere2);

		// menuNode
		final Menu node = new Menu("Node");
		menuNode.getItems().add(node);

		// Camera Menu
		final RadioMenuItem orthographicCamera = new RadioMenuItem("Orthographic Camera");
		final RadioMenuItem perspectiveCamera = new RadioMenuItem("Perspective Camera");
		final RadioMenuItem perspectiveCamera2 = new RadioMenuItem("Perspective Camera 2");
		menuCamera.getItems().addAll(orthographicCamera, perspectiveCamera, perspectiveCamera2);

		// Light Menu
		final RadioMenuItem pointLight = new RadioMenuItem("Point Light");
		final RadioMenuItem directionalLight = new RadioMenuItem("Directional Light");
		final RadioMenuItem spotLight = new RadioMenuItem("Spot Light");
		menuLight.getItems().addAll(pointLight, directionalLight, spotLight);

		// Settings
		final MenuItem backgroundColor = new MenuItem("Background Color");
		menuSettings.getItems().add(backgroundColor);

		final HBox hBox = new HBox();
		imgView = new ImageView();
		final Group group = new Group();
		group.getChildren().addAll(imgView, hBox);
		hBox.getChildren().addAll(menuBar);

		primaryStage.setScene(new Scene(group));

		// Default Kamera
		camera = new PerspectiveCamera(new Point3(0, 0, 8), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4);
		perspectiveCamera.setSelected(true);
		// Default Licht
		lights.add(new PointLight(new Color(1, 1, 1), new Point3(8, 8, 8), true));

		// pointLight.setSelected(true);

		rerender(primaryStage);

		// Ab hier werden die einzelnen Menus Initialisiert.
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
			initializeGeometries(primaryStage, axisAlignedBox, new AxisAlignedBox(new LambertMaterial(new SingleColorTexture(new Color(0,
					0, 1)))));
			// Pyramid
			initializeGeometries(primaryStage, pyramid,
					new TrianglePyramid(new LambertMaterial(new SingleColorTexture(new Color(0, 0, 1)))));

			// Plane
			initializeGeometries(primaryStage, plane, new Plane(new LambertMaterial(new SingleColorTexture(new Color(0, 1, 0)))));

			// Spheren
			initializeGeometries(primaryStage, sphere0, new Sphere(new LambertMaterial(new SingleColorTexture(new Color(1, 0, 0)))));
			initializeGeometries(primaryStage, sphere1, new Sphere(new LambertMaterial(new SingleColorTexture(new Color(1, 0, 0)))));
			initializeGeometries(primaryStage, sphere2, new Sphere(new LambertMaterial(new SingleColorTexture(new Color(1, 0, 0)))));

			// Triangle
			// initializeGeometries(primaryStage, triangle, new Triangle(new
			// LambertMaterial(new Color(1, 0, 1)), new Point3(0, 0, 0),
			// new Point3(3, 0, 0), new Point3(1.5, 3, -1.5)));

			// Node mit Sphere darin
			final Node no = new Node(new Transform().rotateX(0.5).rotateZ(-0.6).scale(2, -0.3, 2), new ArrayList<Geometry>());
			no.geos.add(new Sphere(new PhongMaterial(new Color(1, 0, 0), new Color(1, 1, 1), 64)));
			// no.geos.add(new AxisAlignedBox(new LambertMaterial(new Color(1,
			// 0, 0))));
			initializeNode(primaryStage, node, no);
		}

		// Menu - Camera
		{
			// Orthographic Camera
			orthographicCamera.setOnAction(event -> {
				// 1. Kamera - PerspectiveCamera mit geradem Blick
					camera = new OrthographicCamera(new Point3(4, 4, 4), new Vector3(-0.9, -0.9, -0.9), new Vector3(0, 1, 0), 3);

					orthographicCamera.setSelected(true);
					perspectiveCamera.setSelected(false);
					perspectiveCamera2.setSelected(false);

					rerender(primaryStage);
				});

			// Perspective Camera
			perspectiveCamera.setOnAction(event -> {
				// 2. Kamera für die AxisAlignedBox
					camera = new PerspectiveCamera(new Point3(5, 5, 5), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4);

					orthographicCamera.setSelected(false);
					perspectiveCamera.setSelected(true);
					perspectiveCamera2.setSelected(false);

					rerender(primaryStage);
				});

			// Perspective Camera 2
			perspectiveCamera2.setOnAction(event -> {
				// 2. Kamera für die AxisAlignedBox
					camera = new PerspectiveCamera(new Point3(7, 7, 7), new Vector3(-1.1, -1.1, -1.1), new Vector3(0, 1, 0), Math.PI / 4);

					orthographicCamera.setSelected(false);
					perspectiveCamera.setSelected(false);
					perspectiveCamera2.setSelected(true);

					rerender(primaryStage);
				});
		}

		// Menu - Light
		{
			// PointLight
			initializeLights(primaryStage, pointLight, new PointLight(new Color(1, 1, 1), new Point3(8, 8, 8), true));

			// DirectionalLight
			initializeLights(primaryStage, directionalLight, new DirectionalLight(new Color(1, 1, 1), new Vector3(-8, -8, -8), true));

			// SpotLight
			initializeLights(primaryStage, spotLight, new SpotLight(new Color(1, 1, 1), new Vector3(-1, -1, -1), new Point3(3, 3, 3),
					Math.PI / 14, true));
		}

		// Menu - Settings
		{

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
	/**
	 *
	 * @param primaryStage
	 * @param button
	 * @param light
	 */
	private void initializeLights(Stage primaryStage, final RadioMenuItem button, Light light) {
		button.setOnAction(event -> {
			if (lights.contains(light)) {
				lights.remove(light);
				button.setSelected(false);
			} else {
				lights.add(light);
				button.setSelected(true);
			}
			rerender(primaryStage);
		});
	}

	private void initializeNode(Stage primaryStage, Menu menu, final Geometry geometry) {
		final RadioMenuItem addNode = new RadioMenuItem("Add Node");
		menu.getItems().add(addNode);

		addNode.setOnAction(event -> {
			if (!geometries.contains(geometry)) {
				geometries.add(geometry);
				addNode.setSelected(true);
			} else {
				geometries.remove(geometry);
				addNode.setSelected(false);
			}
			rerender(primaryStage);
		});

	}

	/**
	 * Hilfmethode welche für jeden Objekt-Menueintrag die entsprechenden
	 * Materialien bereitstellt.
	 *
	 * @param primaryStage
	 *            primaryStage für das rerendern
	 * @param menu
	 *            Menueintrag der Geomety
	 * @param geometry
	 *            Geomety für welche ein Menüeintrag erzeugt werden soll.
	 */
	private void initializeGeometries(Stage primaryStage, Menu menu, final Geometry geometry) {

		final RadioMenuItem singleColorMaterial = new RadioMenuItem("Single-Color-Material");
		final RadioMenuItem lambertMaterial = new RadioMenuItem("Lambert-Material");
		final RadioMenuItem phongMaterial = new RadioMenuItem("Phong-Material");
		final RadioMenuItem reflectiveMaterial = new RadioMenuItem("Reflective-Material");
		menu.getItems().addAll(singleColorMaterial, lambertMaterial, phongMaterial, reflectiveMaterial);

		// SingleColor - Material
		singleColorMaterial.setOnAction(event -> {
			if (!geometries.contains(geometry)) {
				final ArrayList<Object> properties = showDialog(0);

				if (!properties.isEmpty()) {
					geometry.material = new SingleColorMaterial((Color) properties.get(0));
					geometries.add(geometry);

					singleColorMaterial.setSelected(true);
					lambertMaterial.setSelected(false);
					phongMaterial.setSelected(false);
					reflectiveMaterial.setSelected(false);
				} else {
					singleColorMaterial.setSelected(false);
				}
			} else {
				geometries.remove(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			}

			rerender(primaryStage);
		});

		// Lambert Material
		lambertMaterial.setOnAction(event -> {
			if (!geometries.contains(geometry)) {
				final ArrayList<Object> properties = showDialog(0);

				if (!properties.isEmpty()) {
					geometry.material = new LambertMaterial(new SingleColorTexture((Color) properties.get(0)));
					geometries.add(geometry);

					singleColorMaterial.setSelected(false);
					lambertMaterial.setSelected(true);
					phongMaterial.setSelected(false);
					reflectiveMaterial.setSelected(false);
				} else {
					lambertMaterial.setSelected(false);
				}
			} else {
				geometries.remove(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			}

			rerender(primaryStage);
		});

		// Phong Material
		phongMaterial.setOnAction(event -> {
			if (!geometries.contains(geometry)) {
				final ArrayList<Object> properties = showDialog(1);

				if (!properties.isEmpty()) {
					geometry.material = new PhongMaterial((Color) properties.get(0), (Color) properties.get(1), (int) properties.get(3));
					geometries.add(geometry);

					singleColorMaterial.setSelected(false);
					lambertMaterial.setSelected(false);
					phongMaterial.setSelected(true);
					reflectiveMaterial.setSelected(false);
				} else {
					phongMaterial.setSelected(false);
				}
			} else {
				geometries.remove(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			}

			rerender(primaryStage);
		});

		// reflective Material
		reflectiveMaterial.setOnAction(event -> {
			if (!geometries.contains(geometry)) {
				final ArrayList<Object> properties = showDialog(2);

				if (!properties.isEmpty()) {
					geometry.material = new ReflectiveMaterial((Color) properties.get(0), (Color) properties.get(1), (Color) properties
							.get(2), (int) properties.get(3));
					geometries.add(geometry);

					singleColorMaterial.setSelected(false);
					lambertMaterial.setSelected(false);
					phongMaterial.setSelected(false);
					reflectiveMaterial.setSelected(true);
				} else {
					reflectiveMaterial.setSelected(false);
				}
			} else {
				geometries.remove(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			}

			rerender(primaryStage);
		});

	}
	/**
	 * Hilsmethode welche dem User die Möglichkeit gibt die Farben über das
	 * angezeigte Fenster selbst zu wählen.
	 *
	 * @param i
	 *            true zeigt dem User die erweiterte Ansiche, zusätzlich noch
	 *            Reflective anzeigt.
	 * @return liefert eine Liste mit Allen Farbens
	 */
	private ArrayList<Object> showDialog(int i) {

		// Create the custom dialog.
		final Dialog<ArrayList<Object>> dialog = new Dialog<ArrayList<Object>>();
		dialog.setTitle("Material Properties");

		final ArrayList<Object> result = new ArrayList<Object>();

		// Set the button types.
		final ButtonType buttonTypeCreate = new ButtonType("Create", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonTypeCreate, ButtonType.CANCEL);

		final GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		final TextField colorR = new TextField("1");
		final TextField colorG = new TextField("0.5");
		final TextField colorB = new TextField("0.2");

		grid.add(new Label("Color:"), 0, 0);
		grid.add(colorR, 1, 0);
		grid.add(colorG, 2, 0);
		grid.add(colorB, 3, 0);

		final TextField colorX = new TextField("1");
		final TextField colorY = new TextField("1");
		final TextField colorZ = new TextField("1");
		final TextField colorO = new TextField("0.5");
		final TextField colorP = new TextField("0.5");
		final TextField colorQ = new TextField("0.5");

		final TextField exponent = new TextField("64");

		if (i == 1) {
			grid.add(new Label("Exponent:"), 0, 2);

			grid.add(new Label("Specular:"), 0, 1);
			grid.add(colorX, 1, 1);
			grid.add(colorY, 2, 1);
			grid.add(colorZ, 3, 1);

			grid.add(exponent, 1, 2);
		}
		if (i == 2) {
			grid.add(new Label("Exponent:"), 0, 3);

			grid.add(new Label("Specular:"), 0, 1);
			grid.add(colorX, 1, 1);
			grid.add(colorY, 2, 1);
			grid.add(colorZ, 3, 1);

			grid.add(new Label("Reflective:"), 0, 2);

			grid.add(colorO, 1, 2);
			grid.add(colorP, 2, 2);
			grid.add(colorQ, 3, 2);

			grid.add(exponent, 1, 3);
		}

		dialog.getDialogPane().setContent(grid);

		// Request focus on the Color field by default.
		Platform.runLater(() -> colorR.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == buttonTypeCreate) {

				result.add(new Color(Double.parseDouble(colorR.getText()), Double.parseDouble(colorG.getText()), Double.parseDouble(colorB
						.getText())));

				// if (!colorX.getText().isEmpty() &&
				// !colorY.getText().isEmpty() && !colorY.getText().isEmpty()) {
				result.add(new Color(Double.parseDouble(colorX.getText()), Double.parseDouble(colorY.getText()), Double.parseDouble(colorZ
						.getText())));
				// }

				// if (!colorO.getText().isEmpty() &&
				// !colorP.getText().isEmpty() && !colorQ.getText().isEmpty()) {
				result.add(new Color(Double.parseDouble(colorO.getText()), Double.parseDouble(colorP.getText()), Double.parseDouble(colorQ
						.getText())));
				// }

				// if (!exponent.getText().isEmpty()) {
				result.add(Integer.parseInt(exponent.getText()));
				// }

				return result;
			}
			return null;
		});
		final Optional<ArrayList<Object>> result1 = dialog.showAndWait();
		return result;
	}

	/**
	 * Method: createWorld
	 * <p>
	 * Erzeugt die Welt und die Objekte für den Test
	 */
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