package raytracergui.controller;

import camera.Camera;
import color.Color;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PlusMinusSlider;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.Glyph;
import ray.World;
import raytracergui.container.LightContainer;
import raytracergui.container.NodeContainer;
import raytracergui.dataclasses.TreeViewWithItems;
import raytracergui.enums.SamplingPattern;
import raytracergui.helpers.Helper;
import raytracergui.threads.RenderTaskRandom;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

//import javafx.scene.image.ImageView;

public class RayTracerMainController {
    @FXML
    private AnchorPane apMain;
    @FXML
    private ChoiceBox<raytracergui.enums.Camera> cameraChoice;
    @FXML
    private ChoiceBox<NodeContainer> nodeChoice;
    @FXML
    private HBox mainViewer;
    @FXML
    private PlusMinusSlider eX;
    @FXML
    private TextField labelEx;
    @FXML
    private PlusMinusSlider eY;
    @FXML
    private TextField labelEy;
    @FXML
    private PlusMinusSlider eZ;
    @FXML
    private TextField labelEz;
    @FXML
    private PlusMinusSlider gX;
    @FXML
    private TextField labelGx;
    @FXML
    private PlusMinusSlider gY;
    @FXML
    private TextField labelGy;
    @FXML
    private PlusMinusSlider gZ;
    @FXML
    private TextField labelGz;
    @FXML
    private PlusMinusSlider uX;
    @FXML
    private TextField labelUx;
    @FXML
    private PlusMinusSlider uY;
    @FXML
    private TextField labelUy;
    @FXML
    private PlusMinusSlider uZ;
    @FXML
    private TextField labelUz;
    @FXML
    private PlusMinusSlider extra;
    @FXML
    private Label labelTextExtra;
    @FXML
    private TextField labelValExtra;
    @FXML
    private Label labelPersp;
    @FXML
    private Button openLightBtn;
    @FXML
    private StatusBar statusBar;
    @FXML
    private PlusMinusSlider transX;
    @FXML
    private TextField labelTransX;
    @FXML
    private PlusMinusSlider transY;
    @FXML
    private TextField labelTransY;
    @FXML
    private PlusMinusSlider transZ;
    @FXML
    private TextField labelTransZ;
    @FXML
    private PlusMinusSlider scaleX;
    @FXML
    private TextField labelScaleX;
    @FXML
    private PlusMinusSlider scaleY;
    @FXML
    private TextField labelScaleY;
    @FXML
    private PlusMinusSlider scaleZ;
    @FXML
    private TextField labelScaleZ;
    @FXML
    private PlusMinusSlider translateX;
    @FXML
    private TextField labelTranslateX;
    @FXML
    private PlusMinusSlider translateY;
    @FXML
    private TextField labelTranslateY;
    @FXML
    private PlusMinusSlider translateZ;
    @FXML
    private TextField labelTranslateZ;
    @FXML
    private ColorPicker backgroundColorPicker;
    @FXML
    private ColorPicker ambientColorPicker;
    @FXML
    private Accordion nodeAccordion;
    @FXML
    private TitledPane nodeSettingsPane;
    @FXML
    private VBox nodeListView;
    @FXML
    private Button editBtn;
    @FXML
    private Spinner<Integer> numSamples;
    @FXML
    private Spinner<Double> refractionIndexSpinner;
    @FXML
    private ChoiceBox<SamplingPattern> samplingChoice;

    /**
     * View für die Erzeugung des Images
     */
    public ImageView imgView;

    /**
     * world - Welt Objekt
     */
    private World world;

    /**
     * wrImg - WritableImage Objekt
     */
    public WritableImage wrImg;

    /**
     * camera - Kamera Objekt
     */
    private Camera camera;

    private Scene scene;

    private Stage stage;

    private TextField[] cameraLabels;
    private TextField[] nodeLabels;

    private CheckBox checkAutorender;

    private final ObservableList<raytracergui.enums.Camera> cameraNames = FXCollections.observableArrayList(raytracergui.enums.Camera
            .values());
    private final ObservableList<SamplingPattern> samplingPatterns = FXCollections.observableArrayList(SamplingPattern.values());
    private final ObservableMap<String, raytracergui.enums.Camera> cameraMap = FXCollections.observableHashMap();

    private int nodeIndex = 0;
    private int selectedNodeIndex;

    public ObservableMap<String, LightContainer> lightMap = FXCollections.observableHashMap();
    public NodeContainer selectedNode;

    private final ObservableList<NodeContainer> rootNodes = FXCollections.observableArrayList();
    private final ObservableList<NodeContainer> allNodes = FXCollections.observableArrayList();
    private ObservableList<NodeContainer> selectedNodes = FXCollections.observableArrayList();

    private raytracergui.enums.Camera selectedCamera;

    private boolean lightWindowOpen;

    private TreeViewWithItems<NodeContainer> treeView;

    private final int cores = Runtime.getRuntime().availableProcessors() / 2;

    public ExecutorService service;
    private final SimpleStringProperty messageProperty = new SimpleStringProperty("OK");
    private int previousHeight;
    private int previousWidth;

    private ArrayList<int[]> coordinates;
    private PixelWriter pixelWriter;
    private double refractionIndex;
    private Color backgroundColor = new Color(0, 0, 0);
    private Color ambientColor = new Color(0.25, 0.25, 0.25);

    @FXML
    public void initialize() {

        nodeAccordion.setExpandedPane(nodeSettingsPane);

        cameraChoice.setItems(cameraNames);
        for (final Enum c : cameraNames) {
            final raytracergui.enums.Camera camera = (raytracergui.enums.Camera) c;
            cameraMap.put(camera.getName(), camera);
        }
        cameraChoice.getSelectionModel().selectLast();
        selectedCamera = cameraMap.get(cameraChoice.getSelectionModel().getSelectedItem().toString());

        samplingChoice.setItems(samplingPatterns);
        samplingChoice.getSelectionModel().select(SamplingPattern.ONERAY);

        treeView = new TreeViewWithItems<>(new TreeItem());
        treeView.setShowRoot(false);

        final HBox hBox = new HBox();

        final Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPadding(new Insets(0, 0, 0, 5));

        Button refreshBtn = new Button("", new Glyph("FontAwesome", "REFRESH"));
        refreshBtn.setOnAction((event -> btnRerender()));

        Button stopBtn = new Button("", new Glyph("FontAwesome", "STOP"));
        stopBtn.setOnAction((event -> stopRendering()));

        checkAutorender = new CheckBox("autorender (beta)");
        checkAutorender.setSelected(false);
        checkAutorender.setPadding(new Insets(0, 5, 0, 0));

        hBox.setMargin(checkAutorender, new Insets(4, 0, 0, 0));

        hBox.getChildren().addAll(separator, checkAutorender, refreshBtn, stopBtn);
        statusBar.getRightItems().add(hBox);

        createNode();

        nodeListView.setMargin(treeView, new Insets(10, 0, 0, 0));
        nodeListView.getChildren().add(treeView);

        treeView.setItems(rootNodes);

        setCameraValues();
        initializeListeners();
        initializeViewer();

    }

    private void initializeListeners() {

        PlusMinusSlider[] cameraSliders = new PlusMinusSlider[]{eX, eY, eZ, gX, gY, gZ, uX, uY, uZ, extra};
        cameraLabels = new TextField[]{labelEx, labelEy, labelEz, labelGx, labelGy, labelGz, labelUx, labelUy, labelUz, labelValExtra};
        PlusMinusSlider[] nodeSliders = new PlusMinusSlider[]{transX, transY, transZ, scaleX, scaleY, scaleZ, translateX, translateY, translateZ};
        nodeLabels = new TextField[]{labelTransX, labelTransY, labelTransZ, labelScaleX, labelScaleY, labelScaleZ, labelTranslateX,
                labelTranslateY, labelTranslateZ};

        ambientColorPicker.valueProperty().set(new javafx.scene.paint.Color(0.25, 0.25, 0.25, 1));
        ambientColorPicker.valueProperty().addListener((ChangeListener) -> {
            ambientColor = generateColor(ambientColorPicker.getValue());
            rerender(false);
        });

        backgroundColorPicker.valueProperty().set(javafx.scene.paint.Color.BLACK);
        backgroundColorPicker.valueProperty().addListener((ChangeListener) -> {
            backgroundColor = generateColor(backgroundColorPicker.getValue());
            rerender(false);
        });

        apMain.sceneProperty().addListener((ChangeListener) -> {
            scene = apMain.getScene();
            if (scene != null) {
                setupResizeListener();
            }
        });

        bindCameraLabels();

        for (int i = 0; i < cameraSliders.length; i++) {
            final TextField l = cameraLabels[i];
            final PlusMinusSlider p = cameraSliders[i];
            p.setOnValueChanged(e -> changeCameraValues(e.getValue(), l));
        }

        bindNodeLabels();

        for (int i = 0; i < nodeSliders.length; i++) {
            final TextField l = nodeLabels[i];
            final PlusMinusSlider p = nodeSliders[i];
            p.setOnValueChanged(e -> changeNodeValues(e.getValue(), l));
        }

        cameraChoice.setOnAction((event) ->

        {
            selectedCamera = cameraChoice.getSelectionModel().getSelectedItem();

            numSamples.getValueFactory().setValue(selectedCamera.getNumSamples());
            samplingChoice.getSelectionModel().select(selectedCamera.getSamplingPattern());
            setCameraValues();
            bindCameraLabels();
            switch (selectedCamera) {
                case ORTHOGRAPHIC:
                    labelTextExtra.setText("Scale factor");
                    labelPersp.setVisible(false);
                    break;
                case PERSPECTIVE:
                    labelTextExtra.setText("View angle");
                    labelPersp.setVisible(true);
                    break;
            }
            rerender(false);
        });

        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        treeView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem<NodeContainer>>) c -> {
            selectedNodes.clear();
            try {
                selectedNodes = (ObservableList<NodeContainer>) treeView.getSelectionModel().getSelectedItems().stream().map(item -> {
                    final TreeItem<NodeContainer> tempItem = (TreeItem<NodeContainer>) item;
                    return tempItem.getValue();
                }).collect(collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
            } catch (final NullPointerException e) {
                treeView.getSelectionModel().clearSelection();
            }
        });

        nodeChoice.setOnAction((event) -> {
            final int index = nodeChoice.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                selectedNode = allNodes.get(index);
                selectedNodeIndex = index;
                bindNodeLabels();
            }
        });

        lightMap.addListener((MapChangeListener) change -> rerender(false));

        rootNodes.addListener((ListChangeListener<? super NodeContainer>) (ListChangeListener) -> {
            allNodes.clear();
            getChildren(rootNodes);
        });

        treeView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                final TreeItem<NodeContainer> item = (TreeItem<NodeContainer>) treeView.getSelectionModel().getSelectedItem();
                final PopOver popOver = new PopOver();

                final Button submitBtn = new Button("", new Glyph("FontAwesome", "CHECK"));
                final TextField textField = new TextField(item.getValue().getName());

                textField.setOnKeyReleased((keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        submitBtn.fire();
                    }
                });

                submitBtn.setOnAction(event -> {
                    item.getValue().setName(textField.getText());
                    allNodes.clear();
                    getChildren(rootNodes);
                    nodeChoice.setItems(allNodes);
                    treeView.refresh();
                    popOver.hide();
                });

                final HBox hBox = new HBox(textField, submitBtn);
                hBox.setPadding(new Insets(10, 10, 0, 10));
                hBox.setMargin(submitBtn, new Insets(0, 0, 0, 5));

                popOver.setContentNode(hBox);
                popOver.show(apMain, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        });


        refractionIndexSpinner.setEditable(true);
        refractionIndexSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0));
        refractionIndexSpinner.getValueFactory().setValue(1.0);
        refractionIndexSpinner.valueProperty().addListener((ChangeListener) -> {
            refractionIndex = refractionIndexSpinner.getValueFactory().getValue();
        });


        numSamples.setEditable(true);
        numSamples.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000));

        numSamples.valueProperty().addListener((ChangeListener) -> {
            selectedCamera.setNumSamples(numSamples.getValue());
            rerender(false);
        });

        samplingChoice.setOnAction((event) -> {
            selectedCamera.setSamplingPaterns(samplingChoice.getSelectionModel().getSelectedItem());
            rerender(false);
        });

    }

    private void getChildren(ObservableList<NodeContainer> rootNodes) {
        for (final NodeContainer nodeContainer : rootNodes) {
            allNodes.add(nodeContainer);
            for (final NodeContainer child : nodeContainer.getChildren()) {
                if (child.getChildren().isEmpty()) {
                    allNodes.add(child);
                } else {
                    getChildren(child.getChildren());
                }
            }
        }
    }

    private int getIndexByName(ObservableList<NodeContainer> items, String name) {
        for (int i = 0; i < items.size(); i++) {
            if (name.equals(items.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    private void removeByName(ObservableList<NodeContainer> items, String name) {
        for (int i = 0; i < items.size(); i++) {
            if (name.equals(items.get(i).getName())) {
                items.remove(i);
            }
        }
    }

    private void setupResizeListener() {
        scene.widthProperty().addListener((ChangeListener) -> {
            if (scene.getWidth() > 0) {
                rerender(false);
            }
        });
        scene.heightProperty().addListener((ChangeListener) -> {
            if (scene.getHeight() > 0) {
                rerender(false);
            }
        });
    }

    private void bindCameraLabels() {
        for (final TextField l : cameraLabels) {
            String id = l.getId();
            l.setText(String.valueOf(selectedCamera.get(id).getValue()));
            l.setOnKeyReleased((keyEvent) -> {
                try {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        selectedCamera.get(id).set(Double.parseDouble(l.getText()));
                        rerender(false);
                    } else if (keyEvent.getCode() == KeyCode.UP) {
                        if (keyEvent.isShiftDown()) {
                            selectedCamera.setValue(id, selectedCamera.getValue(id) + 1);
                        } else {
                            selectedCamera.setValue(id, selectedCamera.getValue(id) + 0.1);
                        }
                        rerender(false);
                    } else if (keyEvent.getCode() == KeyCode.DOWN) {
                        if (keyEvent.isShiftDown()) {
                            selectedCamera.setValue(id, selectedCamera.getValue(id) - 1);
                        } else {
                            selectedCamera.setValue(id, selectedCamera.getValue(id) - 0.1);
                        }
                        rerender(false);
                    }
                } catch (NumberFormatException e) {
                    Notifications.create()
                            .position(Pos.TOP_RIGHT)
                            .title("Value not accepted")
                            .text(String.valueOf(e))
                            .showError();
                }
            });
            selectedCamera.get(id).addListener((ChangeListener) -> {
                try {
                    l.setText(String.valueOf(Helper.round(selectedCamera.get(id).getValue())));
                } catch (ParseException ignored) {
                }
            });
        }
    }

    private void bindNodeLabels() {
        for (final TextField l : nodeLabels) {
            String id = l.getId();
            l.setText(String.valueOf(selectedNode.get(id).getValue()));
            l.setOnKeyReleased((keyEvent) -> {
                try {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        selectedNode.get(id).set(Double.parseDouble(l.getText()));
                        rerender(false);
                    } else if (keyEvent.getCode() == KeyCode.UP) {
                        if (keyEvent.isShiftDown()) {
                            selectedNode.setValue(id, selectedNode.getValue(id) + 1);
                        } else {
                            selectedNode.setValue(id, selectedNode.getValue(id) + 0.1);
                        }
                        rerender(false);
                    } else if (keyEvent.getCode() == KeyCode.DOWN) {
                        if (keyEvent.isShiftDown()) {
                            selectedNode.setValue(id, selectedNode.getValue(id) - 1);
                        } else {
                            selectedNode.setValue(id, selectedNode.getValue(id) - 0.1);
                        }
                        rerender(false);
                    }
                } catch (NumberFormatException e) {
                    Notifications.create()
                            .position(Pos.TOP_RIGHT)
                            .title("Value not accepted")
                            .text(String.valueOf(e))
                            .showError();
                }
            });
            selectedNode.get(id).addListener((ChangeListener) -> {
                try {
                    l.setText(String.valueOf(Helper.round(selectedNode.get(id).getValue())));
                } catch (ParseException ignored) {
                }
            });
        }
    }

    private void changeNodeValues(double value, TextField l) {
        final String id = l.getId();
        selectedNode = allNodes.get(selectedNodeIndex);
        selectedNode.setValue(id, Helper.sliderVal(selectedNode.getValue(id), value));
        rerender(false);
    }

    private void changeCameraValues(double value, TextField l) {
        final String id = l.getId();
        selectedCamera.setValue(id, Helper.sliderVal(selectedCamera.getValue(id), value));
        rerender(false);
    }

    @FXML
    private void setCameraValues() {
        camera = selectedCamera.getCamera();
    }

    private void initializeViewer() {
        imgView = new ImageView();
        imgView.setOnZoom((zoomEvent) -> {
            selectedCamera.setValue(labelEz.getId(), selectedCamera.getValue(labelEz.getId()) / zoomEvent.getZoomFactor());
            rerender(false);
        });
        imgView.setOnScroll((scrollEvent) -> {
            selectedCamera.setValue(labelGx.getId(), selectedCamera.getValue(labelGx.getId()) + scrollEvent.getDeltaX() / 100);
            selectedCamera.setValue(labelGy.getId(), selectedCamera.getValue(labelGy.getId()) + scrollEvent.getDeltaY() / 100);
            rerender(false);
        });
        imgView.setOnRotate((rotateEvent) -> {
            selectedCamera.setValue(labelUy.getId(), selectedCamera.getValue(labelUy.getId()) + rotateEvent.getAngle() / 100);
            rerender(false);
        });
        mainViewer.getChildren().addAll(imgView);
        createWorld();
        startRenderingThreads(640, 480);
    }

    private color.Color generateColor(javafx.scene.paint.Color value) {
        return new color.Color(value.getRed(), value.getGreen(), value.getBlue());
    }

    private javafx.scene.paint.Color generateColor(Color value) {
        return new javafx.scene.paint.Color(value.r, value.g, value.b, 1);
    }

    /**
     * Rendert das Bild einmal komplett neu indem eine neue World erzeugt wird
     * und anschließend das Bild neu gezeichnet wird
     */
    @FXML
    public void rerender(boolean clicked) {
        if (clicked || checkAutorender.isSelected()) {
            setCameraValues();
            createWorld();
            if (mainViewer.getWidth() > 0 && mainViewer.getHeight() > 0)
                startRenderingThreads((int) mainViewer.getWidth(), (int) mainViewer.getHeight());
        }
    }

    @FXML
    private void btnRerender() {
        rerender(true);
    }

    public void createWorld() {

        world = new World(backgroundColor, ambientColor, refractionIndex);

        for (final LightContainer l : lightMap.values()) {
            world.addLight(l.getLight());
        }

        for (final NodeContainer n : rootNodes) {
            world.addGeometry(n.getNode());
        }
    }

    static void shuffleList(List<int[]> li) {
        final Random rnd = ThreadLocalRandom.current();
        for (int i = li.size() - 1; i > 0; i--) {
            final int index = rnd.nextInt(i + 1);
            final int[] l = li.get(index);
            li.set(index, li.get(i));
            li.set(i, l);
        }
    }

    private void startRenderingThreads(int width, int height) {

        if (service != null) {
            service.shutdownNow();
        }

        messageProperty.set("RENDERING");

        if (previousHeight != height || previousWidth != width) {
            wrImg = new WritableImage(width, height);

            pixelWriter = wrImg.getPixelWriter();
            imgView.setImage(wrImg);

            coordinates = new ArrayList<>();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (previousHeight != height || previousWidth != width) {
                        pixelWriter.setColor(x, y, generateColor(world.backgroundColor));
                    }
                    coordinates.add(new int[]{x, y});
                }
            }

        }

        previousHeight = height;
        previousWidth = width;

        shuffleList(coordinates);

        service = Executors.newFixedThreadPool(cores);

        final ArrayList<ReadOnlyDoubleProperty> progress = new ArrayList<>();
        final ArrayList<ReadOnlyStringProperty> messages = new ArrayList<>();
        final ArrayList<ReadOnlyObjectProperty> timeValues = new ArrayList<>();
        for (int i = 0; i < cores; i++) {
            final RenderTaskRandom renderTaskRandom = new RenderTaskRandom("Thread_" + (i + 1), coordinates, cores, i, width, height,
                    camera, world, pixelWriter);
            progress.add(renderTaskRandom.progressProperty());
            messages.add(renderTaskRandom.messageProperty());
            timeValues.add(renderTaskRandom.valueProperty());
            service.submit(renderTaskRandom);
        }

        final SimpleDoubleProperty progressProperty = new SimpleDoubleProperty();
        final SimpleLongProperty totalTimeProperty = new SimpleLongProperty(0);

        for (int i = 0; i < progress.size(); i++) {
            final ReadOnlyDoubleProperty r = progress.get(i);
            r.addListener((ChangeListener) -> {
                if (progressProperty.get() <= r.get()) {
                    progressProperty.set(r.get());
                }
            });
            final ReadOnlyStringProperty s = messages.get(i);
            s.addListener((ChangeListener) -> {
                messageProperty.set(s.get());
            });
            final ReadOnlyObjectProperty o = timeValues.get(i);
            o.addListener((ChangeListener) -> {
                try {
                    totalTimeProperty.set(totalTimeProperty.get() + (Long) o.getValue());
                } catch (final Exception ignored) {
                }
                messageProperty.set("Rendering Time: " + (((totalTimeProperty.get()) / 1000000000.0F)) / cores);
            });
        }

        statusBar.textProperty().bind(messageProperty);

        statusBar.progressProperty().bind(progressProperty);
        service.shutdown();
    }

    @FXML
    private void newGeometryWindow(ActionEvent actionEvent) throws IOException {
        if (allNodes.size() > 0)
            generateStage("../layouts/RayTracerGeometryLayout.fxml", 500, 350);
    }

    @FXML
    public void newLightWindow(ActionEvent actionEvent) throws IOException {
        if (!lightWindowOpen)
            generateStage("../layouts/RayTracerLightLayout.fxml", 500, 400);
    }

    public void stopRendering() {
        service.shutdownNow();
    }

    public void generateStage(String path, int width, int height) throws IOException {
        stage = (Stage) apMain.getScene().getWindow();
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        final Parent root = loader.load();
        final Stage stage = new Stage();
        stage.setUserData(this);
        stage.setScene(new Scene(root, width, height));
        if (path.equals("../layouts/RayTracerGeometryLayout.fxml")) {
            final RayTracerGeometryController controller = loader.getController();
            controller.setMainController(this);
            controller.setStage(this.stage);
            controller.initialization();
            stage.setUserData(selectedNodeIndex);
            stage.focusedProperty().addListener((ov, t, t1) -> {
                final ReadOnlyBooleanProperty o = (ReadOnlyBooleanProperty) ov;
                final Stage s = (Stage) o.getBean();
                selectedNodeIndex = (int) s.getUserData();
                selectedNode = allNodes.get(selectedNodeIndex);
                nodeChoice.getSelectionModel().select(selectedNodeIndex);
                bindNodeLabels();
            });
            stage.setTitle("Geometries of: " + selectedNode.getName());
        } else {
            final RayTracerLightController controller = loader.getController();
            controller.setMainController(this);
            controller.initialization();
            stage.setTitle("Lights");
            openLightBtn.setDisable(true);
            lightWindowOpen = true;
            stage.setOnCloseRequest(we -> {
                lightWindowOpen = false;
                openLightBtn.setDisable(false);
            });
        }
        stage.show();
    }

    @FXML
    public void createNode() {
        nodeIndex += 1;
        final String newNodeName = "node_" + nodeIndex;
        selectedNode = new NodeContainer(newNodeName);
        rootNodes.add(selectedNode);
        allNodes.clear();
        getChildren(rootNodes);
        selectedNodeIndex = getIndexByName(allNodes, selectedNode.getName());
        nodeChoice.setItems(allNodes);
        nodeChoice.getSelectionModel().select(selectedNodeIndex);
    }

    @FXML
    public void deleteNode(ActionEvent actionEvent) {
        try {
            removeRecursive(selectedNodes);
        } catch (ConcurrentModificationException ignored) {
        }
        rerender(false);
    }

    public void removeRecursive(ObservableList<NodeContainer> selectedNodes) {
        for (final NodeContainer nodeContainer : selectedNodes) {
            rootNodes.remove(nodeContainer);
            for (final NodeContainer n : rootNodes) {
                n.removeChild(nodeContainer);
                for (final NodeContainer child : n.getChildren()) {
                    child.removeChild(nodeContainer);
                    if (!child.getChildren().isEmpty()) {
                        getChildren(child.getChildren());
                    }
                }
            }
        }
    }

    @FXML
    public void saveImageAs(ActionEvent actionEvent) {
        stage = (Stage) apMain.getScene().getWindow();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        final FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        final FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        final File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(wrImg, null), "png", file);
            } catch (final IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        stage = (Stage) apMain.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void editNodes(ActionEvent actionEvent) {
        final PopOver popOver = new PopOver();

        final VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        final int size = selectedNodes.size();
        Label label;
        final ChoiceBox<NodeContainer> subNodeChoice = new ChoiceBox<>();
        final Button submitBtn = new Button("", new Glyph("FontAwesome", "CHECK"));

        submitBtn.setOnAction(event -> {
            final NodeContainer selectedNode = subNodeChoice.getSelectionModel().getSelectedItem();
            if (selectedNode.getName().equals("root")) {

                try {
                    for (final NodeContainer n : selectedNodes) {
                        final NodeContainer parent = n.getParent();
                        if (parent != null) {
                            parent.removeChild(n);
                            n.setParent(null);
                        }
                        rootNodes.add(n);
                    }
                } catch (ConcurrentModificationException ignored) {
                }

            } else try {
                for (final NodeContainer n : selectedNodes) {
                    selectedNode.addChild(n);
                    final NodeContainer parent = n.getParent();
                    if (n.getParent() != null) {
                        parent.removeChild(n);
                    } else {
                        rootNodes.remove(n);
                    }
                    n.setParent(selectedNode);
                }
            } catch (final ConcurrentModificationException ignored) {
            }
            removeByName(allNodes, "root");
            popOver.hide();
        });

        if (size == 0) {
            label = new Label("No items selected!");
            subNodeChoice.setDisable(true);
            submitBtn.setDisable(true);
        } else {
            label = new Label("Move " + size + " items to node:");
        }
        label.setPadding(new Insets(0, 0, 5, 0));

        if (getIndexByName(allNodes, "root") == -1) {
            allNodes.add(new NodeContainer("root"));
        }
        subNodeChoice.setItems(allNodes.filtered((NodeContainer) -> !selectedNodes.contains(NodeContainer)));

        final HBox hBox = new HBox(subNodeChoice, submitBtn);
        hBox.setMargin(submitBtn, new Insets(0, 0, 0, 5));

        vBox.getChildren().addAll(label, hBox);
        popOver.setContentNode(vBox);
        popOver.show(editBtn);

    }

    @FXML
    public void duplicateNode(ActionEvent actionEvent) {
        for (final NodeContainer n : selectedNodes) {
            final NodeContainer clone = new NodeContainer(n);
            clone.setName(n.getName().concat("DUPL"));
            rootNodes.add(clone);
        }
    }

    public static class PlatformHelper {

        public static void run(Runnable treatment) {
            if (treatment == null)
                throw new IllegalArgumentException("The treatment to perform can not be null");
            if (Platform.isFxApplicationThread())
                treatment.run();
            else
                Platform.runLater(treatment);
        }
    }

}