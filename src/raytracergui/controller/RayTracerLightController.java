package raytracergui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PlusMinusSlider;
import raytracergui.container.LightContainer;
import raytracergui.enums.Light;
import raytracergui.helpers.Helper;

import java.io.IOException;

/**
 * Created by jroehl on 13.01.16.
 */
public class RayTracerLightController {

    @FXML
    private VBox lightsWindowVbox;
    @FXML
    private Group lightsGroup;
    @FXML
    private ChoiceBox lightChoice;
    @FXML
    private TextField lightName;
    @FXML
    private CheckListView lightsChecklist;

    private Group g = new Group();
    private RayTracerPartialController pC = new RayTracerPartialController();

    private Light selectedLight;
    private LightContainer lightContainer;
    private ObservableList<Object> activeLightNames = FXCollections.observableArrayList();
    private ObservableList<Enum> lightNames = FXCollections.observableArrayList(Light.values());

    private RayTracerMainController mainController;

    public void setMainController(RayTracerMainController controller) {
        this.mainController = controller;
    }

    private PlusMinusSlider[] plusMinusSliders;
    private TextField[] labels;

    @FXML
    public void initialization() {

        activeLightNames = FXCollections.observableArrayList(mainController.lightMap.keySet());


        lightChoice.setOnAction((event) -> {
            lightsGroup.setVisible(true);
            selectedLight = (Light) lightChoice.getSelectionModel().getSelectedItem();
            lightContainer = new LightContainer(selectedLight);
            lightName.setText(selectedLight.name());
            switch (selectedLight) {
                case DIRECTIONAL:
                    setupDirectionalControls();
                    break;
                case POINT:
                    setupPointControls();
                    break;
                case SPOT:
                    setupSpotControls();
                    break;
            }
        });

        lightChoice.setItems(lightNames);

        lightsChecklist.setItems(activeLightNames);

        lightsWindowVbox.getChildren().add(g);
    }

    private void setupSpotControls() {
        g.getChildren().clear();
        loadPartial("../layouts/partials/spotLightControls.fxml");
        setupSliders();
        setupDifControls(true);
    }

    private void setupPointControls() {
        g.getChildren().clear();
        loadPartial("../layouts/partials/pointLightControls.fxml");
        setupSliders();
        setupDifControls(false);
    }

    private void setupDirectionalControls() {
        g.getChildren().clear();
        loadPartial("../layouts/partials/directionalLightControls.fxml");
        setupSliders();
        setupDifControls(false);
    }

    private void setupSliders() {
        plusMinusSliders = new PlusMinusSlider[]{pC.sliderDirX, pC.sliderDirY, pC.sliderDirZ, pC.sliderPosX, pC.sliderPosY, pC.sliderPosZ};
        labels = new TextField[]{pC.labelDirX, pC.labelDirY, pC.labelDirZ, pC.labelPosX, pC.labelPosY, pC.labelPosZ};

        bindLabels();

        for (int i = 0; i < plusMinusSliders.length; i++) {
            if (plusMinusSliders[i] != null) {
                TextField l = labels[i];
                PlusMinusSlider p = plusMinusSliders[i];
                p.setOnValueChanged(e -> changeSliderValues(e.getValue(), l));
            }
        }
    }

    private void bindLabels() {
        for (TextField l : labels) {
            if (l != null) {
                l.setText(String.valueOf(lightContainer.getDoubleValue(l.getId()).getValue()));
                l.setOnKeyReleased((keyEvent) -> {
                    try {
                        lightContainer.getDoubleValue(l.getId()).set(Double.parseDouble(l.getText()));
                        mainController.rerender(false);
                    } catch (NumberFormatException e) {
                        Notifications.create()
                                .position(Pos.TOP_RIGHT)
                                .title("Value not accepted")
                                .text(String.valueOf(e))
                                .showError();
                    }
                });
                lightContainer.getDoubleValue(l.getId()).addListener((ChangeListener) -> {
                    l.setText(String.valueOf(lightContainer.getDoubleValue(l.getId()).getValue()));
                });
            }
        }
    }

    private void changeSliderValues(double value, TextField l) {
        bindLabels();
        String id = l.getId();
        lightContainer.setValue(id, Helper.sliderVal(lightContainer.getValue(id), value));
    }

    private void setupDifControls(boolean angleControl) {
        pC.colorPicker.setValue(javafx.scene.paint.Color.WHITE);
        pC.colorPicker.setOnAction((event) -> {
            lightContainer.setColor(pC.colorPicker.getValue());
        });
        pC.castShadowSwitch.selectedProperty().addListener((event) -> {
            lightContainer.setCastsShadow(pC.castShadowSwitch.isSelected());
        });
        if (angleControl) {
            pC.angleSpinner.setEditable(true);
            pC.angleSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000.0));
            pC.angleSpinner.getValueFactory().setValue(0.0);
            pC.angleSpinner.valueProperty().addListener((event) -> {
                try {
                    lightContainer.setHalfAngle((double) pC.angleSpinner.getValue());
                } catch (Exception e) {
                    System.out.println("FOO");
                    System.out.println(e);
                }
            });
        }
    }

    private void loadPartial(String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        VBox v = null;
        loader.setController(pC);
        try {
            v = loader.load();
        } catch (IOException e) {
            System.out.println(e);
        }
        g.getChildren().add(v);
    }

    @FXML
    public void createLight(ActionEvent actionEvent) {
        try {
            if (mainController.lightMap.get(lightName.getText()) == null) {
                lightContainer.setName(lightName.getText());
                try {
                    lightContainer.getLight();
                } catch (Exception e) {
                    Notifications.create()
                            .position(Pos.TOP_RIGHT)
                            .title("Creation failed")
                            .text("Check your input!")
                            .showError();
                    throw new Exception("Check your input!");
                }
                mainController.lightMap.put(lightName.getText(), lightContainer);
                lightContainer = new LightContainer(selectedLight);
                activeLightNames = FXCollections.observableArrayList(mainController.lightMap.keySet());
                lightsChecklist.setItems(activeLightNames);
            } else {
                Notifications.create()
                        .position(Pos.TOP_RIGHT)
                        .title("Creation failed")
                        .text("Name of light exists!")
                        .showWarning();
                throw new Exception("name of light exists");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void deleteLight(ActionEvent actionEvent) {
        for (Object name : lightsChecklist.getCheckModel().getCheckedItems()) {
            if (name instanceof String) {
                String n = (String) name;
                int index = activeLightNames.indexOf(n);
                if (index >= 0) {
                    activeLightNames.remove(index);
                    mainController.lightMap.remove(n);
                    mainController.rerender(false);
                }
            }
        }
    }
}
