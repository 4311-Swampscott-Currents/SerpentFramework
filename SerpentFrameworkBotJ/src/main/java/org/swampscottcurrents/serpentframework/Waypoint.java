package org.swampscottcurrents.serpentframework;

public class Waypoint {
    
    private static int pointCount;

    public final int id;
    public double xPosition;
    public double yPosition;
    public WaypointType actionType;

    public Waypoint() {
        id = pointCount;
        pointCount++;
    }

    public Waypoint(double x, double y, WaypointType action) {
        id = pointCount;
        xPosition = x;
        yPosition = y;
        actionType = action;
        pointCount++;
    }
}