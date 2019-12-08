package org.swampscottcurrents.serpentui;

import java.util.logging.Logger;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.shuffleboard.api.data.types.NoneType;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.util.Time;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.Components;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import edu.wpi.first.shuffleboard.app.DashboardData;
import edu.wpi.first.shuffleboard.app.plugin.PluginLoader;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

@Description(dataTypes = { NoneType.class }, name = "Path Control Widget")
@ParametrizedController(value = "PathControlWidget.fxml")
public class PathControlWidget extends SimpleAnnotatedWidget implements INetworkUpdatable {

    @FXML
    private Pane viewingPane;
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
        Button tab = new Button();
        tab.setLayoutX(147);
        tab.setLayoutY(102);
        tab.setText("BEpis");
        tab.setPrefHeight(27);
        tab.setPrefWidth(69);
        tab.setOnAction(event -> new Alert(AlertType.INFORMATION, "you clicked him :D ").showAndWait());
        viewingPane.getChildren().add(tab);
    }

    public void reload() {

    }
    
    @FXML
    public void onMovementButtonClicked(ActionEvent event) {
        
    }

    @Override
    public void OnNetworkUpdate() {
        Platform.runLater(this::reload);
    }
}