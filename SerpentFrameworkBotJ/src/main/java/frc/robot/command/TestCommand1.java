package frc.robot.command;

import org.swampscottcurrents.serpentframework.command.Command;

import frc.robot.Robot;

public class TestCommand1 extends Command {

    private double nextTime = 0;
    private int count;

    public void start() {
        nextTime = Robot.instance.getRobotTime() + 2;
        System.out.println("test1 start");
    }

    public void update() {
        if(nextTime < Robot.instance.getRobotTime()) {
            System.out.println("test1 update");
            nextTime = Robot.instance.getRobotTime() + 2;
            count++;
        }
    }

    public void interrupted() {
        System.out.println("EXCUSE YOU");
    }

    public void end() {
        System.out.println("test1 end");
    }

    public boolean isComplete() {
        return count > 4;
    }
}