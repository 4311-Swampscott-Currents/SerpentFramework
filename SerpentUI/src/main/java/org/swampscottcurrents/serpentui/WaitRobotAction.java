package org.swampscottcurrents.serpentui;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class WaitRobotAction extends RobotAction {

    public double timeToWait = 1;

    public static WaitRobotAction create(double x, double y) {
        WaitRobotAction action = new WaitRobotAction();
        action.xPosition = x;
        action.yPosition = y;

        AnchorPane pane = new AnchorPane();
        pane.setLayoutX(action.xPosition);
        pane.setLayoutY(action.yPosition - 30);
        pane.setStyle("-fx-background-size: 20 20; -fx-background-radius: 9; -fx-background-color: #fc3ddf;");
        pane.setMinSize(20, 20);
        pane.setMaxSize(20, 20);
        pane.setPrefSize(20, 20);
        Label l = new Label(new String(Character.toChars(0x1F559)));
        l.setStyle("-fx-text-fill: #ffffff;");
        l.setLayoutX(2);
        pane.getChildren().add(l);
        
        action.associatedNode = pane;
        return action;
    }

    @Override
    public void setPosition(double x, double y) {
        xPosition = x;
        yPosition = y;
        associatedNode.setLayoutX(x);
        associatedNode.setLayoutY(y - 30);
    }

    @Override
    public String getHumanReadableName() {
        return "Pause for " + timeToWait + " seconds";
    }

    @Override
    public void select() {
        ((AnchorPane)associatedNode).setStyle("-fx-background-size: 20 20; -fx-background-radius: 9; -fx-background-color: #ffff00;");
    }

    @Override
    public void unselect() {
        ((AnchorPane)associatedNode).setStyle("-fx-background-size: 20 20; -fx-background-radius: 9; -fx-background-color: #fc3ddf;");
    }

    @Override
    public String serialize() {
        return "Wait," + timeToWait;
    }
}