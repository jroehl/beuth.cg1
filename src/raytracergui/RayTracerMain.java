package raytracergui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventDispatcher;
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
        final EventDispatcher eventDispatcher = scene.getEventDispatcher();  // the original dispatcher
        scene.setEventDispatcher((event, tail) -> {
            long millis = System.currentTimeMillis();
            Event returnedEvent = eventDispatcher.dispatchEvent(event, tail);  // let original one handle it as usual
            millis = System.currentTimeMillis() - millis;
            if(millis >= 100) {  // check if it was slow
                System.out.println("[WARN] Slow Event Handling: " + millis + " ms for event: " + event);
            }
            return returnedEvent;
        });
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
