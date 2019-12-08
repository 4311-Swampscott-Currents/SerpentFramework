#include "serpentframework/FastRobot.h"

void serpentframework::FastRobot::RobotInit() {
    robotTimer = new frc::Timer();
    matchTimer = new frc::Timer();
    robotTimer->Start();
}

void serpentframework::FastRobot::Autonomous() {
    matchTimer->Reset();
    matchTimer->Start();
    autonomousStart();
    updateTimeDelta();
    while(IsAutonomous() && !IsDisabled()) {
        robotUpdate();
        autonomousUpdate();
        updateTimeDelta();
    }
    autonomousEnd();
    updateTimeDelta();
}

void serpentframework::FastRobot::OperatorControl() {
    teleopStart();
    updateTimeDelta();
    while(IsOperatorControl() && !IsDisabled()) {
        robotUpdate();
        teleopUpdate();
        updateTimeDelta();
    }
    teleopEnd();
    updateTimeDelta();
}

void serpentframework::FastRobot::Test() {
    testStart();
    updateTimeDelta();
    while(IsTest() && !IsDisabled()) {
        robotUpdate();
        testUpdate();
        updateTimeDelta();
    }
    testEnd();
    updateTimeDelta();
}

void serpentframework::FastRobot::Disabled() {
    disabledStart();
    updateTimeDelta();
    while(IsDisabled()) {
        robotUpdate();
        disabledUpdate();
        updateTimeDelta();
    }
    disabledEnd();
    updateTimeDelta();
}

void serpentframework::FastRobot::updateTimeDelta() {
    timeDelta = robotTimer->Get() - lastUpdateTime;
    lastUpdateTime = robotTimer->Get();
}

double serpentframework::FastRobot::getRobotTime() {
    return robotTimer->Get();
}

double serpentframework::FastRobot::getMatchTime() {
    return matchTimer->Get();
}

double serpentframework::FastRobot::getTimeDelta() {
    return timeDelta;
}