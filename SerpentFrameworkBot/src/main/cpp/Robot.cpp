/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

#include "Robot.h"

#include <iostream>
#include <serpentframework/NetworkBinding.h>
#include <frc/Timer.h>
#include <frc/smartdashboard/SmartDashboard.h>
#include <networktables/NetworkTableInstance.h>

void Robot::robotStart() {
  std::vector<serpentframework::Waypoint> points = std::vector<serpentframework::Waypoint>();
  points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 10, 10));
  serpentframework::NetworkBinding::SetWaypoints(points);
  weirdTime = Robot::getRobotTime() + 2;
}

void Robot::disabledUpdate() {
  if(weirdTime < Robot::getRobotTime()) {
    std::vector<serpentframework::Waypoint> points = std::vector<serpentframework::Waypoint>();
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::GRAB_PANEL, 20, 5));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 5, 50));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 2, 21.3));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 0, 0));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 12, 26));
    serpentframework::NetworkBinding::SetWaypoints(points);
    weirdTime = Robot::getRobotTime() + 1;
  }
}

#ifndef RUNNING_FRC_TESTS
int main() { return frc::StartRobot<Robot>(); }
#endif
