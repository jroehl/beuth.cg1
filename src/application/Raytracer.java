package application;

import camera.Camera;
import camera.PerspectiveCamera;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Raytracer extends Application {

	WritableImage wrImg;
	Text renderingTime;
	Stage primaryStage;
	ImageView imgView;

	Camera camera;

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("PNG Creator");
		primaryStage.setHeight(640);
		primaryStage.setWidth(480);

		Menu fileMenu = new Menu("File");

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(fileMenu);
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty().divide(2));
		MenuItem save = new MenuItem("Save");
		fileMenu.getItems().add(save);

		HBox hBox = new HBox();

		renderingTime = new Text();
		renderingTime.setFill(Color.WHITE);

		Group group = new Group();
		ImageView imgView = new ImageView();
		group.getChildren().addAll(imgView, hBox);
		hBox.getChildren().addAll(menuBar, renderingTime);

		primaryStage.setScene(new Scene(group));

		drawImage(primaryStage.getWidth(), primaryStage.getHeight());
		imgView.setImage(wrImg);

		save.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Image");
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
			File file = fileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(wrImg, null), "png", file);
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		/**
		 * Über einen AddListener an der HeightProperty und der WidthProperty der primaryStage wird das neu Zeichnen des
		 * Bildes aufgerufen
		 */

		primaryStage.heightProperty().addListener((ChangeEvent) -> {
			drawImage(primaryStage.getWidth(), primaryStage.getHeight());
			imgView.setImage(wrImg);
		});

		primaryStage.widthProperty().addListener((ChangeEvent) -> {
			drawImage(primaryStage.getWidth(), primaryStage.getHeight());
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

	private void drawImage(double width, double height) {
		camera = new PerspectiveCamera();

		wrImg = new WritableImage((int) width, (int) height);
		long start = System.nanoTime();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				wrImg.getPixelWriter().setColor(x, y, getColor(x, y));
			}
		}
		long end = System.nanoTime();
		renderingTime.setText((" Rendering Time: " + (end - start) / 1000000000.0F));
	}

	/**
	 * Liefert den Farbwert zurück um eine Diagonale Linie in einem schwarzen Bild zu malen.
	 * 
	 * @param x
	 *            , X Coordinate eines Pixels im Bild
	 * @param y
	 *            Y Coordinate eines Pixels im Bild
	 * @return Farbe wie das Pixel eingefärbt werden soll
	 */

	private Color getColor(int x, int y) {
		if (x == y | (x - 1) == y | (x + 1) == y) {
			return Color.RED;
		}
		return Color.BLACK;
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