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
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NetworkBinding {

    public static long lastUpdateTime;

    private static NetworkTableInstance currentNetworkTableInstance;
    private static Waypoint[] availableWaypoints;

    public static void Initialize() {
        lastUpdateTime = Time.now();
        currentNetworkTableInstance = NetworkTableInstance.getDefault();
        currentNetworkTableInstance.addEntryListener("waypoints", NetworkBinding::onWaypointsUpdated, EntryListenerFlags.kImmediate | EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }

    public static Waypoint[] getAvailableWaypoints() {
        return availableWaypoints;
    }

    public static void SendNetworkUpdate() {
        for(Widget widget : Components.getDefault().getActiveWidgets()) {
            if(widget instanceof INetworkUpdatable) {
                ((INetworkUpdatable)widget).OnNetworkUpdate();
            }
        }
    }

    private static void onWaypointsUpdated(EntryNotification notification) {
        try {
            byte[] waypointRepresentation = currentNetworkTableInstance.getEntry("waypoints").getRaw(new byte[0]);
            Waypoint[] loadedWaypoints = new Waypoint[waypointRepresentation.length / 18];
            for(int x = 0; x < loadedWaypoints.length; x++) {
                Waypoint point = new Waypoint();
                point.id = waypointRepresentation[x * 18 + 0];
                point.actionType = waypointRepresentation[x * 18 + 1];
                point.xPosition = ByteBuffer.wrap(waypointRepresentation, x * 18 + 2, 8).getDouble();
                point.yPosition = ByteBuffer.wrap(waypointRepresentation, x * 18 + 10, 8).getDouble();
                loadedWaypoints[x] = point;
            }
            availableWaypoints = loadedWaypoints;
            SendNetworkUpdate();
        }
        catch(Exception e) {
            Logger.getAnonymousLogger().severe("THERE WAS AN EXCEPTION\n" + e.toString() + "\n");
            for(StackTraceElement el : e.getStackTrace()) {
                Logger.getAnonymousLogger().severe(el.toString());
            }
        }
    }
}