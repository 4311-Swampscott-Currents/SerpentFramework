#include "Waypoint.h"
#include <vector>

namespace serpentframework {
    class NetworkBinding {
        public:
            //Returns whether the machine that this is running on stores bytes in big endian.
            static bool IsBigEndian();
            //Updates the Waypoint information in NetworkTables such that SerpentUI has access to it.
            static void SetWaypoints(std::vector<Waypoint>);
    };
}