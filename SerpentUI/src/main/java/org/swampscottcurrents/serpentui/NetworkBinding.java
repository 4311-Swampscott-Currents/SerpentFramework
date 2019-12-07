package org.swampscottcurrents.serpentui;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkBinding {

    private static NetworkTableInstance currentNetworkTableInstance;
    private static Waypoint[] availableWaypoints;

    public static void Initialize() {
        currentNetworkTableInstance = NetworkTableInstance.getDefault();
        currentNetworkTableInstance.addEntryListener("waypoints", notification -> { onWaypointsUpdated(); }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }

    public static Waypoint[] getAvailableWaypoints() {
        return availableWaypoints;
    }

    private static void onWaypointsUpdated() {
        
    }
}