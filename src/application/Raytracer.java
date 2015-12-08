package application;

import geometries.AxisAlignedBox;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
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
import javafx.scene.Node;
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
	 * Light - Light Objekt
	 */
	private Light light;

	/**
	 * world - Welt Objekt
	 */
	private World world;

	/**
	 * graphics - Liste mit darzustellenden Objecten
	 */
	private final ArrayList<Geometry> graphics = new ArrayList<Geometry>();

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
		renderingTime = new Menu();

		final MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menuFile);
		menuBar.getMenus().add(menuGraph);
		menuBar.getMenus().add(menuCamera);
		menuBar.getMenus().add(menuLight);
		menuBar.getMenus().add(menuSettings);
		menuBar.getMenus().add(renderingTime);
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

		// Save Menu
		final MenuItem save = new MenuItem("Save as...");
		final MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().add(save);
		menuFile.getItems().add(exit);

		// Graphics Menu
		final Menu axisAlignedBox = new Menu("AxisAlignedBox");
		final Menu pyramid = new Menu("Pyramid");
		final Menu triangle = new Menu("Triangle");
		final Menu plane = new Menu("Plane");
		final Menu sphere0 = new Menu("Sphere 0");
		final Menu sphere1 = new Menu("Sphere 1");
		final Menu sphere2 = new Menu("Sphere 2");

		menuGraph.getItems().add(axisAlignedBox);
		menuGraph.getItems().add(pyramid);
		menuGraph.getItems().add(triangle);
		menuGraph.getItems().add(plane);
		menuGraph.getItems().add(sphere0);
		menuGraph.getItems().add(sphere1);
		menuGraph.getItems().add(sphere2);

		// Camera Menu
		final RadioMenuItem orthographicCamera = new RadioMenuItem("Orthographic Camera");
		final RadioMenuItem perspectiveCamera = new RadioMenuItem("Perspective Camera");
		final RadioMenuItem perspectiveCamera2 = new RadioMenuItem("Perspective Camera 2");
		menuCamera.getItems().add(orthographicCamera);
		menuCamera.getItems().add(perspectiveCamera);
		menuCamera.getItems().add(perspectiveCamera2);

		// Light Menu
		final RadioMenuItem pointLight = new RadioMenuItem("Point Light");
		final RadioMenuItem directionalLight = new RadioMenuItem("Directional Light");
		final RadioMenuItem spotLight = new RadioMenuItem("Spot Light");
		menuLight.getItems().add(pointLight);
		menuLight.getItems().add(directionalLight);
		menuLight.getItems().add(spotLight);

		// Settings
		final MenuItem backgroundColor = new MenuItem("Background Color");
		menuSettings.getItems().add(backgroundColor);

		final HBox hBox = new HBox();

		final Group group = new Group();
		final ImageView imgView = new ImageView();
		group.getChildren().addAll(imgView, hBox);
		hBox.getChildren().addAll(menuBar);

		primaryStage.setScene(new Scene(group));

		// Default Kamera
		camera = new OrthographicCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 3);
		orthographicCamera.setSelected(true);
		// Default Licht
		light = new PointLight(new Color(1, 1, 1), new Point3(8, 8, 8), true);
		pointLight.setSelected(true);

		createWorld();
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
			initializeButton(primaryStage, imgView, axisAlignedBox, new AxisAlignedBox(new LambertMaterial(new Color(0, 0, 1)), new Point3(
					-0.5, 0, -0.5), new Point3(0.5, 1, 0.5)));
			// Pyramid
			initializeButton(primaryStage, imgView, pyramid, new TrianglePyramid(new LambertMaterial(new Color(0, 0, 1)), new Point3(0, 0,
					0)));

			// Plane
			initializeButton(primaryStage, imgView, plane, new Plane(new LambertMaterial(new Color(0, 1, 0)), new Point3(0, 0, 0),
					new Normal3(0, 1, 0)));

			// Spheren
			initializeButton(primaryStage, imgView, sphere0, new Sphere(new LambertMaterial(new Color(1, 0, 0)), new Point3(-3, 1, 0), 1));
			initializeButton(primaryStage, imgView, sphere1, new Sphere(new LambertMaterial(new Color(1, 0, 0)), new Point3(0, 1, 0), 1));
			initializeButton(primaryStage, imgView, sphere2, new Sphere(new LambertMaterial(new Color(1, 0, 0)), new Point3(3, 1, 0), 1));

			// Triangle
			initializeButton(primaryStage, imgView, triangle, new Triangle(new LambertMaterial(new Color(1, 0, 1)), new Point3(-0.5, 0.5,
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
					perspectiveCamera2.setSelected(false);

					rerender(primaryStage, imgView);
				});

			// Perspective Camera
			perspectiveCamera.setOnAction(event -> {
				// 2. Kamera für die AxisAlignedBox
					camera = new PerspectiveCamera(new Point3(8, 8, 8), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4);

					orthographicCamera.setSelected(false);
					perspectiveCamera.setSelected(true);
					perspectiveCamera2.setSelected(false);

					rerender(primaryStage, imgView);
				});

			// Perspective Camera 2
			perspectiveCamera2.setOnAction(event -> {
				// 2. Kamera für die AxisAlignedBox
					camera = new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, 1), new Vector3(0, 1, 0), Math.PI / 4);

					orthographicCamera.setSelected(false);
					perspectiveCamera.setSelected(false);
					perspectiveCamera2.setSelected(true);

					rerender(primaryStage, imgView);
				});
		}

		// Menu - Light
		{
			// PointLight
			pointLight.setOnAction(event -> {
				light = new PointLight(new Color(1, 1, 1), new Point3(8, 8, 0), true);

				if (world.lights.contains(light)) {
					world.lights.remove(light);
					pointLight.setSelected(false);
				} else {
					world.lights.add(light);
					pointLight.setSelected(true);
				}

				rerender(primaryStage, imgView);
			});

			// DirectionalLight
			directionalLight.setOnAction(event -> {

				light = new DirectionalLight(new Color(1, 1, 1), new Vector3(8, 8, 0), true);

				if (world.lights.contains(light)) {
					world.lights.remove(light);
					directionalLight.setSelected(false);
				} else {
					world.lights.add(light);
					directionalLight.setSelected(true);
				}

				rerender(primaryStage, imgView);
			});

			// SpotLight
			spotLight.setOnAction(event -> {

				light = new SpotLight(new Color(1, 1, 1), new Vector3(-1, -1, -1), new Point3(-3, -3, -3), Math.PI / 14, true);

				if (world.lights.contains(light)) {
					world.lights.remove(light);
					spotLight.setSelected(false);
				} else {
					world.lights.add(light);
					spotLight.setSelected(true);
				}

				rerender(primaryStage, imgView);
			});
		}

		// Menu - Material
		{
		}

		// Menu - Settings
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
	private void initializeButton(Stage primaryStage, final ImageView imgView, Menu menu, final Geometry geometry) {

		final RadioMenuItem singleColorMaterial = new RadioMenuItem("Single-Color-Material");
		final RadioMenuItem lambertMaterial = new RadioMenuItem("Lambert-Material");
		final RadioMenuItem phongMaterial = new RadioMenuItem("Phong-Material");
		final RadioMenuItem reflectiveMaterial = new RadioMenuItem("Reflective-Material");
		menu.getItems().addAll(singleColorMaterial, lambertMaterial, phongMaterial, reflectiveMaterial);

		// SingleColor - Material
		singleColorMaterial.setOnAction(event -> {
			if (!graphics.contains(geometry)) {
				final ArrayList<Object> properties = showDialog(0);
				geometry.material = new SingleColorMaterial((Color) properties.get(0));

				graphics.add(geometry);

				singleColorMaterial.setSelected(true);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			} else {
				graphics.remove(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			}

			createWorld();
			rerender(primaryStage, imgView);
		});

		// Lambert Material
		lambertMaterial.setOnAction(event -> {
			if (!graphics.contains(geometry)) {
				final ArrayList<Object> properties = showDialog(0);
				geometry.material = new LambertMaterial((Color) properties.get(0));

				graphics.add(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(true);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			} else {
				graphics.remove(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			}

			createWorld();
			rerender(primaryStage, imgView);
		});

		// Phong Material
		phongMaterial.setOnAction(event -> {
			if (!graphics.contains(geometry)) {
				final ArrayList<Object> properties = showDialog(1);
				geometry.material = new PhongMaterial((Color) properties.get(0), (Color) properties.get(1), (int) properties.get(3));

				graphics.add(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(true);
				reflectiveMaterial.setSelected(false);
			} else {
				graphics.remove(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			}

			createWorld();
			rerender(primaryStage, imgView);
		});

		// reflective Material
		reflectiveMaterial.setOnAction(event -> {
			if (!graphics.contains(geometry)) {
				final ArrayList<Object> properties = showDialog(2);
				geometry.material = new ReflectiveMaterial((Color) properties.get(0), (Color) properties.get(1), (Color) properties.get(2),
						(int) properties.get(3));

				graphics.add(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(true);
			} else {
				graphics.remove(geometry);

				singleColorMaterial.setSelected(false);
				lambertMaterial.setSelected(false);
				phongMaterial.setSelected(false);
				reflectiveMaterial.setSelected(false);
			}

			createWorld();
			rerender(primaryStage, imgView);
		});
	}

	public String random() {
		return String.valueOf(Math.random());
	}

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

		final TextField colorR = new TextField();
		final TextField colorG = new TextField();
		final TextField colorB = new TextField();

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

		final Node loginButton = dialog.getDialogPane().lookupButton(buttonTypeCreate);
		// loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		// colorR.textProperty().addListener((observable, oldValue, newValue) ->
		// {
		// loginButton.setDisable(newValue.trim().isEmpty());
		// });
		// colorG.textProperty().addListener((observable, oldValue, newValue) ->
		// {
		// loginButton.setDisable(newValue.trim().isEmpty());
		// });
		// colorB.textProperty().addListener((observable, oldValue, newValue) ->
		// {
		// loginButton.setDisable(newValue.trim().isEmpty());
		// });

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
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
		world.addLight(light);

		for (final Geometry obj : graphics) {
			world.addGeometry(obj);
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