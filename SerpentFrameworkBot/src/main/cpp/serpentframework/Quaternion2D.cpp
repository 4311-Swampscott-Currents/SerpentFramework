#include "serpentframework/Quaternion2D.h"

serpentframework::Quaternion2D::Quaternion2D() {
    x = 0;
    y = 0;
}

serpentframework::Quaternion2D::Quaternion2D(double xpos, double ypos) {
    x = xpos;
    y = ypos;
}

//Takes an angle in degrees to convert to a unit quaternion.
serpentframework::Quaternion2D serpentframework::Quaternion2D::fromEuler(double angle) {
    angle = angle * (M_PI / 360);
    return Quaternion2D(cos(angle), sin(angle));
}

//Spherically interpolates between two unit quaternions.
serpentframework::Quaternion2D serpentframework::Quaternion2D::slerp(Quaternion2D a, Quaternion2D b, double t) {
    double cosDot = acos(dot(a, b));
    double aCoeff = sin((1 - t) * cosDot) / sin(cosDot);
    double bCoeff = sin(t * cosDot) / sin(cosDot);
    return Quaternion2D((aCoeff * a.x) + (bCoeff * b.x), (aCoeff * a.y) + (bCoeff * b.y));
}

//Calculates the dot product, or the cosine of the angle between two unit quaternions.
double serpentframework::Quaternion2D::dot(Quaternion2D a, Quaternion2D b) {
    return (a.x * b.x) + (a.y * b.y);
}

//Converts a quaternion to an angle in degrees.
double serpentframework::Quaternion2D::toEuler() {
    return atan(y / x) * (180 / M_PI);
}

//Converts a quaternion to unit length.
void serpentframework::Quaternion2D::normalize() {
    double distance = sqrt(pow(x, 2) + pow(y, 2));
    x /= distance;
    y /= distance;
}

serpentframework::Quaternion2D serpentframework::operator+(serpentframework::Quaternion2D a, serpentframework::Quaternion2D b) {
    return a * b;
}

serpentframework::Quaternion2D serpentframework::operator-(serpentframework::Quaternion2D a, serpentframework::Quaternion2D b) {
    return serpentframework::Quaternion2D((a.x * b.x) + (a.y * b.y), (a.y * b.x) - (a.x * b.y));
}

serpentframework::Quaternion2D serpentframework::operator*(serpentframework::Quaternion2D a, serpentframework::Quaternion2D b) {
    return serpentframework::Quaternion2D((a.x * b.x) + (a.y * b.y), (a.y * b.x) - (a.x * b.y));
}

serpentframework::Quaternion2D serpentframework::operator!(serpentframework::Quaternion2D a) {
    a.y = -a.y;
    return a;
}