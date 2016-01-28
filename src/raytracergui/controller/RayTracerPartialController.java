package raytracergui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
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
    public TextField labelPosX;
    @FXML
    public TextField labelPosY;
    @FXML
    public TextField labelPosZ;
    @FXML
    public PlusMinusSlider sliderPosX;
    @FXML
    public PlusMinusSlider sliderPosY;
    @FXML
    public PlusMinusSlider sliderPosZ;
    @FXML
    public Label labelDir;
    @FXML
    public TextField labelDirX;
    @FXML
    public TextField labelDirY;
    @FXML
    public TextField labelDirZ;
    @FXML
    public PlusMinusSlider sliderDirX;
    @FXML
    public PlusMinusSlider sliderDirY;
    @FXML
    public PlusMinusSlider sliderDirZ;
    @FXML
    public Spinner angleSpinner;

}
