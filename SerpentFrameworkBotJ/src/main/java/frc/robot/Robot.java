/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.swampscottcurrents.serpentframework.FastRobot;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Robot extends FastRobot {
    public void robotStart() {
        System.out.println("hoi");
    }

    public void autonomousStart() {
        System.out.println("auto");
    }

    public void teleopStart() {
        System.out.println("tell");
    }

    public void disabledStart() {
        System.out.println("im disabled lol");
    }
}
