package org.swampscottcurrents.serpentui;

import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;

public class DriveRobotAction extends RobotAction {

    public boolean isMovingForwards = true;

    public static DriveRobotAction create(double x, double y, double xend, double yend) {
        DriveRobotAction action = new DriveRobotAction();
        action.xPosition = xend;
        action.yPosition = yend;

        AnchorPane pane = new AnchorPane();
        pane.setStyle(GamePlanWidget.driveStraightArrowTemplate.getStyle());
        pane.setMinSize(GamePlanWidget.driveStraightArrowTemplate.getMinWidth(), GamePlanWidget.driveStraightArrowTemplate.getMinHeight());
        pane.setMaxSize(GamePlanWidget.driveStraightArrowTemplate.getMaxWidth(), GamePlanWidget.driveStraightArrowTemplate.getMaxHeight());
        pane.setPrefSize(Math.sqrt(Math.pow(x - xend, 2) + Math.pow(y - yend, 2)), GamePlanWidget.driveStraightArrowTemplate.getPrefHeight());
        ImageView pointer = new ImageView(GamePlanWidget.driveStraightArrowTemplateImage.getImage());
        pane.getChildren().add(pointer);
        pointer.setFitWidth(GamePlanWidget.driveStraightArrowTemplateImage.getFitWidth());
        pointer.setFitHeight(GamePlanWidget.driveStraightArrowTemplateImage.getFitHeight());
        pointer.setLayoutX(pane.getPrefWidth() - pointer.getFitWidth() + 1);
        pointer.setLayoutY(-pointer.getFitHeight() / 2 + 2.5);
        pointer.setRotate(GamePlanWidget.driveStraightArrowTemplateImage.getRotate());
        double rottwo = 0;
        if(yend - y > 0) {
            rottwo = (180 / Math.PI) * Math.acos((xend - x) / pane.getPrefWidth());
            pane.getTransforms().add(new Rotate(rottwo, 0, 0));
        }
        else {
            rottwo = -(180 / Math.PI) * Math.acos((xend - x) / pane.getPrefWidth());
            pane.getTransforms().add(new Rotate(rottwo, 0, 0));
        }
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        action.associatedNode = pane;
        return action;
    }
    
    public void setPosition(double x, double y, double xend, double yend) {
        xPosition = xend;
        yPosition = yend;
        AnchorPane pane = (AnchorPane)associatedNode;
        pane.getTransforms().clear();
        pane.setPrefSize(Math.sqrt(Math.pow(x - xend, 2) + Math.pow(y - yend, 2)), GamePlanWidget.driveStraightArrowTemplate.getPrefHeight());
        pane.getChildren().get(0).setLayoutX(pane.getPrefWidth() - ((ImageView)pane.getChildren().get(0)).getFitWidth() + 1);
        double rottwo = 0;
        if(yend - y > 0) {
            rottwo = (180 / Math.PI) * Math.acos((xend - x) / pane.getPrefWidth());
            pane.getTransforms().add(new Rotate(rottwo, 0, 0));
        }
        else {
            rottwo = -(180 / Math.PI) * Math.acos((xend - x) / pane.getPrefWidth());
            pane.getTransforms().add(new Rotate(rottwo, 0, 0));
        }
        pane.setLayoutX(x);
        pane.setLayoutY(y);
    }

    @Override
    public void select() {
        associatedNode.setStyle("-fx-background-color: #ffff00");
        ((ImageView)((AnchorPane)associatedNode).getChildren().get(0)).setImage(new Image(GamePlanWidget.driveStraightArrowTemplateImage.getImage().getUrl().replace("AquaTriangle.png", "YellowTriangle.png")));
    }

    @Override
    public void unselect() {
        if(isMovingForwards) {
            associatedNode.setStyle("-fx-background-color: #00ffff");
            ((ImageView)((AnchorPane)associatedNode).getChildren().get(0)).setImage(new Image(GamePlanWidget.driveStraightArrowTemplateImage.getImage().getUrl()));
        }
        else {
            
            associatedNode.setStyle("-fx-background-color: #ff9b00");
            ((ImageView)((AnchorPane)associatedNode).getChildren().get(0)).setImage(new Image(GamePlanWidget.driveStraightArrowTemplateImage.getImage().getUrl().replace("AquaTriangle.png", "OrangeTriangle.png")));
        }
    }

    @Override
    public String getHumanReadableName() {
        if(isMovingForwards) {
            return "Drive to (" + (double)Math.round(100 * xPosition / GamePlanWidget.pxFieldWidth * GamePlanWidget.fieldWidth) / 100 + ", " + (double)Math.round(100 * yPosition / GamePlanWidget.pxFieldHeight * GamePlanWidget.fieldHeight) / 100 + ")";
        }
        else {
            return "Drive backwards to (" + (double)Math.round(100 * xPosition / GamePlanWidget.pxFieldWidth * GamePlanWidget.fieldWidth) / 100 + ", " + (double)Math.round(100 * yPosition / GamePlanWidget.pxFieldHeight * GamePlanWidget.fieldHeight) / 100 + ")";
        }
    }

    @Override
    public String serialize() {
        if(isMovingForwards) {
            return "DriveRobotForwards," + xPosition / GamePlanWidget.pxFieldWidth * GamePlanWidget.fieldWidth + "," + yPosition / GamePlanWidget.pxFieldHeight * GamePlanWidget.fieldHeight;
        }
        else {
            return "DriveRobotBackwards," + xPosition / GamePlanWidget.pxFieldWidth * GamePlanWidget.fieldWidth + "," + yPosition / GamePlanWidget.pxFieldHeight * GamePlanWidget.fieldHeight;
        }
    }
}