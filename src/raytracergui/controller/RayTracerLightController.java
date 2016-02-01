package raytracergui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PlusMinusSlider;
import raytracergui.container.LightContainer;
import raytracergui.enums.Light;
import raytracergui.helpers.Helper;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by jroehl on 13.01.16.
 */
public class RayTracerLightController {

    @FXML
    private VBox lightsWindowVbox;
    @FXML
    private Group lightsGroup;
    @FXML
    private ChoiceBox<Light> lightChoice;
    @FXML
    private TextField lightName;
    @FXML
    private ListView<String> lightsChecklist;

    private Group g = new Group();
    private RayTracerPartialController partialController = new RayTracerPartialController();

    private Light selectedLight;
    private LightContainer lightContainer;
    private ObservableList<String> activeLightNames = FXCollections.observableArrayList();
    private ObservableList<Light> lightNames = FXCollections.observableArrayList(Light.values());

    private RayTracerMainController mainController;

    public void setMainController(RayTracerMainController controller) {
        this.mainController = controller;
    }

    private TextField[] labels;

    private boolean isNewLight;

    @FXML
    public void initialization() {

        activeLightNames = FXCollections.observableArrayList(mainController.lightMap.keySet());

        lightChoice.setOnAction((event) -> setupLight(true));
        lightChoice.setItems(lightNames);

        lightsChecklist.setItems(activeLightNames);
        lightsChecklist.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lightsWindowVbox.getChildren().add(g);

        lightsChecklist.getSelectionModel().selectedItemProperty().addListener((ChangeListener) -> {
            try {
                String selectedItem = lightsChecklist.getSelectionModel().getSelectedItem();
                this.lightContainer = mainController.lightMap.get(selectedItem);
                selectedLight = this.lightContainer.getType();
                lightChoice.getSelectionModel().select(selectedLight);
                setupLight(false);
            } catch (NullPointerException ignored) {
            }
        });

    }

    private void setupLight(boolean isNewLight) {
        lightsGroup.setVisible(true);
        this.isNewLight = isNewLight;
        if (isNewLight) {
            selectedLight = lightChoice.getSelectionModel().getSelectedItem();
            if (lightsChecklist != null) {
                lightsChecklist.getSelectionModel().clearSelection();
            }
            lightContainer = new LightContainer(selectedLight);
            lightContainer.setName(selectedLight.name());
        }
        lightName.setText(lightContainer.getName());
        lightContainer.setType(selectedLight);
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
        PlusMinusSlider[] plusMinusSliders = new PlusMinusSlider[]{partialController.sliderDirX, partialController.sliderDirY, partialController.sliderDirZ, partialController.sliderPosX, partialController.sliderPosY, partialController.sliderPosZ};
        labels = new TextField[]{partialController.labelDirX, partialController.labelDirY, partialController.labelDirZ, partialController.labelPosX, partialController.labelPosY, partialController.labelPosZ};

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
                String id = l.getId();
                l.setText(String.valueOf(lightContainer.getDoubleValue(id).getValue()));
                l.setOnKeyReleased((keyEvent) -> {
                    try {
                        if (keyEvent.getCode() == KeyCode.ENTER) {
                            lightContainer.getDoubleValue(id).set(Double.parseDouble(l.getText()));
                            mainController.rerender(false);
                            bindLabels();
                        } else if (keyEvent.getCode() == KeyCode.UP) {
                            if (keyEvent.isShiftDown()) {
                                lightContainer.setValue(id, lightContainer.getValue(id) + 1);
                            } else {
                                lightContainer.setValue(id, lightContainer.getValue(id) + 0.1);
                            }
                            mainController.rerender(false);
                            bindLabels();
                        } else if (keyEvent.getCode() == KeyCode.DOWN) {
                            if (keyEvent.isShiftDown()) {
                                lightContainer.setValue(id, lightContainer.getValue(id) - 1);
                            } else {
                                lightContainer.setValue(id, lightContainer.getValue(id) - 0.1);
                            }
                            mainController.rerender(false);
                            bindLabels();
                        }
                    } catch (NumberFormatException e) {
                        Notifications.create()
                                .position(Pos.TOP_RIGHT)
                                .title("Value not accepted")
                                .text(String.valueOf(e))
                                .showError();
                    }
                });
                lightContainer.getDoubleValue(l.getId()).addListener((ChangeListener) -> {
                    try {
                        l.setText(String.valueOf(Helper.round(lightContainer.getDoubleValue(l.getId()).getValue())));
                    } catch (ParseException ignored) {
                    }
                });
            }
        }
    }

    private void changeSliderValues(double value, TextField l) {
        bindLabels();
        String id = l.getId();
        lightContainer.setValue(id, Helper.sliderVal(lightContainer.getValue(id), value));
        mainController.rerender(false);
    }

    private void setupDifControls(boolean angleControl) {
        partialController.colorPicker.setValue(javafx.scene.paint.Color.WHITE);
        partialController.colorPicker.setOnAction((event) -> {
            lightContainer.setColor(partialController.colorPicker.getValue());
            mainController.rerender(false);
        });
        partialController.castShadowSwitch.selectedProperty().addListener((event) -> {
            lightContainer.setCastsShadow(partialController.castShadowSwitch.isSelected());
            mainController.rerender(false);
        });
        if (angleControl) {
            partialController.angleSpinner.setEditable(true);
            partialController.angleSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000.0));
            partialController.angleSpinner.getValueFactory().setValue(lightContainer.getHalfAngle());
            partialController.angleSpinner.valueProperty().addListener((event) -> {
                try {
                    lightContainer.setHalfAngle(partialController.angleSpinner.getValue());
                    mainController.rerender(false);
                } catch (Exception ignored) {
                }
            });
        }
    }

    private void loadPartial(String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        VBox v = null;
        loader.setController(partialController);
        try {
            v = loader.load();
        } catch (IOException ignored) {
        }
        g.getChildren().add(v);
    }

    @FXML
    public void createLight(ActionEvent actionEvent) {
        try {
            if (!isNewLight | mainController.lightMap.get(lightName.getText()) == null) {
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
                mainController.rerender(false);
            } else {
                Notifications.create()
                        .position(Pos.TOP_RIGHT)
                        .title("Creation failed")
                        .text("Name of light exists!")
                        .showWarning();
                throw new Exception("name of light exists");
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    public void deleteLight(ActionEvent actionEvent) {
        mainController.lightMap.remove(lightsChecklist.getSelectionModel().getSelectedItem());
        activeLightNames = FXCollections.observableArrayList(mainController.lightMap.keySet());
        lightsChecklist.setItems(activeLightNames);
        lightContainer = new LightContainer(selectedLight);
        setupSliders();
        mainController.rerender(false);
    }
}
