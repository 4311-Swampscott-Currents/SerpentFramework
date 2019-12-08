package org.swampscottcurrents.serpentui;

import java.util.ArrayList;
import java.util.logging.Logger;

import edu.wpi.first.shuffleboard.api.data.types.NoneType;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Description(dataTypes = { NoneType.class }, name = "Path Control Widget")
@ParametrizedController(value = "PathControlWidget.fxml")
public class PathControlWidget extends SimpleAnnotatedWidget implements INetworkUpdatable {

    public static final String NAVIGATION_BUTTON_STYLE = ".pcw-navigationbutton { -fx-border-color: black; -fx-border-width: 3px; -fx-background-color: #00aaff; -fx-background-radius: 50%; -fx-border-radius: 50%; }";
    public static final String GRAB_PANEL_BUTTON_STYLE = ".pcw-navigationbutton { -fx-border-color: black; -fx-border-width: 3px; -fx-background-color: #ffaa00; -fx-background-radius: 50%; -fx-border-radius: 50%; }";
    public static final String PLACE_PANEL_BUTTON_STYLE = ".pcw-navigationbutton { -fx-border-color: black; -fx-border-width: 3px; -fx-background-color: #00ff00; -fx-background-radius: 50%; -fx-border-radius: 50%; }";

    protected ArrayList<Button> movementButtons = new ArrayList<Button>();

    @FXML
    private Pane viewingPane;
    @FXML
    private Pane fieldPane;
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
        reload();
    }

    public void reload() {
        for (Button button : movementButtons) {
            fieldPane.getChildren().remove(button);
        }
        movementButtons.clear();
        for (Waypoint point : NetworkBinding.getAvailableWaypoints()) {
            Button moveButton = new Button();
            moveButton.setPrefSize(18, 18);
            moveButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            moveButton.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            moveButton.setTranslateX((point.zPosition / 54.08333333333) * fieldPane.getPrefWidth() - 9); // FRC field is 54.08333333333 ft deep
            moveButton.setTranslateY((point.xPosition / 26.58333333333) * fieldPane.getPrefHeight() - 9); // FRC field is 26.58333333333 ft wide
            moveButton.setOnAction(event -> Platform.runLater(() -> new Alert(AlertType.INFORMATION, "BEEP").showAndWait()));
            moveButton.getStyleClass().clear();
            if(point.actionType == WaypointType.NAVIGATION) {
                moveButton.setStyle(NAVIGATION_BUTTON_STYLE);
            }
            else if(point.actionType == WaypointType.GRAB_PANEL) {
                moveButton.setStyle(GRAB_PANEL_BUTTON_STYLE);
            }
            else {
                moveButton.setStyle(PLACE_PANEL_BUTTON_STYLE);
            }
            movementButtons.add(moveButton);
            fieldPane.getChildren().add(moveButton);
        }
    }
    
    @FXML
    public void onMovementButtonClicked(ActionEvent event) {
        
    }

    @Override
    public void OnNetworkUpdate() {
        Platform.runLater(this::reload);
    }
}