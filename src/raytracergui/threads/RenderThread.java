package raytracergui.threads;

import camera.Camera;
import color.Color;
import javafx.scene.image.WritableImage;
import ray.Ray;
import ray.World;

import java.util.Set;
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

                    final Set<Ray> raySet = camera.rayFor(width, height, x, height - 1 - y);
                    Color retCol = new Color(0, 0, 0);

                    for (final Ray r : raySet) {
                        retCol = retCol.add(world.hit(r));
                    }

                    final Color retCol2 = retCol.mul(1.0 / raySet.size());
                    if (retCol2.r > 1) {
                        retCol2.r = 1;
                    } else if (retCol2.r < 0) {
                        retCol2.r = 0;
                    }

                    if (retCol2.g > 1) {
                        retCol2.g = 1;
                    } else if (retCol2.g < 0) {
                        retCol2.g = 0;
                    }

                    if (retCol2.b > 1) {
                        retCol2.b = 1;
                    } else if (retCol2.b < 0) {
                        retCol2.b = 0;
                    }

                    final javafx.scene.paint.Color javaColor = new javafx.scene.paint.Color(retCol2.r, retCol2.g, retCol2.b, 1);
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

