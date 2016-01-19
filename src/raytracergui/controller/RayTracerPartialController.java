package raytracergui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import org.controlsfx.control.PlusMinusSlider;
import org.controlsfx.control.ToggleSwitch;

/**
 * Created by jroehl on 15.01.16.
 */
public class RayTracerPartialController {

    @FXML
    public ColorPicker colorPicker;
    @FXML
    public ToggleSwitch castShadowSwitch;
    @FXML
    public Label labelPos;
    @FXML
    public Label labelPosX;
    @FXML
    public Label labelPosY;
    @FXML
    public Label labelPosZ;
    @FXML
    public PlusMinusSlider sliderPosX;
    @FXML
    public PlusMinusSlider sliderPosY;
    @FXML
    public PlusMinusSlider sliderPosZ;
    @FXML
    public Label labelDir;
    @FXML
    public Label labelDirX;
    @FXML
    public Label labelDirY;
    @FXML
    public Label labelDirZ;
    @FXML
    public PlusMinusSlider sliderDirX;
    @FXML
    public PlusMinusSlider sliderDirY;
    @FXML
    public PlusMinusSlider sliderDirZ;
    @FXML
    public Spinner angleSpinner;

}
