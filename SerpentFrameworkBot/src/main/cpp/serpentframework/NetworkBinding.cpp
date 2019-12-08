#include <networktables/NetworkTableInstance.h>
#include <iostream>
#include "serpentframework/NetworkBinding.h"

bool serpentframework::NetworkBinding::IsBigEndian() {
    unsigned int i = 1;  
    char *c = (char*)&i;  
    if (*c) {
        return false;
    }
    else {
        return true;
    }
}

void serpentframework::NetworkBinding::SetWaypoints(std::vector<Waypoint> points) {
    char* charList = new char[points.size() * 18];
    for (int x = 0; x < points.size(); x++)
    {
        *(charList + (x * 18) + 0) = points[x].id;
        *(charList + (x * 18) + 1) = (char)points[x].actionType;
        if(IsBigEndian()) {
            char* positionVal = (char*)&points[x].xPosition;
            for(int y = 0; y < 8; y++) {
                *(charList + (x * 18) + y + 2) = *(positionVal + y);
            }
            positionVal = (char*)&points[x].zPosition;
            for(int y = 0; y < 8; y++) {
                *(charList + (x * 18) + y + 10) = *(positionVal + y);
            }
        }
        else {
            char* positionVal = (char*)&points[x].xPosition;
            for(int y = 0; y < 8; y++) {
                *(charList + (x * 18) - y + 9) = *(positionVal + y);
            }
            positionVal = (char*)&points[x].zPosition;
            for(int y = 0; y < 8; y++) {
                *(charList + (x * 18) - y + 17) = *(positionVal + y);
            }
        }
    }
    nt::NetworkTableInstance::GetDefault().GetEntry("waypoints").SetRaw(wpi::StringRef(charList, points.size() * 18));
    delete[] charList;
}