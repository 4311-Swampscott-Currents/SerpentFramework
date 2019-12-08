#include <cmath>

namespace serpentframework {
    struct Quaternion2D {
        public:
            double x;
            double y;

            Quaternion2D();
            Quaternion2D(double, double);


            //Takes an axis representing an angle and converts it to a quaternion.
            static Quaternion2D fromAxis(double, double);
            //Takes an angle in degrees to convert to a unit quaternion.
            static Quaternion2D fromEuler(double);
            //Spherically interpolates between two unit quaternions.
            static Quaternion2D slerp(Quaternion2D, Quaternion2D, double);
            //Calculates the dot product, or the cosine of the angle between two unit quaternions.
            static double dot(Quaternion2D, Quaternion2D);

            //Converts a quaternion to an angle in degrees.
            double toEuler();
            //Converts a quaternion to unit length.
            void normalize();

            friend Quaternion2D operator +(Quaternion2D, Quaternion2D);
            friend Quaternion2D operator -(Quaternion2D, Quaternion2D);
            friend Quaternion2D operator *(Quaternion2D, Quaternion2D);
            friend Quaternion2D operator !(Quaternion2D);
    };
}