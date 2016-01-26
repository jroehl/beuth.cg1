package raytracergui.controller;

import camera.Camera;
import color.Color;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PlusMinusSlider;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.Glyph;
import ray.World;
import raytracergui.container.LightContainer;
import raytracergui.container.NodeContainer;
import raytracergui.dataclasses.TreeViewWithItems;
import raytracergui.enums.SamplingPattern;
import raytracergui.threads.RenderTaskRandom;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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
    private ChoiceBox cameraChoice;
    @FXML
    private ChoiceBox nodeChoice;
    @FXML
    private HBox mainViewer;
    @FXML
    private PlusMinusSlider eX;
    @FXML
    private Label labelEx;
    @FXML
    private PlusMinusSlider eY;
    @FXML
    private Label labelEy;
    @FXML
    private PlusMinusSlider eZ;
    @FXML
    private Label labelEz;
    @FXML
    private PlusMinusSlider gX;
    @FXML
    private Label labelGx;
    @FXML
    private PlusMinusSlider gY;
    @FXML
    private Label labelGy;
    @FXML
    private PlusMinusSlider gZ;
    @FXML
    private Label labelGz;
    @FXML
    private PlusMinusSlider uX;
    @FXML
    private Label labelUx;
    @FXML
    private PlusMinusSlider uY;
    @FXML
    private Label labelUy;
    @FXML
    private PlusMinusSlider uZ;
    @FXML
    private Label labelUz;
    @FXML
    private PlusMinusSlider extra;
    @FXML
    private Label labelTextExtra;
    @FXML
    private Label labelValExtra;
    @FXML
    private Label labelPersp;
    @FXML
    private Button openLightBtn;
    @FXML
    private StatusBar statusBar;
    @FXML
    private PlusMinusSlider transX;
    @FXML
    private Label labelTransX;
    @FXML
    private PlusMinusSlider transY;
    @FXML
    private Label labelTransY;
    @FXML
    private PlusMinusSlider transZ;
    @FXML
    private Label labelTransZ;
    @FXML
    private PlusMinusSlider scaleX;
    @FXML
    private Label labelScaleX;
    @FXML
    private PlusMinusSlider scaleY;
    @FXML
    private Label labelScaleY;
    @FXML
    private PlusMinusSlider scaleZ;
    @FXML
    private Label labelScaleZ;
    @FXML
    private PlusMinusSlider translateX;
    @FXML
    private Label labelTranslateX;
    @FXML
    private PlusMinusSlider translateY;
    @FXML
    private Label labelTranslateY;
    @FXML
    private PlusMinusSlider translateZ;
    @FXML
    private Label labelTranslateZ;
    @FXML
    private ColorPicker backgroundColorPicker;
    @FXML
    private Accordion nodeAccordion;
    @FXML
    private TitledPane nodeSettingsPane;
    @FXML
    private VBox nodeListView;
    @FXML
    private Button editBtn;
    @FXML
    private Spinner numSamples;
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

    private PlusMinusSlider[] cameraSliders;
    private Label[] cameraLabels;
    private PlusMinusSlider[] nodeSliders;
    private Label[] nodeLabels;

    private Button refreshBtn;
    private Button stopBtn;
    private CheckBox checkAutorender;

    private ObservableList<raytracergui.enums.Camera> cameraNames = FXCollections.observableArrayList(raytracergui.enums.Camera.values());
    private ObservableList<SamplingPattern> samplingPatterns = FXCollections.observableArrayList(SamplingPattern.values());
    private ObservableMap<String, raytracergui.enums.Camera> cameraMap = FXCollections.observableHashMap();

    private int nodeIndex = 0;
    private int selectedNodeIndex;

    public ObservableMap<String, LightContainer> lightMap = FXCollections.observableHashMap();
    public NodeContainer selectedNode;

    private ObservableList<NodeContainer> rootNodes = FXCollections.observableArrayList();
    private ObservableList<NodeContainer> allNodes = FXCollections.observableArrayList();
    private ObservableList<NodeContainer> selectedNodes = FXCollections.observableArrayList();

    private raytracergui.enums.Camera selectedCamera;
    private boolean lightWindowOpen;

    private Color backgroundColor = new Color(0, 0, 0);

    private TreeViewWithItems treeView;

    private final int cores = Runtime.getRuntime().availableProcessors() / 2;

    public ExecutorService service;
    private SimpleStringProperty messageProperty = new SimpleStringProperty("OK");
    private int previousHeight;
    private int previousWidth;

    @FXML
    public void initialize() {

        nodeAccordion.setExpandedPane(nodeSettingsPane);

        cameraChoice.setItems(cameraNames);
        for (Enum c : cameraNames) {
            raytracergui.enums.Camera camera = (raytracergui.enums.Camera) c;
            cameraMap.put(camera.getName(), camera);
        }
        cameraChoice.getSelectionModel().selectLast();
        selectedCamera = cameraMap.get(cameraChoice.getSelectionModel().getSelectedItem().toString());

        samplingChoice.setItems(samplingPatterns);
        samplingChoice.getSelectionModel().select(SamplingPattern.ONERAY);

        treeView = new TreeViewWithItems(new TreeItem<>());
        treeView.setShowRoot(false);

        HBox hBox = new HBox();

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPadding(new Insets(0, 0, 0, 5));

        refreshBtn = new Button("", new Glyph("FontAwesome", "REFRESH"));
        refreshBtn.setOnAction((event -> btnRerender()));

        stopBtn = new Button("", new Glyph("FontAwesome", "STOP"));
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

        cameraSliders = new PlusMinusSlider[]{eX, eY, eZ, gX, gY, gZ, uX, uY, uZ, extra};
        cameraLabels = new Label[]{labelEx, labelEy, labelEz, labelGx, labelGy, labelGz, labelUx, labelUy, labelUz, labelValExtra};
        nodeSliders = new PlusMinusSlider[]{transX, transY, transZ, scaleX, scaleY, scaleZ, translateX, translateY, translateZ};
        nodeLabels = new Label[]{labelTransX, labelTransY, labelTransZ, labelScaleX, labelScaleY, labelScaleZ, labelTranslateX, labelTranslateY, labelTranslateZ};


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
            Label l = cameraLabels[i];
            PlusMinusSlider p = cameraSliders[i];
            p.setOnValueChanged(e -> changeCameraValues(e.getValue(), l));
        }

        bindNodeLabels();

        for (int i = 0; i < nodeSliders.length; i++) {
            Label l = nodeLabels[i];
            PlusMinusSlider p = nodeSliders[i];
            p.setOnValueChanged(e -> changeNodeValues(e.getValue(), l));
        }

        cameraChoice.setOnAction((event) ->

                {
                    selectedCamera = (raytracergui.enums.Camera) cameraChoice.getSelectionModel().getSelectedItem();
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
                }
        );


        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        treeView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem<NodeContainer>>) c -> {
            selectedNodes.clear();
            try {
                selectedNodes = (ObservableList<NodeContainer>) treeView.getSelectionModel().getSelectedItems().stream().map(item -> {
                    TreeItem<NodeContainer> tempItem = (TreeItem<NodeContainer>) item;
                    return tempItem.getValue();
                }).collect(collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
            } catch (NullPointerException e) {
                treeView.getSelectionModel().clearSelection();
            }
        });

        nodeChoice.setOnAction((event) -> {
            int index = nodeChoice.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                selectedNode = allNodes.get(index);
                selectedNodeIndex = index;
                bindNodeLabels();
            }
        });

        lightMap.addListener((MapChangeListener) change -> {
            rerender(false);
        });

        rootNodes.addListener((ListChangeListener<? super NodeContainer>) (ListChangeListener) -> {
            allNodes.clear();
            getChildren(rootNodes);
        });

        treeView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                TreeItem<NodeContainer> item = (TreeItem<NodeContainer>) treeView.getSelectionModel().getSelectedItem();
                PopOver popOver = new PopOver();

                Button submitBtn = new Button("", new Glyph("FontAwesome", "CHECK"));
                TextField textField = new TextField(item.getValue().getName());

                submitBtn.setOnAction(event -> {
                    item.getValue().setName(textField.getText());
                    allNodes.clear();
                    getChildren(rootNodes);
                    nodeChoice.setItems(allNodes);
                    treeView.refresh();
                    popOver.hide();
                });

                HBox hBox = new HBox(textField, submitBtn);
                hBox.setPadding(new Insets(10, 10, 0, 10));
                hBox.setMargin(submitBtn, new Insets(0, 0, 0, 5));

                popOver.setContentNode(hBox);
                popOver.show(apMain, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        });

        numSamples.setEditable(true);
        numSamples.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000));

        numSamples.valueProperty().addListener((ChangeListener) -> {
            selectedCamera.setNumSamples((int) numSamples.getValue());
            rerender(false);
        });

        samplingChoice.setOnAction((event) -> {
            selectedCamera.setSamplingPaterns(samplingChoice.getSelectionModel().getSelectedItem());
            rerender(false);
        });


    }

    private void getChildren(ObservableList<NodeContainer> rootNodes) {
        for (NodeContainer nodeContainer : rootNodes) {
            allNodes.add(nodeContainer);
            for (NodeContainer child : nodeContainer.getChildren()) {
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
        for (Label l : cameraLabels) {
            l.textProperty().bind(createBinding(selectedCamera.get(l.getId())));
        }
    }

    private void bindNodeLabels() {
        for (Label l : nodeLabels) {
            l.textProperty().bind(createBinding(selectedNode.get(l.getId())));
        }
    }

    private void changeNodeValues(double value, Label l) {
        String id = l.getId();
        selectedNode = allNodes.get(selectedNodeIndex);
        selectedNode.setValue(id, selectedNode.getValue(id) + value);
        rerender(false);
    }

    private void changeCameraValues(double value, Label l) {
        String id = l.getId();
        selectedCamera.setValue(id, selectedCamera.getValue(id) + value);
        rerender(false);
    }

    @FXML
    private void setCameraValues() {
        camera = selectedCamera.getCamera();
    }

    private void initializeViewer() {
        imgView = new ImageView();
        mainViewer.getChildren().addAll(imgView);
        createWorld();
        startRenderingThreads(640, 480);
    }

    public StringBinding createBinding(SimpleDoubleProperty simpleDoubleProperty) {
        final int DIVIDEND = 4;
        return new StringBinding() {
            {
                bind(simpleDoubleProperty);
            }

            protected String computeValue() {
                return Double.toString(Math.round((simpleDoubleProperty.getValue() / DIVIDEND) * 10.0) / 10.0);
            }
        };
    }

    private color.Color generateColor(javafx.scene.paint.Color value) {
        return new color.Color(value.getRed(), value.getGreen(), value.getBlue());
    }

    private javafx.scene.paint.Color generateColor(Color value) {
        return new javafx.scene.paint.Color((double) value.r, (double) value.g, (double) value.b, 1);
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

        world = new World(backgroundColor);

        for (LightContainer l : lightMap.values()) {
            world.addLight(l.getLight());
        }

        for (NodeContainer n : rootNodes) {
            world.addGeometry(n.getNode());
        }
    }

    static void shuffleList(List<int[]> li) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = li.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int[] l = li.get(index);
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
        }

        previousHeight = height;
        previousWidth = width;

        PixelWriter pixelWriter = wrImg.getPixelWriter();
        imgView.setImage(wrImg);

        ArrayList<int[]> coordinates = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (previousHeight != height || previousWidth != width) {
                    pixelWriter.setColor(x, y, generateColor(backgroundColor));
                }
                coordinates.add(new int[]{x, y});
            }
        }

        shuffleList(coordinates);

        service = Executors.newFixedThreadPool(cores);

        ArrayList<ReadOnlyDoubleProperty> progress = new ArrayList<>();
        ArrayList<ReadOnlyStringProperty> messages = new ArrayList<>();
        ArrayList<ReadOnlyObjectProperty> timeValues = new ArrayList<>();
        for (int i = 0; i < cores; i++) {
            RenderTaskRandom renderTaskRandom = new RenderTaskRandom("Thread_" + (i + 1), coordinates, cores, i, width, height, camera, world, pixelWriter);
            progress.add(renderTaskRandom.progressProperty());
            messages.add(renderTaskRandom.messageProperty());
            timeValues.add(renderTaskRandom.valueProperty());
            service.submit(renderTaskRandom);
        }

        SimpleDoubleProperty progressProperty = new SimpleDoubleProperty();
        SimpleLongProperty totalTimeProperty = new SimpleLongProperty(0);

        for (int i = 0; i < progress.size(); i++) {
            ReadOnlyDoubleProperty r = progress.get(i);
            r.addListener((ChangeListener) -> {
                if (progressProperty.get() <= r.get()) {
                    progressProperty.set(r.get());
                }
            });
            ReadOnlyStringProperty s = messages.get(i);
            s.addListener((ChangeListener) -> {
                messageProperty.set(s.get());
            });
            ReadOnlyObjectProperty o = timeValues.get(i);
            o.addListener((ChangeListener) -> {
                try {
                    totalTimeProperty.set(totalTimeProperty.get() + (Long) o.getValue());
                } catch (Exception e) {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setUserData(this);
        stage.setScene(new Scene(root, width, height));
        if (path.equals("../layouts/RayTracerGeometryLayout.fxml")) {
            RayTracerGeometryController controller = loader.getController();
            controller.setMainController(this);
            controller.setStage(this.stage);
            controller.initialization();
            stage.setUserData(selectedNodeIndex);
            stage.focusedProperty().addListener((ov, t, t1) -> {
                ReadOnlyBooleanProperty o = (ReadOnlyBooleanProperty) ov;
                Stage s = (Stage) o.getBean();
                selectedNodeIndex = (int) s.getUserData();
                selectedNode = allNodes.get(selectedNodeIndex);
                nodeChoice.getSelectionModel().select(selectedNodeIndex);
                bindNodeLabels();
            });
            stage.setTitle("Geometries of: " + selectedNode.getName());
        } else {
            RayTracerLightController controller = loader.getController();
            controller.setMainController(this);
            controller.initialization();
            stage.setTitle("Lights");
            openLightBtn.setDisable(true);
            lightWindowOpen = true;
            stage.setOnCloseRequest(we -> {
                        lightWindowOpen = false;
                        openLightBtn.setDisable(false);
                    }
            );
        }
        stage.show();
    }

    @FXML
    public void createNode() {
        nodeIndex += 1;
        String newNodeName = "node_" + nodeIndex;
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
        } catch (ConcurrentModificationException e) {
            System.out.println(e);
        }
        rerender(false);
    }

    public void removeRecursive(ObservableList<NodeContainer> selectedNodes) {
        for (NodeContainer nodeContainer : selectedNodes) {
            rootNodes.remove(nodeContainer);
            for (NodeContainer n : rootNodes) {
                n.removeChild(nodeContainer);
                for (NodeContainer child : n.getChildren()) {
                    System.out.println(child);
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
        PopOver popOver = new PopOver();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        int size = selectedNodes.size();
        Label label;
        ChoiceBox<NodeContainer> subNodeChoice = new ChoiceBox<>();
        Button submitBtn = new Button("", new Glyph("FontAwesome", "CHECK"));

        submitBtn.setOnAction(event -> {
            NodeContainer selectedNode = subNodeChoice.getSelectionModel().getSelectedItem();
            if (selectedNode.getName().equals("root")) {

                try {
                    for (NodeContainer n : selectedNodes) {
                        NodeContainer parent = n.getParent();
                        if (parent != null) {
                            parent.removeChild(n);
                            n.setParent(null);
                        }
                        rootNodes.add(n);
                    }
                } catch (ConcurrentModificationException e) {
                    System.out.println(e);
                }

            } else if (selectedNode != null) {
                try {
                    for (NodeContainer n : selectedNodes) {
                        selectedNode.addChild(n);
                        NodeContainer parent = n.getParent();
                        if (n.getParent() != null) {
                            parent.removeChild(n);
                        } else {
                            rootNodes.remove(n);
                        }
                        n.setParent(selectedNode);
                    }
                } catch (ConcurrentModificationException e) {
                    System.out.println(e);
                }
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

        HBox hBox = new HBox(subNodeChoice, submitBtn);
        hBox.setMargin(submitBtn, new Insets(0, 0, 0, 5));

        vBox.getChildren().addAll(label, hBox);
        popOver.setContentNode(vBox);
        popOver.show(editBtn);

    }

    @FXML
    public void duplicateNode(ActionEvent actionEvent) {
        for (NodeContainer n : selectedNodes) {
            NodeContainer clone = new NodeContainer(n);
            clone.setName(n.getName().concat("DUPL"));
            rootNodes.add(clone);
        }
    }

    public static class PlatformHelper {

        public static void run(Runnable treatment) {
            if (treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");
            if (Platform.isFxApplicationThread()) treatment.run();
            else Platform.runLater(treatment);
        }
    }


}