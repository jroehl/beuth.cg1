package raytracergui.threads;

import camera.Camera;
import color.Color;
import javafx.concurrent.Task;
import javafx.scene.image.PixelWriter;
import ray.Ray;
import ray.World;
import raytracergui.controller.RayTracerMainController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jroehl on 13.01.16.
 */
public class RenderTaskRandom extends Task {

    private final int coordinatesAmount;
    private final int progressTotal;
    private int progress;
    private final int moduloValue;
    private final int cores;
    private final int width;
    private final int height;
    private final String name;
    private final List<int[]> coordinates;
    private final Camera camera;
    private final World world;
    private final PixelWriter pixelWriter;
    private final ArrayList<Object[]> batch;

    public RenderTaskRandom(String name, List<int[]> coordinates, int cores, int moduloValue, int width, int height, Camera camera,
                            World world, PixelWriter pixelWriter) {
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
        this.batch = new ArrayList<>();

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
                final int[] coordinate = coordinates.get(i);
                final Set<Ray> raySet = camera.rayFor(width, height, coordinate[0], height - 1 - coordinate[1]);
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
                final long end = System.nanoTime();

                final Object[] obj = new Object[2];
                obj[0] = coordinate;
                obj[1] = javaColor;

                batch.add(obj);

                long timeTaken = end - start;
                totalTime += timeTaken;
                updateProgress(progress, progressTotal);
                updateValue(totalTime);

                if (progress % 200 == 0) {
                    final ArrayList<Object[]> clone = cloneList(batch);
                    batch.clear();
                    RayTracerMainController.PlatformHelper.run(() -> paintBatch(clone));

                }
            }
        }

        updateMessage(name + " took ~" + totalTime / progress + " nanoseconds for each ray");
    }

    private static ArrayList<Object[]> cloneList(List<Object[]> list) {
        final ArrayList<Object[]> clone = new ArrayList<>(list.size());
        for (final Object[] o : list) {
            clone.add(o);
        }
        // clone.addAll(list.stream().map(Object[]::clone).collect(Collectors.toList()));
        return clone;
    }

    private void paintBatch(ArrayList<Object[]> objects) {
        for (final Object[] obj : objects) {
            final int[] coordinate = (int[]) obj[0];
            pixelWriter.setColor(coordinate[0], coordinate[1], (javafx.scene.paint.Color) obj[1]);
        }
    }

    @Override
    public Boolean call() throws Exception {
        drawImage();
        return true;
    }
}
