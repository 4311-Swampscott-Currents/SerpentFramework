#include <cmath>

namespace serpentframework {
    struct Quaternion2D {
        public:
            double x;
            double y;

            Quaternion2D();
            Quaternion2D(double, double);

            static Quaternion2D fromEuler(double);
            static Quaternion2D slerp(Quaternion2D, Quaternion2D, double);
            static double dot(Quaternion2D, Quaternion2D);

            double toEuler();
            void normalize();

            friend Quaternion2D operator +(Quaternion2D, Quaternion2D);
            friend Quaternion2D operator -(Quaternion2D, Quaternion2D);
            friend Quaternion2D operator *(Quaternion2D, Quaternion2D);
            friend Quaternion2D operator !(Quaternion2D);
    };
}