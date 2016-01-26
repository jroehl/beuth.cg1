package raytracergui;

import camera.Camera;
import color.Color;
import javafx.scene.image.WritableImage;
import ray.World;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by jroehl on 13.01.16.
 */
public class RenderThread implements Callable {

    private Thread t;
    private long timeTaken;
    private int cores;
    private int moduloValue;
    private int width;
    private int height;
    private WritableImage wrImg;
    private Camera camera;
    private World world;

    public RenderThread(int cores, int moduloValue, int width, int height, WritableImage wrImg, Camera camera, World world) {
        this.cores = cores;
        this.moduloValue = moduloValue;
        this.width = width;
        this.height = height;
        this.wrImg = wrImg;
        this.camera = camera;
        this.world = world;
    }

    /**
     * Geht von oben nach unten jedes Pixel durch und gibt diesen Farbe
     */
    private void drawImage() {

        final long start = System.nanoTime();

        for (int y = 0; y < height; y++) {
            if ((y + moduloValue) % cores == 0) {
                for (int x = 0; x < width; x++) {
                    final Color c = world.hit(camera.rayFor(width, height, x, height - 1 - y));
                    final javafx.scene.paint.Color javaColor = new javafx.scene.paint.Color(c.r, c.g, c.b, 1);
                    wrImg.getPixelWriter().setColor(x, y, javaColor);
                }
            }
        }

        final long end = System.nanoTime();

        timeTaken = end - start;
    }

    @Override
    public Long call() throws Exception {
        drawImage();
        try {
            TimeUnit.SECONDS.sleep(2);
            return timeTaken;
        } catch (InterruptedException e) {
            throw new IllegalStateException("task interrupted", e);
        }

    }
}

