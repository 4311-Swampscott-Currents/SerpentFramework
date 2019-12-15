package org.swampscottcurrents.serpentframework;

public enum WaypointType {
    NAVIGATION(0),
    GRAB_PANEL(1),
    PLACE_PANEL(2);

    private final int value;

    WaypointType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}