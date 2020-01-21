package org.swampscottcurrents.serpentui;

import java.util.ArrayList;
import java.util.logging.Logger;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.shuffleboard.api.data.types.NoneType;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Description(dataTypes = { NoneType.class }, name = "GamePlan")
@ParametrizedController(value = "GamePlanWidget.fxml")
public class GamePlanWidget extends SimpleAnnotatedWidget implements INetworkUpdatable {

    @FXML
    private Pane viewingPane;
    @FXML
    private Pane fieldPane;
    @FXML
    private Pane disabledPane;
    @FXML
    private ImageView fieldImage;
    @FXML
    private VBox robotIcon;
    @FXML
    private TextArea genId;

    @Override
    public Pane getView() {
        return viewingPane;
    }

    @FXML
    public void initialize() {
        onNetworkUpdate();
    }

    @Override
    public void onNetworkUpdate() {
        if(NetworkTableInstance.getDefault().isConnected()) {
            disabledPane.setVisible(false);
            disabledPane.setDisable(true);
        } else {
            disabledPane.setVisible(true);
            disabledPane.setDisable(false);
        }
    }
}