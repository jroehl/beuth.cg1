package raytracergui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import raytracergui.container.GeometryContainer;
import raytracergui.enums.Geometry;
import raytracergui.enums.Material;
import raytracergui.enums.Texture;

import java.io.File;
import java.util.HashMap;

/**
 * Created by jroehl on 13.01.16.
 */
public class RayTracerGeometryController {

    @FXML
    private VBox geometryWindowVbox;
    @FXML
    private ChoiceBox geoChoice;
    @FXML
    private Group materialGroup;
    @FXML
    private ChoiceBox materialChoice;
    @FXML
    private TextField geoName;
    @FXML
    private CheckListView geometryChecklist;
    @FXML
    private AnchorPane anchorPaneGeometry;

    private Stage stage;
    private GridPane g;
    private Spinner exponentSpinner;
    private ObservableList<String> activeGeometryNames;

    private ObservableList<Geometry> geometryNames = FXCollections.observableArrayList(Geometry.values());
    private ObservableList<Material> materialNames = FXCollections.observableArrayList(Material.values());
    private ObservableList<Texture> textureNames = FXCollections.observableArrayList(Texture.values());
    private GeometryContainer geoCont;

    private HashMap<String, Object[]> texMap;

    private RayTracerMainController mainController;

    private Geometry selectedGeometry;
    private Material selectedMaterial;
    private Texture selectedTexture;

    private Scene scene;
    private File file;

    private final String[] textureLabels = new String[] {"Diffuse", "Specular", "Reflection"};

    @FXML
    public void initialization() {

        geoChoice.setItems(geometryNames);
        materialChoice.setItems(materialNames);

        activeGeometryNames = FXCollections.observableArrayList(mainController.selectedNode.getGeometryMap().keySet());

        scene = anchorPaneGeometry.getScene();

        geoChoice.setOnAction((event) -> {
            geoCont = new GeometryContainer();
            materialGroup.setVisible(true);
            selectedGeometry = (Geometry) geoChoice.getSelectionModel().getSelectedItem();
            geoName.setText(selectedGeometry.toString());
        });

        materialChoice.setOnAction((event) -> {
            selectedMaterial = (Material) materialChoice.getSelectionModel().getSelectedItem();
            try {
                g.getChildren().clear();
                g.getRowConstraints().clear();
            } catch (NullPointerException e) {
                System.out.println(e);
            }
            g = new GridPane();
            ColumnConstraints column1 = new ColumnConstraints(200);
            ColumnConstraints column2 = new ColumnConstraints(80);
            g.getColumnConstraints().addAll(column1, column2);

            for (int i = 0; i < 4; i++) {
                g.getRowConstraints().add(new RowConstraints(40));
            }
            texMap = new HashMap<String, Object[]>();

            switch (selectedMaterial) {
                case SINGLECOLOR:
                case LAMBERT:
                    initializeRows(g, 1, false);
                    break;
                case PHONG:
                    initializeRows(g, 2, true);
                    break;
                case REFLECTIVE:
                    initializeRows(g, 3, true);
                    break;
            }
            geometryWindowVbox.getChildren().add(g);
        });

        geometryChecklist.setItems(activeGeometryNames);

        geometryChecklist.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c1 -> {
            System.out.println(geometryChecklist.getCheckModel().getCheckedItems());
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainController(RayTracerMainController controller) {
        this.mainController = controller;
    }

    private void initializeRows(GridPane g, int repeats, boolean needsExponent) {
        int lastRow = 0;
        for (int i = 0; i < repeats; i++) {
            int row = i;
            lastRow = i + 1;
            ChoiceBox textureChoice = new ChoiceBox();
            textureChoice.setPrefWidth(200);
            textureChoice.setItems(textureNames);
            textureChoice.setOnAction((e) -> {
                selectedTexture = (Texture) textureChoice.getSelectionModel().getSelectedItem();
                switch (selectedTexture) {
                    case IMAGE:
                        initializeButton(g, row);
                        break;
                    case INTERPOLATEDIMAGE:
                        initializeButton(g, row);
                        break;
                    case SINGLECOLOR:
                        initializeColorPicker(g, row);
                        break;
                }
            });
            VBox vBox = new VBox();

            Label l;

            if (repeats == 1) {
                l = new Label("Texture");
            } else {
                l = new Label(textureLabels[row]);
            }
            vBox.getChildren().addAll(l, textureChoice);
            g.add(vBox, 0, row);
        }
        if (needsExponent) {
            Label exponentLabel = new Label("Exponent");
            exponentSpinner = new Spinner();
            exponentSpinner.setEditable(true);
            exponentSpinner.setPrefWidth(80);
            exponentSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
            g.add(exponentLabel, 0, lastRow);
            g.add(exponentSpinner, 1, lastRow);
        }
    }

    private void initializeButton(GridPane g, int row) {
        Button openBtn = new Button();
        openBtn.setText("open img");
        openBtn.setPrefWidth(80);
        openBtn.setOnAction((open) -> {
            FileChooser f = new FileChooser();
            final FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            final FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            f.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            file = f.showOpenDialog(stage);
            if (file != null) {
                switch (selectedTexture) {
                    case IMAGE:
                        texMap.put("texture_" + row, new Object[]{Texture.IMAGE, file});
                        break;
                    case INTERPOLATEDIMAGE:
                        texMap.put("texture_" + row, new Object[]{Texture.INTERPOLATEDIMAGE, file});
                        break;
                }
            }
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Label(" "), openBtn);
        g.add(vBox, 1, row);
    }

    private void initializeColorPicker(GridPane g, int row) {
        ColorPicker cPicker = new ColorPicker();
        cPicker.setValue(Color.TURQUOISE);
        String key = "texture_" + row;
        cPicker.setOnAction((picked) -> texMap.put(key, new Object[]{Texture.SINGLECOLOR, cPicker.getValue()}));
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Label(" "), cPicker);
        g.add(vBox, 1, row);
    }

    @FXML
    public void createGeometry() {
        try {
            if (mainController.selectedNode.getGeometryMap().get(geoName.getText()) == null) {
                geoCont.setName(geoName.getText());
                geoCont.addGeometry(selectedGeometry);
                geoCont.addTextures(texMap);
                int exponent = 0;
                try {
                    exponent = ((int) exponentSpinner.getValue());
                } catch (Exception e) {
                    System.out.println(e);
                }
                geoCont.addMaterial(selectedMaterial, exponent);
                try {
                    geoCont.getGeometry();
                } catch (Exception e) {
                    Notifications.create()
                            .position(Pos.TOP_RIGHT)
                            .title("Creation failed")
                            .text("Check your input!")
                            .showError();
                    throw new Exception("Check your input!");
                }
                mainController.selectedNode.addGeometry(geoCont);
                geoCont = new GeometryContainer();
                activeGeometryNames = FXCollections.observableArrayList(mainController.selectedNode.getGeometryMap().keySet());
                geometryChecklist.setItems(activeGeometryNames);
                mainController.addGeometries();
            } else {
                Notifications.create()
                        .position(Pos.TOP_RIGHT)
                        .title("Creation failed")
                        .text("Name of geometry exists!")
                        .showWarning();
                throw new Exception("name of geometry exists");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void deleteGeometry(ActionEvent actionEvent) {
        for (Object name : geometryChecklist.getCheckModel().getCheckedItems()) {
            if (name instanceof String) {
                String n = (String) name;
                int index = activeGeometryNames.indexOf(n);
                if (index >= 0) {
                    activeGeometryNames.remove(index);
                    mainController.selectedNode.removeGeometry(n);
                    mainController.rerender();
                }
            }
        }
    }

    @FXML
    public void changeGeometry() {

    }

    @FXML
    public void closePane(ActionEvent actionEvent) {
        stage = (Stage) geometryWindowVbox.getScene().getWindow();
        stage.close();
    }

}
