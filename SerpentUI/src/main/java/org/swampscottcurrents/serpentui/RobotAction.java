package org.swampscottcurrents.serpentui;

import javafx.scene.Node;

public class RobotAction {

    public Node associatedNode;
    public double xPosition, yPosition;

    public String serialize() {
        return "UnknownAction";
    }

    public void select() {

    }

    public void unselect() {

    }

    public String getHumanReadableName() {
        return "Unknown Robot Action";
    }

    public void setPosition(double x, double y) {
        
    }
}