package org.swampscottcurrents.serpentui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Logger;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.shuffleboard.api.util.Time;
import edu.wpi.first.shuffleboard.api.widget.Components;
import edu.wpi.first.shuffleboard.api.widget.Widget;

public class NetworkBinding {

    public static long lastUpdateTime;

    private static NetworkTableInstance currentNetworkTableInstance;
    private static Waypoint[] availableWaypoints = new Waypoint[0];

    public static void Initialize() {
        lastUpdateTime = Time.now();
        currentNetworkTableInstance = NetworkTableInstance.getDefault();
        currentNetworkTableInstance.addConnectionListener(notification -> {
            if(!notification.connected) {
                currentNetworkTableInstance.deleteAllEntries();
            }
            sendNetworkUpdate();
        }, true);
        //currentNetworkTableInstance.addEntryListener("waypoints", notification -> onWaypointsUpdated(), EntryListenerFlags.kImmediate | EntryListenerFlags.kDelete | EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }

    public static Waypoint[] getAvailableWaypoints() {
        return availableWaypoints;
    }

    public static void sendNetworkUpdate() {
        for(Widget widget : Components.getDefault().getActiveWidgets()) {
            if(widget instanceof INetworkUpdatable) {
                ((INetworkUpdatable)widget).onNetworkUpdate();
            }
        }
    }

    private static void onWaypointsUpdated() {
        byte[] waypointRepresentation = currentNetworkTableInstance.getEntry("waypoints").getRaw(new byte[0]);
        Waypoint[] loadedWaypoints = new Waypoint[waypointRepresentation.length / 18];
        for(int x = 0; x < loadedWaypoints.length; x++) {
            Waypoint point = new Waypoint();
            point.id = waypointRepresentation[x * 18 + 0];
            point.actionType = WaypointType.fromValue(waypointRepresentation[x * 18 + 1]);
            point.xPosition = ByteBuffer.wrap(waypointRepresentation, x * 18 + 2, 8).getDouble();
            point.zPosition = ByteBuffer.wrap(waypointRepresentation, x * 18 + 10, 8).getDouble();
            loadedWaypoints[x] = point;
        }
        availableWaypoints = loadedWaypoints;
        sendNetworkUpdate();
    }    
}