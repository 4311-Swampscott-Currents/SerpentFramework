#include "Waypoint.h"
#include <vector>

namespace serpentframework {
    class NetworkBinding {
        public:
            static bool IsBigEndian();
            static void SetWaypoints(std::vector<Waypoint>);
    };
}