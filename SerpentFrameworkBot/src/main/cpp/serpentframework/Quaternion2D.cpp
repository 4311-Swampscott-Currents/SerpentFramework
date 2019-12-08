#include <math.h>

#include "serpentframework/Quaternion2D.h"
#define M_PI 3.14159265358979323846

serpentframework::Quaternion2D::Quaternion2D() {
    x = 0;
    y = 0;
}

serpentframework::Quaternion2D::Quaternion2D(double xpos, double ypos) {
    x = xpos;
    y = ypos;
}

serpentframework::Quaternion2D serpentframework::Quaternion2D::fromAxis(double x, double y) {
    double length = sqrt(x*x + y*y);
    x /= length;
    y /= length;
    return serpentframework::Quaternion2D::fromEuler((-2 * signbit(x) + 1) * asin(y) * (180 / M_PI));
}

serpentframework::Quaternion2D serpentframework::Quaternion2D::fromEuler(double angle) {
    angle = angle * (M_PI / 360);
    return Quaternion2D(cos(angle), sin(angle));
}

serpentframework::Quaternion2D serpentframework::Quaternion2D::slerp(Quaternion2D a, Quaternion2D b, double t) {
    double cosDot = acos(dot(a, b));
    double aCoeff = sin((1 - t) * cosDot) / sin(cosDot);
    double bCoeff = sin(t * cosDot) / sin(cosDot);
    return Quaternion2D((aCoeff * a.x) + (bCoeff * b.x), (aCoeff * a.y) + (bCoeff * b.y));
}

double serpentframework::Quaternion2D::dot(Quaternion2D a, Quaternion2D b) {
    return (a.x * b.x) + (a.y * b.y);
}

double serpentframework::Quaternion2D::toEuler() {
    return (-2 * signbit(x) + 1) * asin(y) * (360 / M_PI);
}

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