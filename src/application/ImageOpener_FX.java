package application;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Öffnet ein Bild und zeigt dieses an.
 *
 */
public class ImageOpener_FX extends Application {

	@Override
	public void start(Stage openDialog) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

		File selectedFile = fileChooser.showOpenDialog(openDialog);
		String path = selectedFile.getAbsolutePath();
		Image image = new Image("file:" + path);

		if (selectedFile != null) {
			ImageView iv1 = new ImageView();
			iv1.setImage(image);
			HBox box = new HBox();
			box.getChildren().add(iv1);
			Stage viewer = new Stage();
			viewer.setTitle(path);
			viewer.setScene(new Scene(box));
			viewer.show();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
