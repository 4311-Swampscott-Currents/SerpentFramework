package org.swampscottcurrents.serpentui;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.shuffleboard.api.data.types.NoneType;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import edu.wpi.first.shuffleboard.app.Shuffleboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Description(dataTypes = { NoneType.class }, name = "Path Control Widget")
@ParametrizedController(value = "PathControlWidget.fxml")
public class PathControlWidget extends SimpleAnnotatedWidget {

    @FXML
    private Pane viewingPane;
    @FXML
    private Button placeRocketBallBTRMB;
    @FXML
    private Button placeRocketBallRTRMB;
    @FXML
    private VBox robotIcon;

    @Override
    public Pane getView() {
        return viewingPane;
    }

    @FXML
    public void initialize() {
        robotIcon.setLayoutX(146);
        robotIcon.setLayoutY(229);
        NetworkTableInstance.getDefault();
    }
    
    @FXML
    public void onMovementButtonClicked(ActionEvent event) {
        NetworkTableInstance.getDefault().getTable("waypoint").getKeys();
        NetworkTableInstance.getDefault().getTable("waypoint").getEntry("bob").getDouble(-69);
        new Alert(AlertType.INFORMATION, String.valueOf(NetworkTableInstance.getDefault().getTable("waypoint").getEntry("bob").getDouble(-69))).show();
    }
}