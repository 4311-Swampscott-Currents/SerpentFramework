#include "serpentframework/Waypoint.h"
#include <math.h>
#define M_PI 3.14159265358979323846

serpentframework::Waypoint::Waypoint(char i, WaypointType action, double x, double z) {
    id = i;
    actionType = action;
    xPosition = x;
    zPosition = z;
}

double serpentframework::Waypoint::distance(Waypoint a, Waypoint b) {
    return sqrt(pow(a.xPosition - b.xPosition, 2) + pow(a.zPosition - b.zPosition, 2));
}

serpentframework::Quaternion2D serpentframework::Waypoint::quaternionAngle(Waypoint a, Waypoint b) {
    return serpentframework::Quaternion2D::fromAxis(b.xPosition - a.xPosition, b.zPosition - a.zPosition);
}

double serpentframework::Waypoint::eulerAngle(Waypoint a, Waypoint b) {
   return (-2 * signbit(b.xPosition - a.xPosition) + 1) * asin(b.zPosition - a.zPosition) * (180 / M_PI);
}