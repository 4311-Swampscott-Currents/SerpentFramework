package org.swampscottcurrents.serpentui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
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
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

@Description(dataTypes = { NoneType.class }, name = "GamePlan")
@ParametrizedController(value = "GamePlanWidget.fxml")
public class GamePlanWidget extends SimpleAnnotatedWidget implements INetworkUpdatable {

    public static final double fieldWidth = 54.083; //ft
    public static final double fieldHeight = 26.583; //ft
    public static final double robotLength = 2.692; //ft
    public static final double robotGirth = 2.25; //ft
    public static double pxFieldWidth, pxFieldHeight;

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
    private Pane verticalPositionBar;
    @FXML
    private Label verticalPositionBarLabel;
    @FXML
    private Pane horizontalPositionBar;
    @FXML
    private Label horizontalPositionBarLabel;
    @FXML
    private TextArea genId;
    @FXML
    private AnchorPane arrowTemplate;
    @FXML
    private ImageView arrowTemplateImage;
    @FXML
    private VBox robotActionVBox;
    @FXML
    private ScrollPane robotActionScrollPane;
    @FXML
    private Button removeActionButton;
    @FXML
    private Button addActionButton;
    @FXML
    private Button moveActionUpButton;
    @FXML
    private Button moveActionDownButton;
    @FXML
    private Button showHideActionButton;
    @FXML
    private Button editActionButton;
    @FXML
    private Button saveConfigurationButton;
    @FXML
    private Button loadConfigurationButton;
    @FXML
    private AnchorPane setPositionPane;
    @FXML
    private ImageView robotPositionImageView;

    public static ImageView driveStraightArrowTemplateImage;
    public static AnchorPane driveStraightArrowTemplate;

    private boolean movingRobot = false;

    public ArrayList<RobotAction> robotActions = new ArrayList<RobotAction>();
    private RobotAction selectedAction = null;
    private ToggleGroup robotActionToggleGroup = new ToggleGroup();

    @Override
    public Pane getView() {
        return viewingPane;
    }

    public String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @FXML
    public void initialize() {
        for(Window stage : Stage.getWindows()) {
            if(Stage.class.isInstance(stage)) {
                ((Stage)stage).getIcons().add(new Image(arrowTemplateImage.getImage().getUrl().replace("AquaTriangle.png", "Logo.png")));
            }
        }
        robotPositionImageView.setOnMouseClicked(event -> {
           NetworkTableInstance.getDefault().getEntry("guiRobotPositionX").setDouble(event.getX() / pxFieldWidth * fieldWidth); 
           NetworkTableInstance.getDefault().getEntry("guiRobotPositionY").setDouble(event.getY() / pxFieldHeight * fieldHeight); 
           event.consume();
        });
        saveConfigurationButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save strategy");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("GamePlan Strategy files (*.gps)", "*.gps"));
            fileChooser.setInitialFileName("*.gps");
            File dest = fileChooser.showSaveDialog(viewingPane.getScene().getWindow());
            if(dest != null) {
                try {
                    FileWriter writer = new FileWriter(dest); 
                    writer.write(getGamePlanStrategyOutput());
                    writer.flush();
                    writer.close();
                } catch(Exception e) {}
            }
        });
        loadConfigurationButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load strategy");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("GamePlan Strategy files (*.gps)", "*.gps"));
            fileChooser.setInitialFileName("*.gps");
            File dest = fileChooser.showOpenDialog(viewingPane.getScene().getWindow());
            if(dest != null) {
                try {
                    loadGamePlanStrategy(Files.readAllLines(Paths.get(dest.getPath())).get(0));
                    onNetworkUpdate();
                } catch(Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("An unknown error occured while attempting to load the GamePlan strategy.");
                    alert.getDialogPane().expandableContentProperty().set(new TextArea(e.getMessage() + "\n" + stackTraceToString(e)));
                    alert.getDialogPane().setExpanded(true);
                    alert.showAndWait();
                }
            }
        });
        showHideActionButton.setOnMouseClicked(event -> {
            if(selectedAction != null) {
                selectedAction.associatedNode.setVisible(!selectedAction.associatedNode.isVisible());
                selectedAction.associatedNode.setDisable(selectedAction.associatedNode.isDisable());
            }
            reloadRobotActionVBox();
        });
        editActionButton.setOnMouseClicked(event -> {
            if(selectedAction != null) {
                if(DriveRobotAction.class.isInstance(selectedAction)) {
                    Alert alert = new Alert(Alert.AlertType.NONE);
                    alert.setHeaderText("How should the robot drive?");
                    alert.getButtonTypes().addAll(new ButtonType("Forwards"), new ButtonType("Backwards"));
                    alert.showAndWait();
                    if(alert.getResult().getText() == "Forwards") {
                        ((DriveRobotAction)selectedAction).isMovingForwards = true;
                    }
                    else {
                        ((DriveRobotAction)selectedAction).isMovingForwards = false;
                    }
                    reloadRobotActionVBox();
                }
                if(WaitRobotAction.class.isInstance(selectedAction)) {
                    Alert timer = new Alert(Alert.AlertType.NONE);
                    timer.setHeaderText("How long should the robot pause (in milliseconds)?");
                    timer.getButtonTypes().add(ButtonType.OK);
                    timer.getDialogPane().expandableContentProperty().set(new NumericTextField((int)(((WaitRobotAction)selectedAction).timeToWait * 1000)));
                    timer.getDialogPane().setExpanded(true);
                    timer.showAndWait();
                    ((WaitRobotAction)selectedAction).timeToWait = ((NumericTextField)timer.getDialogPane().expandableContentProperty().getValue()).getNumber() / 1000D;
                    reloadRobotActionVBox();
                }
                onNetworkUpdate();
            }
        });
        moveActionUpButton.setOnMouseClicked(event -> {
            if(selectedAction != null && !DriveRobotAction.class.isInstance(selectedAction)) {
                int index = robotActions.lastIndexOf(selectedAction);
                if(index != 0) {
                    robotActions.remove(selectedAction);
                    robotActions.add(index - 1, selectedAction);
                    reloadRobotActionVBox();
                    if(index == 1) {
                        selectedAction.setPosition(robotIcon.getLayoutX(), robotIcon.getLayoutY());
                    }
                    else {
                        selectedAction.setPosition(robotActions.get(index - 2).xPosition, robotActions.get(index - 2).yPosition);
                    }
                    onNetworkUpdate();
                }
            }
        });
        moveActionDownButton.setOnMouseClicked(event -> {
            if(selectedAction != null && !DriveRobotAction.class.isInstance(selectedAction)) {
                int index = robotActions.lastIndexOf(selectedAction);
                if(index != robotActions.size() - 1) {
                    robotActions.remove(selectedAction);
                    robotActions.add(index + 1, selectedAction);
                    reloadRobotActionVBox();
                    selectedAction.setPosition(robotActions.get(index).xPosition, robotActions.get(index).yPosition);
                    onNetworkUpdate();
                }
            }
        });
        addActionButton.setOnMouseClicked(event -> newActionButton());
        removeActionButton.setOnMouseClicked(event -> {
            if(selectedAction != null) {
                if(DriveRobotAction.class.isInstance(selectedAction)) {
                    int q = robotActions.size();
                    ArrayList<RobotAction> toRem = new ArrayList<RobotAction>();
                    for(int x = robotActions.lastIndexOf(selectedAction); x < q; x++) {
                        RobotAction y = robotActions.get(x);
                        if(DriveRobotAction.class.isInstance(y)) {
                            toRem.add(y);
                            fieldPane.getChildren().remove(y.associatedNode);
                        }
                        else {
                            y.setPosition(robotIcon.getLayoutX(), robotIcon.getLayoutY());
                        }
                    }
                    for(RobotAction act : toRem) {
                        robotActions.remove(act);
                    }
                    selectItem(null);
                }
                else {
                    robotActions.remove(selectedAction);
                    fieldPane.getChildren().remove(selectedAction.associatedNode);
                    selectItemQuiet(null);
                }
                onNetworkUpdate();
                reloadRobotActionVBox();
            }
        });
        pxFieldWidth = fieldPane.getPrefWidth();
        pxFieldHeight = fieldPane.getPrefHeight();
        driveStraightArrowTemplate = arrowTemplate;
        driveStraightArrowTemplateImage = arrowTemplateImage;
        colorChoiceBox.getItems().add("RED");
        colorChoiceBox.getItems().add("BLUE");
        colorChoiceBox.getSelectionModel().selectedItemProperty().addListener(event -> {
            if(colorChoiceBox.getSelectionModel().getSelectedItem() == "RED") {
                robotIcon.setStyle("-fx-background-color: #ff0000");
                fieldImage.setRotate(0);
                robotPositionImageView.setRotate(0);
            }
            else {
                robotIcon.setStyle("-fx-background-color: #0000ff");
                fieldImage.setRotate(180);
                robotPositionImageView.setRotate(180);
            }
        });
        colorChoiceBox.setValue("RED");
        robotIcon.setOnMouseClicked(event -> {
            movingRobot = !movingRobot;
            event.consume();
        });
        fieldPane.setOnMouseMoved(event -> {
            if(movingRobot) {
                robotIcon.setLayoutX(clamp(event.getX(), 0, fieldImage.getFitWidth()));
                robotIcon.setLayoutY(clamp(event.getY(), 0, fieldImage.getFitHeight()));
                drawPositionBars();
                for(RobotAction act : robotActions) {
                    if(DriveRobotAction.class.isInstance(act)) {
                        ((DriveRobotAction)act).setPosition(event.getX(), event.getY(), act.xPosition, act.yPosition);
                        break;
                    }
                    else {
                        act.setPosition(event.getX(), event.getY());
                    }
                }
                onNetworkUpdate();
                robotIcon.toFront();
            }
        });
        fieldPane.setOnMouseClicked(event -> {
            final DriveRobotAction act;
            if(robotActions.isEmpty()) {
                act = DriveRobotAction.create(robotIcon.getLayoutX(), robotIcon.getLayoutY(), event.getX(), event.getY());
            }
            else {
                RobotAction lastAct = robotActions.get(robotActions.size() - 1);
                act = DriveRobotAction.create(lastAct.xPosition, lastAct.yPosition, event.getX(), event.getY());
            }
            act.associatedNode.setOnMouseClicked(ev -> { if(selectedAction == act) { selectItem(null); } else { selectItem(act); } ev.consume(); });
            fieldPane.getChildren().add(act.associatedNode);
            robotActions.add(act);
            reloadRobotActionVBox();
            onNetworkUpdate();
            robotIcon.toFront();
            event.consume();
        });


        update();
        drawPositionBars();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(33), event -> update()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    

    public void loadGamePlanStrategy(String input) {
        for(RobotAction act : robotActions) {
            fieldPane.getChildren().remove(act.associatedNode);
        }
        robotActions.clear();
        String[] items = input.split("\\|");
        for(int x = 0; x < items.length; x++) {
            if(!items[x].equals("")) {
                processGamePlanStrategyElement(items[x]);
            }
        }
        reloadRobotActionVBox();
        drawPositionBars();
        robotIcon.toFront();
    }

    private void processGamePlanStrategyElement(String element) {
        String[] elementParameters = element.split(",");
        if(elementParameters[0].equals("RobotPosition")) {
            robotIcon.setLayoutX(Double.parseDouble(elementParameters[1]) / fieldWidth * pxFieldWidth);
            robotIcon.setLayoutY(Double.parseDouble(elementParameters[2]) / fieldHeight * pxFieldHeight);
        }
        else if(elementParameters[0].startsWith("DriveRobot")) {
            final DriveRobotAction act;
            if(robotActions.isEmpty()) {
                act = DriveRobotAction.create(robotIcon.getLayoutX(), robotIcon.getLayoutY(), Double.parseDouble(elementParameters[1]) / fieldWidth * pxFieldWidth, Double.parseDouble(elementParameters[2]) / fieldHeight * pxFieldHeight);
            }
            else {
                RobotAction lastAct = robotActions.get(robotActions.size() - 1);
                act = DriveRobotAction.create(lastAct.xPosition, lastAct.yPosition, Double.parseDouble(elementParameters[1]) / fieldWidth * pxFieldWidth, Double.parseDouble(elementParameters[2]) / fieldHeight * pxFieldHeight);
            }
            if(elementParameters[0].equals("DriveRobotBackwards")) {
                act.isMovingForwards = false;
                act.unselect();
            }
            act.associatedNode.setOnMouseClicked(ev -> { if(selectedAction == act) { selectItem(null); } else { selectItem(act); } ev.consume(); });
            fieldPane.getChildren().add(act.associatedNode);
            robotActions.add(act);
        }
        else if(elementParameters[0].equals("Wait")) {
            double xpos, ypos;
            if(robotActions.isEmpty()) {
                xpos = robotIcon.getLayoutX();
                ypos = robotIcon.getLayoutY();
            }
            else {
                xpos = robotActions.get(robotActions.size() - 1).xPosition;
                ypos = robotActions.get(robotActions.size() - 1).yPosition;
            }
            WaitRobotAction act = WaitRobotAction.create(xpos, ypos);
            act.timeToWait = Double.parseDouble(elementParameters[1]);
            robotActions.add(act);
            act.associatedNode.setOnMouseClicked(ev -> {
                if(selectedAction == act) {
                    selectItem(null);
                }
                else {
                    selectItem(act);
                }
                ev.consume();
            });
            fieldPane.getChildren().add(act.associatedNode);
        }
        else if(elementParameters[0].equals("FireBalls")) {
            double xpos, ypos;
            if(robotActions.isEmpty()) {
                xpos = robotIcon.getLayoutX();
                ypos = robotIcon.getLayoutY();
            }
            else {
                xpos = robotActions.get(robotActions.size() - 1).xPosition;
                ypos = robotActions.get(robotActions.size() - 1).yPosition;
            }
            FireRobotAction act = FireRobotAction.create(xpos, ypos);
            robotActions.add(act);
            act.associatedNode.setOnMouseClicked(ev -> {
                if(selectedAction == act) {
                    selectItem(null);
                }
                else {
                    selectItem(act);
                }
                ev.consume();
            });
            fieldPane.getChildren().add(act.associatedNode);
        }
    }

    public String getGamePlanStrategyOutput() {
        String outputString = "|RobotPosition," + robotIcon.getLayoutX() / pxFieldWidth * fieldWidth + "," + robotIcon.getLayoutY() / pxFieldHeight * fieldHeight + "|";
        for(RobotAction act : robotActions) {
            outputString += act.serialize() + "|";
        }
        return outputString;
    }

    public void newActionButton() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText("What action would you like to add?");
        alert.getButtonTypes().addAll(new ButtonType("Fire"), new ButtonType("Pause"), ButtonType.CANCEL);
        alert.showAndWait();
        if(alert.getResult().getText() == "Fire") {
            double xpos, ypos;
            int insertionIndex;
            if(robotActions.isEmpty()) {
                xpos = robotIcon.getLayoutX();
                ypos = robotIcon.getLayoutY();
                insertionIndex = 0;
            }
            else {
                if(selectedAction == null) {
                    insertionIndex = robotActions.size();
                    xpos = robotActions.get(robotActions.size() - 1).xPosition;
                    ypos = robotActions.get(robotActions.size() - 1).yPosition;
                }
                else {
                    insertionIndex = robotActions.lastIndexOf(selectedAction) + 1;
                    xpos = robotActions.get(robotActions.lastIndexOf(selectedAction)).xPosition;
                    ypos = robotActions.get(robotActions.lastIndexOf(selectedAction)).yPosition;
                }
            }
            FireRobotAction act = FireRobotAction.create(xpos, ypos);
            robotActions.add(insertionIndex, act);
            act.associatedNode.setOnMouseClicked(ev -> {
                if(selectedAction == act) {
                    selectItem(null);
                }
                else {
                    selectItem(act);
                }
                ev.consume();
            });
            fieldPane.getChildren().add(act.associatedNode);
            reloadRobotActionVBox();
        }
        if(alert.getResult().getText() == "Pause") {

            Alert timer = new Alert(Alert.AlertType.NONE);
            timer.setHeaderText("How long should the robot pause (in milliseconds)?");
            timer.getButtonTypes().add(ButtonType.OK);
            timer.getDialogPane().expandableContentProperty().set(new NumericTextField(1000));
            timer.getDialogPane().setExpanded(true);
            timer.showAndWait();

            double xpos, ypos;
            int insertionIndex;
            if(robotActions.isEmpty()) {
                xpos = robotIcon.getLayoutX();
                ypos = robotIcon.getLayoutY();
                insertionIndex = 0;
            }
            else {
                if(selectedAction == null) {
                    insertionIndex = robotActions.size();
                    xpos = robotActions.get(robotActions.size() - 1).xPosition;
                    ypos = robotActions.get(robotActions.size() - 1).yPosition;
                }
                else {
                    insertionIndex = robotActions.lastIndexOf(selectedAction) + 1;
                    xpos = robotActions.get(robotActions.lastIndexOf(selectedAction)).xPosition;
                    ypos = robotActions.get(robotActions.lastIndexOf(selectedAction)).yPosition;
                }
            }
            WaitRobotAction act = WaitRobotAction.create(xpos, ypos);
            act.timeToWait = ((NumericTextField)timer.getDialogPane().expandableContentProperty().getValue()).getNumber() / 1000D;
            robotActions.add(insertionIndex, act);
            act.associatedNode.setOnMouseClicked(ev -> {
                if(selectedAction == act) {
                    selectItem(null);
                }
                else {
                    selectItem(act);
                }
                ev.consume();
            });
            fieldPane.getChildren().add(act.associatedNode);
            reloadRobotActionVBox();
        }
        onNetworkUpdate();
    }

    public void reloadRobotActionVBox() {
        robotActionVBox.getChildren().clear();
        for(RobotAction act : robotActions) {
            RadioButton rad = new RadioButton();
            rad.setToggleGroup(robotActionToggleGroup);
            if(act.associatedNode.isVisible()) {
                rad.setText(act.getHumanReadableName());
            }
            else {
                rad.setText(new String(Character.toChars(0x03F4)) + " " + act.getHumanReadableName());
            }
            rad.setOnMouseClicked(ev -> {
                if(act == selectedAction) {
                    rad.setSelected(false);
                    selectItemQuiet(null);
                }
                else {
                    selectItemQuiet(act);
                }
            });
            robotActionVBox.getChildren().add(rad);
            if(act == selectedAction) {
                rad.setSelected(true);
            }

        }
    }

    public void selectItem(RobotAction toSelect) {
        if(selectedAction != null) {
            selectedAction.unselect();
        }
        if(toSelect == null) {
            for(int x = 0; x < robotActions.size(); x++) {
                if(robotActions.get(x) == selectedAction) {
                    ((RadioButton)robotActionVBox.getChildren().get(x)).setSelected(false);
                }
            }
            selectedAction = null;
        }
        else {
            selectedAction = toSelect;
            toSelect.select();
            for(int x = 0; x < robotActions.size(); x++) {
                if(robotActions.get(x) == toSelect) {
                    ((RadioButton)robotActionVBox.getChildren().get(x)).setSelected(true);
                }
            }
        }
    }

    public void selectItemQuiet(RobotAction toSelect) {
        if(selectedAction != null) {
            selectedAction.unselect();
        }
        if(toSelect == null) {
            selectedAction = null;
        }
        else {
            selectedAction = toSelect;
            toSelect.select();
        }
    }

    private void drawPositionBars() {
        Rotate rot = new Rotate(robotIcon.getRotate(), robotIcon.getLayoutX(), robotIcon.getLayoutY());
        Point2D closestPoint = rot.transform(robotIcon.getPrefWidth() / 2 + robotIcon.getLayoutX(), robotIcon.getPrefHeight() / 2 + robotIcon.getLayoutY());
        Point2D pointInConsideration = rot.transform(-robotIcon.getPrefWidth() / 2 + robotIcon.getLayoutX(), robotIcon.getPrefHeight() / 2 + robotIcon.getLayoutY());
        if(pointInConsideration.distance(0, 0) < closestPoint.distance(0, 0)) {
        closestPoint = pointInConsideration;
        }
        pointInConsideration = rot.transform(robotIcon.getPrefWidth() / 2 + robotIcon.getLayoutX(), -robotIcon.getPrefHeight() / 2 + robotIcon.getLayoutY());
        if(pointInConsideration.distance(0, 0) < closestPoint.distance(0, 0)) {
            closestPoint = pointInConsideration;
        }
        pointInConsideration = rot.transform(robotIcon.getPrefWidth() / 2 + robotIcon.getLayoutX(), robotIcon.getPrefHeight() / 2 + robotIcon.getLayoutY());
        if(pointInConsideration.distance(0, 0) < closestPoint.distance(0, 0)) {
            closestPoint = pointInConsideration;
        }
        horizontalPositionBar.setLayoutY(closestPoint.getY());
        horizontalPositionBar.setPrefWidth(closestPoint.getX());
        double pos = closestPoint.getX() / fieldPane.getPrefWidth() * fieldWidth;
        horizontalPositionBarLabel.setText((int)pos + "'" + (int)(12 * (pos - (int)pos)) + "\"");
        verticalPositionBar.setLayoutX(closestPoint.getX());
        verticalPositionBar.setPrefHeight(closestPoint.getY());
        pos = closestPoint.getY() / fieldPane.getPrefHeight() * fieldHeight;
        double tpos = fieldHeight - pos;
        verticalPositionBarLabel.setText((int)pos + "'" + (int)(12 * (pos - (int)pos)) + "\" (opposite " + (int)tpos + "'" + (int)(12 * (tpos - (int)tpos)) + "\")");
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

            if(instance.getEntry("isManualMode").exists()) {
                setPositionPane.setVisible(true);
                setPositionPane.setDisable(false);
            }
            else {
                setPositionPane.setVisible(false);
                setPositionPane.setDisable(true);
            }
        } else {
            colorChoiceBox.setVisible(true);
            colorChoiceBox.setDisable(false);
            colorLabel.setVisible(false);
            colorLabel.setDisable(true);
            setPositionPane.setVisible(false);
            setPositionPane.setDisable(true);
        }
        robotIcon.setRotate(180 + instance.getEntry("robotOrientationY").getDouble(0));
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    @Override
    public void onNetworkUpdate() {
        if(NetworkTableInstance.getDefault().isConnected()) {
            NetworkTableInstance.getDefault().getEntry("gamePlanStrategy").setString(getGamePlanStrategyOutput());
        }
    }
}