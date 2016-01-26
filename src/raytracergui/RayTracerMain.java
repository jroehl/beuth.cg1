package raytracergui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import raytracergui.controller.RayTracerMainController;

public class RayTracerMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layouts/RayTracerMainLayout.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Waschmaschine - Raytracer");
        Scene scene = new Scene(root, 1040, 500);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event -> {
            RayTracerMainController controller = loader.getController();
            if (controller.service != null) {
                controller.service.shutdownNow();
            }
        }));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
