package raytracergui.controller;

import Matrizen_Vektoren_Bibliothek.Point3;
import camera.Camera;
import color.Color;
import com.sun.javafx.geom.Vec3d;
import geometries.Geometry;
import geometries.Node;
import geometries.Sphere;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import material.ReflectiveMaterial;
import org.controlsfx.control.PlusMinusSlider;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.Glyph;
import ray.Transform;
import ray.World;
import raytracergui.RenderThread;
import raytracergui.container.GeometryContainer;
import raytracergui.container.LightContainer;
import raytracergui.container.NodeContainer;
import raytracergui.dataclasses.TreeViewWithItems;
import textures.SingleColorTexture;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

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
    private CheckMenuItem checkAutorender;
    @FXML
    private CheckMenuItem openLightBtn;
    @FXML
    private Menu refreshBtn;
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


    /**
     * View für die Erzeugung des Images
     */
    private ImageView imgView;

    /**
     * world - Welt Objekt
     */
    private World world;

    /**
     * wrImg - WritableImage Objekt
     */
    private WritableImage wrImg;

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

    private ObservableList<raytracergui.enums.Camera> cameraNames = FXCollections.observableArrayList(raytracergui.enums.Camera.values());
    private ObservableMap<String, raytracergui.enums.Camera> cameraMap = FXCollections.observableHashMap();

    private int nodeIndex = 0;
    private String selectedNodeName;

    public ObservableMap<String, LightContainer> lightMap = FXCollections.observableHashMap();
    public NodeContainer selectedNode;

    private ObservableList<NodeContainer> nodes = FXCollections.observableArrayList();
    private ObservableList<NodeContainer> selectedItems = FXCollections.observableArrayList();

    private raytracergui.enums.Camera selectedCamera;
    private boolean lightWindowOpen;

    private Color backgroundColor = new Color(0, 0, 0);
    private double mouseOldY;
    private double mouseOldX;
    private double mousePosX;
    private double mousePosY;
    private double mouseDeltaX;
    private double mouseDeltaY;
    private Vec3d vecPos;

    private TreeViewWithItems treeView;
    private TreeItem<NodeContainer> root;


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
        createNode();

        root = new CheckBoxTreeItem<>();

        treeView = new TreeViewWithItems(root);
        treeView.setShowRoot(false);
        nodeListView.setMargin(treeView, new Insets(10, 0, 0, 0));
        nodeListView.getChildren().add(treeView);

        treeView.setItems(nodes);
        treeView.refreshList(root);

        nodeChoice.setItems(treeView.allEntries);
        nodeChoice.getSelectionModel().select(selectedNode);

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
            if (checkAutorender.isSelected()) {
                rerender();
            }
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
                    if (checkAutorender.isSelected()) {
                        rerender();
                    }
                }

        );


        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        treeView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem<NodeContainer>>) c -> {
            selectedItems = (ObservableList<NodeContainer>) treeView.getSelectionModel().getSelectedItems().stream().map(item -> {
                TreeItem<NodeContainer> tempItem = (TreeItem<NodeContainer>) item;
                return tempItem.getValue();
            }).collect(collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
        });

        lightMap.addListener((MapChangeListener) change -> {
            if (checkAutorender.isSelected()) {
                rerender();
            }
        });
    }


    private void setupResizeListener() {
//        scene.widthProperty().addListener((ChangeListener) -> {
//            if (checkAutorender.isSelected()) {
//                rerender();
//            }
//        });
//        scene.heightProperty().addListener((ChangeListener) -> {
//            if (checkAutorender.isSelected()) {
//                rerender();
//            }
//        });
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
        selectedNode = getNodeContainer(selectedNodeName);
        selectedNode.setValue(id, selectedNode.getValue(id) + value);

        if (checkAutorender.isSelected()) {
            setNodeValues();
            rerender();
        }
    }

    private void setNodeValues() {
        overwriteNodeContainer(selectedNodeName);
    }

    private void changeCameraValues(double value, Label l) {
        String id = l.getId();
        selectedCamera.setValue(id, selectedCamera.getValue(id) + value);
        if (checkAutorender.isSelected()) {
            rerender();
        }
    }

    @FXML
    private void setCameraValues() {
        camera = selectedCamera.getCamera();
    }

    @FXML
    private void toggleButton() {
        if (checkAutorender.isSelected()) {
            refreshBtn.setVisible(false);
        } else {
            refreshBtn.setVisible(true);
        }
    }

    private void initializeViewer() {
        imgView = new ImageView();
        mainViewer.getChildren().add(imgView);
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


    /**
     * Rendert das Bild einmal komplett neu indem eine neue World erzeugt wird
     * und anschließend das Bild neu gezeichnet wird
     */
    @FXML
    public void rerender() {
        setCameraValues();
        createWorld();
        startRenderingThreads((int) mainViewer.getWidth(), (int) mainViewer.getHeight());
    }

    public void createWorld() {

        world = new World(backgroundColor);

        for (LightContainer l : lightMap.values()) {
            world.addLight(l.getLight());
        }

        for (NodeContainer n : nodes) {
            world.addGeometry(n.getNode());
        }
    }

    private void startRenderingThreads(int width, int height) {

        final ExecutorService service;
        final int cores = Runtime.getRuntime().availableProcessors() / 2;

        wrImg = new WritableImage(width, height);
        service = Executors.newFixedThreadPool(cores);

        ArrayList<Future> tasks = new ArrayList<>();
        for (int i = 0; i < cores; i++) {
            tasks.add(service.submit(new RenderThread(cores, i, width, height, wrImg, camera, world)));
        }

        long sumTimeTaken = 0;
        for (Future<Long> t : tasks) {
            try {
                sumTimeTaken += t.get();
            } catch (InterruptedException e) {
                System.out.println(e);
            } catch (ExecutionException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        statusBar.setText(" Rendering Time: " + (((sumTimeTaken) / 1000000000.0F)) / cores);
        imgView.setImage(wrImg);
    }

    public void addGeometries() {
        setNodeValues();
        rerender();
    }

    @FXML
    private void newGeometryWindow(ActionEvent actionEvent) throws IOException {
        generateStage("../layouts/RayTracerGeometryLayout.fxml", 500, 350);
    }

    @FXML
    public void newLightWindow(ActionEvent actionEvent) throws IOException {
        if (!lightWindowOpen)
            generateStage("../layouts/RayTracerLightLayout.fxml", 500, 400);
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
            stage.setUserData(selectedNodeName);
            stage.focusedProperty().addListener((ov, t, t1) -> {
                ReadOnlyBooleanProperty o = (ReadOnlyBooleanProperty) ov;
                Stage s = (Stage) o.getBean();
                selectedNodeName = (String) s.getUserData();
                selectedNode = getNodeContainer(selectedNodeName);
                nodeChoice.getSelectionModel().select(treeView.allEntries.indexOf(selectedNode));
                bindNodeLabels();
            });
            stage.setTitle("Geometries of: " + selectedNodeName);
        } else {
            RayTracerLightController controller = loader.getController();
            controller.setMainController(this);
            controller.setStage(this.stage);
            controller.initialization();
            stage.setTitle("Lights");
            openLightBtn.setDisable(true);
            lightWindowOpen = true;
            stage.setOnCloseRequest(we -> {
                        lightWindowOpen = false;
                        openLightBtn.setDisable(false);
                        openLightBtn.setSelected(false);
                    }
            );
        }
        stage.show();
    }

    @FXML
    public void createNode() {
        nodeIndex += 1;
        String newNodeName = "node_" + nodeIndex;
        selectedNodeName = newNodeName;
        selectedNode = new NodeContainer(newNodeName);

        final Node sphereNode = new Node(new Transform().translate(new Point3(3, 4, 6)).rotateY(-4).rotateX(-5).scale(1, 1, 1), new ArrayList<Geometry>());
        sphereNode.geos.add(new Sphere(new ReflectiveMaterial(new SingleColorTexture(new Color(1, 0, 0)), new SingleColorTexture(new Color(
                1, 1, 1)), new SingleColorTexture(new Color(0.8, 0.4, 0)), 64)));

        GeometryContainer geometryContainer = new GeometryContainer();
        geometryContainer.addGeometry(sphereNode);
        geometryContainer.setName("TEST");

        NodeContainer sphere = new NodeContainer("Sphere");

        sphere.addGeometry(geometryContainer);
        selectedNode.addChild(sphere);

        nodes.add(selectedNode);
        treeView.refreshList(root);
    }

    public NodeContainer getNodeContainer(String key) {
        for (NodeContainer n : nodes) {
            if (key.equals(n.getName())) {
                return n;
            }
        }
        return null;
    }


    private void overwriteNodeContainer(String key) {
        if (getNodeContainer(selectedNodeName) == null) {
            nodes.add(selectedNode);
        } else {
            for (NodeContainer n : nodes) {
                if (key.equals(n.getName())) {
                    int index = nodes.indexOf(n);
                    if (index != -1) {
                        nodes.remove(index);
                        nodes.add(index, selectedNode);
                    }
                }
            }
        }
    }

//    @FXML
//    public void deleteNode(ActionEvent actionEvent) {
//        nodeMap.remove(selectedNodeName);
//
//        if (checkAutorender.isSelected()) {
//            rerender();
//        }
//    }

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

    public void getMouseDrag(Event event) {
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;

            Point3D coords = me.getPickResult().getIntersectedPoint();

            double x = coords.getX();
            double y = coords.getY();
            double z = coords.getZ();

            System.out.println(x);
            System.out.println(y);
            System.out.println(z);

        }
    }

    @FXML
    public void deleteNode(ActionEvent actionEvent) {
    }

    @FXML
    public void editNodes(ActionEvent actionEvent) {
        PopOver popOver = new PopOver();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        int size = selectedItems.size();
        Label label;
        ChoiceBox<NodeContainer> subNodeChoice = new ChoiceBox<>();
        Button submitBtn = new Button("", new Glyph("FontAwesome","CHECK"));
        if (size == 0) {
            label = new Label("No items selected!");
            subNodeChoice.setDisable(true);
            submitBtn.setDisable(true);
        } else {
            label = new Label("Move "+size+" items to node:");
        }
        label.setPadding(new Insets(0,0,5,0));

        System.out.println(selectedItems);
        System.out.println(treeView.allEntries.get(0));

        subNodeChoice.setItems(treeView.allEntries.filtered((NodeContainer) -> !selectedItems.contains(NodeContainer)));


        HBox hBox = new HBox(subNodeChoice, submitBtn);
        hBox.setMargin(submitBtn, new Insets(0,0,0,5));

        vBox.getChildren().addAll(label, hBox);
        popOver.setContentNode(vBox);
        popOver.show(editBtn);
    }


}