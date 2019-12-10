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

    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 11.364374999998574, 21.728694374473893));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 11.364374999998574, 23.43157010915053));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 11.364374999998574, 25.270675902601297));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 15.285416666664748, 25.20256087321423));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 15.285416666664748, 23.43157010915053));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 15.285416666664748, 21.728694374473893));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 14.0891666666649, 18.527287993281817));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 12.427708333331775, 18.59540302266888));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 1.7943749999997751, 17.84613769941116));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 1.7943749999997751, 20.162048698571386));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 24.85541666666355, 17.778022670024097));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::PLACE_PANEL, 24.921874999996874, 20.162048698571386));    
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::GRAB_PANEL, 24.257291666663622, 0.20434508816119648));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::GRAB_PANEL, 2.326041666666375, 0.0681150293870655));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 23.52624999999705, 5.926007556674699));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 4.5191666666661, 5.789777497900567));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 22.064166666663898, 11.715785054575266));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 10.699791666665325, 11.6476700251882));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 4.91791666666605, 11.579554995801134));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 7.709166666665699, 15.938916876573327));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 11.896041666665175, 15.598341729637998));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 14.6208333333315, 15.462111670863868));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 20.136874999997474, 15.053421494541475));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 19.738124999997527, 19.753358522248995));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 20.4691666666641, 24.998215785053034));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 22.5958333333305, 23.159109991602268));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 23.991458333330325, 15.053421494541475));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 17.345624999997824, 21.45623425692563));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 17.41208333333115, 23.159109991602268));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 17.67791666666445, 25.40690596137543));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 6.845208333332475, 20.638853904280847));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 9.17124999999885, 21.388119227538567));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 9.104791666665525, 23.2953400503764));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 8.90541666666555, 25.40690596137543));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 4.851458333332725, 24.65764063811771));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 3.05708333333295, 23.704030226698794));
    points.push_back(serpentframework::Waypoint(0, serpentframework::WaypointType::NAVIGATION, 2.326041666666375, 14.576616288832016));


    serpentframework::NetworkBinding::SetWaypoints(points);
    weirdTime = Robot::getRobotTime() + 1;
  }
}

#ifndef RUNNING_FRC_TESTS
int main() { return frc::StartRobot<Robot>(); }
#endif
