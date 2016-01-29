package raytracergui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import raytracergui.helpers.ObjLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jroehl on 13.01.16.
 */
public class RayTracerGeometryController {

    @FXML
    private VBox geometryWindowVbox;
    @FXML
    private ChoiceBox<Geometry> geoChoice;
    @FXML
    private Group materialGroup;
    @FXML
    private ChoiceBox<Material> materialChoice;
    @FXML
    private TextField geoName;
    @FXML
    private CheckListView<String> geometryChecklist;
    @FXML
    private AnchorPane anchorPaneGeometry;

    private Stage stage;
    private GridPane g;
    private Spinner<Integer> exponentSpinner;
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

    private HashMap<String, ArrayList> objectValues;

    private File file;

    private final String[] textureLabels = new String[]{"Diffuse", "Specular", "Reflection"};
    private Spinner<Double> refractionIndexSpinner;

    @FXML
    public void initialization() {

        geoChoice.setItems(geometryNames);
        materialChoice.setItems(materialNames);

        activeGeometryNames = FXCollections.observableArrayList(mainController.selectedNode.getGeometryMap().keySet());

        geoChoice.setOnAction((event) -> {
            geoCont = new GeometryContainer();
            materialGroup.setVisible(true);
            selectedGeometry = geoChoice.getSelectionModel().getSelectedItem();
            geoName.setText(selectedGeometry.toString());
        });

        materialChoice.setOnAction((event) -> {
            selectedMaterial = materialChoice.getSelectionModel().getSelectedItem();
            try {
                g.getChildren().clear();
                g.getRowConstraints().clear();
            } catch (NullPointerException ignored) {
            }
            g = new GridPane();
            ColumnConstraints column1 = new ColumnConstraints(200);
            ColumnConstraints column2 = new ColumnConstraints(80);
            g.getColumnConstraints().addAll(column1, column2);

            for (int i = 0; i < 4; i++) {
                g.getRowConstraints().add(new RowConstraints(40));
            }
            texMap = new HashMap<>();
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
                case REFRACTIVE:
                    initializeRefractionSpinner(g);
                    break;
            }
            geometryWindowVbox.getChildren().add(g);
        });
        geometryChecklist.setItems(activeGeometryNames);
//        geometryChecklist.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c1 -> System.out.println(geometryChecklist.getCheckModel().getCheckedItems()));
    }

    private void initializeRefractionSpinner(GridPane g) {
        refractionIndexSpinner = new Spinner<>();
        refractionIndexSpinner.setEditable(true);
        refractionIndexSpinner.setPrefWidth(80);
        refractionIndexSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0));
        refractionIndexSpinner.getValueFactory().setValue(1.0);
        g.add(new Label("Refraction index"), 0, 0);
        g.add(refractionIndexSpinner, 1, 0);
        initializeObjectLoadBtn(g, 0, 1);
    }

    private void initializeObjectLoadBtn(GridPane g, int column, int row) {
        if (selectedGeometry == Geometry.OBJECTFILE) {

            Button button = new Button("load obj");

            button.setOnAction((buttonEvent) -> {
                File file;
                FileChooser fileChooser = new FileChooser();
                file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    ObjLoader objLoader = new ObjLoader(file);
                    objectValues = objLoader.readFile();
                }
            });
            g.add(button, column, row);
        }
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
            ChoiceBox<Texture> textureChoice = new ChoiceBox<>();
            textureChoice.setPrefWidth(200);
            textureChoice.setItems(textureNames);
            textureChoice.setOnAction((e) -> {
                selectedTexture = textureChoice.getSelectionModel().getSelectedItem();
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
            exponentSpinner = new Spinner<>();
            exponentSpinner.setEditable(true);
            exponentSpinner.setPrefWidth(80);
            exponentSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
            exponentSpinner.getValueFactory().setValue(64);
            g.add(new Label("Exponent"), 0, lastRow);
            g.add(exponentSpinner, 1, lastRow);
        }

        initializeObjectLoadBtn(g, 0, lastRow+1);
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
        cPicker.setEditable(true);
        String key = "texture_" + row;
        cPicker.setOnAction((picked) -> texMap.put(key, new Object[]{Texture.SINGLECOLOR, cPicker.getValue()}));
        cPicker.setValue(Color.WHITE);
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
                geoCont.addLoadedValues(objectValues);
                geoCont.addTextures(texMap);
                int exponent = 0;
                try {
                    exponent = exponentSpinner.getValue();
                } catch (NullPointerException ignored) {
                }
                double refractionIndex = 0.0;
                try {
                    refractionIndex = refractionIndexSpinner.getValue();
                } catch (NullPointerException ignored) {
                }
                geoCont.addMaterial(selectedMaterial, exponent, refractionIndex);
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
                mainController.rerender(false);
            } else {
                Notifications.create()
                        .position(Pos.TOP_RIGHT)
                        .title("Creation failed")
                        .text("Name of geometry exists!")
                        .showWarning();
                throw new Exception("name of geometry exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                    mainController.rerender(false);
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
