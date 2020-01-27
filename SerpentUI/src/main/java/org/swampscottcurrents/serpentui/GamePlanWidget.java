package org.swampscottcurrents.serpentui;

import java.util.logging.Logger;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.shuffleboard.api.data.types.NoneType;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

@Description(dataTypes = { NoneType.class }, name = "GamePlan")
@ParametrizedController(value = "GamePlanWidget.fxml")
public class GamePlanWidget extends SimpleAnnotatedWidget {

    @FXML
    private Pane viewingPane;
    @FXML
    private Pane fieldPane;
    @FXML
    private ChoiceBox<String> colorChoiceBox;
    @FXML
    private Label colorLabel;
    @FXML
    private ImageView fieldImage;
    @FXML
    private Pane robotIcon;
    @FXML
    private TextArea genId;

    private boolean movingRobot = false;

    @Override
    public Pane getView() {
        return viewingPane;
    }

    @FXML
    public void initialize() {
        colorChoiceBox.getSelectionModel().selectedItemProperty().addListener(event -> {
            if(colorChoiceBox.getSelectionModel().getSelectedItem() == "RED") { //for some reason getValue() doesn't seem to update until after the event is called, so we invert here
                robotIcon.setStyle("-fx-background-color: #ff0000");
                fieldImage.setRotate(0);
            }
            else {
                robotIcon.setStyle("-fx-background-color: #0000ff");
                fieldImage.setRotate(180);
            }
        });
        colorChoiceBox.getItems().add("RED");
        colorChoiceBox.getItems().add("BLUE");
        colorChoiceBox.setValue("RED");

        robotIcon.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> movingRobot = !movingRobot);
        fieldPane.setOnMouseMoved(event -> {
            if(movingRobot) {
                robotIcon.setLayoutX(clamp(event.getX(), 0, fieldImage.getFitWidth()));
                robotIcon.setLayoutY(clamp(event.getY(), 0, fieldImage.getFitHeight()));
            }
        });


        update();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(33), event -> update()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void update() {
        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        if(NetworkTableInstance.getDefault().isConnected()) {
            colorChoiceBox.setVisible(false);
            colorChoiceBox.setDisable(true);
            colorLabel.setVisible(true);
            colorLabel.setDisable(false);

            if(instance.getTable("FMSInfo").getEntry("IsRedAlliance").getBoolean(false)) {
                colorChoiceBox.setValue("RED");
                colorLabel.setText("RED");
            }
            else {
                colorChoiceBox.setValue("BLUE");
                colorLabel.setText("BLUE");
            }
        } else {
            colorChoiceBox.setVisible(true);
            colorChoiceBox.setDisable(false);
            colorLabel.setVisible(false);
            colorLabel.setDisable(true);
        }
        robotIcon.setRotate(180 + instance.getEntry("robotOrientationY").getDouble(0));
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}