/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import org.swampscottcurrents.serpentframework.*;
import org.swampscottcurrents.serpentframework.command.*;

import frc.robot.command.*;

public class Robot extends FastRobot {

    public static Robot instance;

    public void robotStart() {
        instance = this;
        CommandGroup group = new CommandGroup(new Command[] { new TestCommand1() });
        group.queueCommand(new TestCommand1());
        group.queueCommand(new TestCommand2());
        CommandManager.executeCommand(group);
    }

    public void robotUpdate() {
        CommandManager.update();
    }

    /*public double[] theAverageTen = new double[10];
    public double nextSpeakTime;
    public int xPos;

    public void robotUpdate() {
        //loop benchmarking test
        if(xPos == 10) {
            theAverageTen = new double[10];
            xPos = 0;
        }
        theAverageTen[xPos] = getTimeDelta();
        xPos++;
        if(nextSpeakTime < getRobotTime() && theAverageTen[9] != 0) {
            double avg = 0;
            for(double d : theAverageTen) {
                avg += d / 10;
            }
            //System.out.println("The average is " + (avg * 1000) + " ms");
            nextSpeakTime = getRobotTime() + 2;
        }
    }*/
}
