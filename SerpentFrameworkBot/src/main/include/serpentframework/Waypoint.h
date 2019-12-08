#include "WaypointType.h"
#include "Quaternion2D.h"
#include <vector>

namespace serpentframework {
    class Waypoint {
        public:
            Waypoint() {}
            //Creates a new Waypoint with the specified ID, waypoint action, and position.
            Waypoint(char i, WaypointType action, double x, double y);

            char id;
            double xPosition;
            double zPosition;
            WaypointType actionType;
            std::vector<Waypoint> connectedPoints[];

            //Calculates the distance between two Waypoints.
            static double distance(Waypoint, Waypoint);
            //Calculates the quaternion angle from a to b, where a is the "centerpoint."  All angles are calculated with 0 degrees pointing TOWARDS the red alliance station wall.
            static double eulerAngle(Waypoint, Waypoint);
            //Calculates the Euler angle from a to b, where a is the "centerpoint."  All angles are calculated with 0 degrees pointing TOWARDS the red alliance station wall.
            static Quaternion2D quaternionAngle(Waypoint, Waypoint);
    };
}