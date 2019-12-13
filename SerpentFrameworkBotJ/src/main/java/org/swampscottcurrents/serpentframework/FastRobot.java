package org.swampscottcurrents.serpentframework;

import edu.wpi.first.wpilibj.*;

public class FastRobot extends SampleRobot {

    private Timer matchTimer;
    private Timer robotTimer;
    private double timeDelta;
    private double lastUpdateTime;

    /** Called once when the robot initally turns on. */
    public void robotStart() {}
    /** Called every robot update cycle, no matter the mode. */
    public void robotUpdate() {}
    /** Called once at the start of autonomous mode. */
    public void autonomousStart() {}
    /** Called every robot update cycle during autonomous mode. */
    public void autonomousUpdate() {}
    /** Called once at the end of autonomous mode. */
    public void autonomousEnd() {}
    /** Called once at the start of teleoperated mode. */
    public void teleopStart() {}
    /** Called every robot update cycle during teleoperated mode. */
    public void teleopUpdate() {}
    /** Called once at the end of teleoperated mode. */
    public void teleopEnd() {}
    /** Called once at the start of test mode. */
    public void testStart() {}
    /** Called every robot update cycle during test mode. */
    public void testUpdate() {}
    /** Called once at the end of test mode. */
    public void testEnd() {}
    /** Called once when the robot is disabled. */
    public void disabledStart() {}
    /** Called every robot update cycle while the robot is disabled. */
    public void disabledUpdate() {}
    /** Called once when the robot is enabled. */
    public void disabledEnd() {}

    public final void robotInit() {
        robotTimer = new Timer();
        matchTimer = new Timer();
        robotTimer.start();
        robotStart();
    }

    public final void autonomous() {
        matchTimer.reset();
        matchTimer.start();
        autonomousStart();
        updateTimeDelta();
        while(isAutonomous() && !isDisabled()) {
            robotUpdate();
            autonomousUpdate();
            updateTimeDelta();
        }
        autonomousEnd();
        updateTimeDelta();
    }

    public final void operatorControl() {
        teleopStart();
        updateTimeDelta();
        while(isOperatorControl() && !isDisabled()) {
            robotUpdate();
            teleopUpdate();
            updateTimeDelta();
        }
        autonomousEnd();
        updateTimeDelta();
    }

    public final void test() {
        testStart();
        updateTimeDelta();
        while(isTest() && !isDisabled()) {
            robotUpdate();
            testUpdate();
            updateTimeDelta();
        }
        testEnd();
        updateTimeDelta();
    }

    public final void disabled() {
        disabledStart();
        updateTimeDelta();
        while(isDisabled()) {
            robotUpdate();
            disabledUpdate();
            updateTimeDelta();
        }
        disabledEnd();
        updateTimeDelta();
    }

    /** Returns the time in seconds since the robot was turned on. */
    public final double getRobotTime() {
        return robotTimer.get();
    }

    /** Returns the time in seconds since the beginning of the match. */
    public final double getMatchTime() {
        return matchTimer.get();
    }

    /** Returns the time the last robot update cycle took. */
    public final double getTimeDelta() {
        return timeDelta;
    }

    private void updateTimeDelta() {
        timeDelta = robotTimer.get() - lastUpdateTime;
        lastUpdateTime = robotTimer.get();
    }
}