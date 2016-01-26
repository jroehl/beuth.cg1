package raytracergui.threads;

import camera.Camera;
import color.Color;
import javafx.concurrent.Task;
import javafx.scene.image.PixelWriter;
import ray.World;
import raytracergui.controller.RayTracerMainController;

import java.util.List;

/**
 * Created by jroehl on 13.01.16.
 */
public class RenderTaskRandom extends Task {

    private final int coordinatesAmount;
    private final int progressTotal;
    private long timeTaken;
    private int progress;
    private int moduloValue;
    private int cores;
    private int width;
    private int height;
    private String name;
    private List<int[]> coordinates;
    private Camera camera;
    private World world;
    private PixelWriter pixelWriter;


    public RenderTaskRandom(String name, List<int[]> coordinates, int cores, int moduloValue, int width, int height, Camera camera, World world, PixelWriter pixelWriter) {
        this.name = name;
        this.coordinates = coordinates;
        this.cores = cores;
        this.moduloValue = moduloValue;
        this.width = width;
        this.height = height;
        this.camera = camera;
        this.world = world;
        this.pixelWriter = pixelWriter;
        this.coordinatesAmount = coordinates.size();
        this.progress = 0;
        this.progressTotal = this.coordinatesAmount / (moduloValue + 1);
    }


    /**
     * Geht von oben nach unten jedes Pixel durch und gibt diesen Farbe
     */
    private void drawImage() {

        long totalTime = 0;

        for (int i = 0; i < coordinatesAmount; i++) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            if ((i + moduloValue) % cores == 0) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                progress++;
                final long start = System.nanoTime();
                int[] coordinate = coordinates.get(i);
                final Color c = world.hit(camera.rayFor(width, height, coordinate[0], height - 1 - coordinate[1]));
                final javafx.scene.paint.Color javaColor = new javafx.scene.paint.Color(c.r, c.g, c.b, 1);
                final long end = System.nanoTime();
                timeTaken = end - start;
                totalTime += timeTaken;
                updateProgress(progress, progressTotal);
                updateValue(totalTime);
                if (timeTaken < 15000) {
                    if (progress % 150 == 0) {
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    RayTracerMainController.PlatformHelper.run(() -> pixelWriter.setColor(coordinate[0], coordinate[1], javaColor));
                } else if (timeTaken < 30000) {
                    if (progress % 150 == 0) {
                        try {
                            Thread.sleep(15);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    RayTracerMainController.PlatformHelper.run(() -> pixelWriter.setColor(coordinate[0], coordinate[1], javaColor));
                } else {
                    if (progress % 300 == 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    RayTracerMainController.PlatformHelper.run(() -> pixelWriter.setColor(coordinate[0], coordinate[1], javaColor));

                }
            }
        }

        updateMessage(name + " took ~" + totalTime / progress + " nanoseconds for each ray");

    }

    @Override
    public Boolean call() throws Exception {
        drawImage();
        return true;
    }
}

