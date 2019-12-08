#include "WaypointType.h"
#include "Quaternion2D.h"

namespace serpentframework {
    class Waypoint {
        public:
            Waypoint() {}
            Waypoint(char i, WaypointType action, double x, double y);

            char id;
            double xPosition;
            double yPosition;
            WaypointType actionType;

            static double distance(Waypoint, Waypoint);
            static double eulerAngle(Waypoint, Waypoint);
            static Quaternion2D quaternionAngle(Waypoint, Waypoint);
    };
}