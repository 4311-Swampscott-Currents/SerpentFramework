package org.swampscottcurrents.serpentframework;

public class Waypoint {
    
    private static byte pointCount;

    public final byte id;
    public double xPosition;
    public double yPosition;
    public WaypointType actionType;

    public Waypoint() {
        id = pointCount;
        actionType = WaypointType.NAVIGATION;
        pointCount++;
    }

    public Waypoint(WaypointType action, double x, double y) {
        id = pointCount;
        xPosition = x;
        yPosition = y;
        actionType = action;
        pointCount++;
    }
}