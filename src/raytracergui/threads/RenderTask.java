package raytracergui.threads;

import camera.Camera;
import javafx.concurrent.Task;
import javafx.scene.image.PixelWriter;
import ray.World;
import raytracergui.controller.RayTracerMainController;

import java.util.ArrayList;

/**
 * Created by jroehl on 13.01.16.
 */
public class RenderTask extends Task {

    private int progress;
    private int progressTotal;
    private int cores;
    private int moduloValue;
    private int width;
    private int height;
    private Camera camera;
    private World world;
    private ArrayList<Object[]> values;
    private PixelWriter pixelWriter;


    public RenderTask(int cores, int moduloValue, int width, int height, Camera camera, World world, PixelWriter pixelWriter) {
        this.cores = cores;
        this.moduloValue = moduloValue;
        this.width = width;
        this.height = height;
        this.camera = camera;
        this.world = world;
        this.pixelWriter = pixelWriter;
        if (moduloValue == 0) {
            moduloValue ++;
        }
        this.progressTotal = (height / moduloValue) * width;
        this.progress = 0;
    }

    public RenderTask(int cores, int moduloValue, int width, int height, Camera camera, World world, PixelWriter pixelWriter, RayTracerMainController rayTracerMainController) {
        this.cores = cores;
        this.moduloValue = moduloValue;
        this.width = width;
        this.height = height;
        this.camera = camera;
        this.world = world;
        this.pixelWriter = pixelWriter;
        RayTracerMainController rayTracerMainController1 = rayTracerMainController;
        if (moduloValue == 0) {
            moduloValue ++;
        }
        this.progressTotal = (height / moduloValue) * width;
        this.progress = 0;
    }

    /**
     * Geht von oben nach unten jedes Pixel durch und gibt diesen Farbe
     */
    private void drawImage() {

        long prevTime = 0;

        for (int y = 0; y < height; y++) {
            if ((y + moduloValue) % cores == 0) {
                for (int x = 0; x < width; x++) {
                    progress ++;
                    final long start = System.nanoTime();
//                    final Color c = world.hit(camera.rayFor(width, height, x, height - 1 - y));
                    final long end = System.nanoTime();
                    long timeTaken = end - start;
                    prevTime += timeTaken;

//                    final javafx.scene.paint.Color javaColor = new javafx.scene.paint.Color(c.r, c.g, c.b, 1);
                    final int finX = x, finY = y;
//                    updateMessage("Iteration " + progress);
//                    updateProgress(progress, progressTotal);
//                    pixelWriter.setColor(finX, finY, javaColor);
//                    if (timeTaken < 2000 && progress % 100 == 0) {
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                        RayTracerMainController.PlatformHelper.run(() -> pixelWriter.setColor(finX, finY, javaColor));

//                    if (progress % 100 == 0) {
//                        RayTracerMainController.PlatformHelper.run(() -> rayTracerMainController.imgView.setImage(rayTracerMainController.wrImg));
//                    }

                }
            }
        }

        System.out.println(prevTime / progress);

    }

    @Override
    public Boolean call() throws Exception {
        drawImage();
        return true;
    }
}

