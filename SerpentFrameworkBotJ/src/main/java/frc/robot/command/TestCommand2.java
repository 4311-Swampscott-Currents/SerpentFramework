package frc.robot.command;

import org.swampscottcurrents.serpentframework.command.Command;

import frc.robot.Robot;

public class TestCommand2 extends Command {

    private double nextTime = 0;
    private int count;

    public void start() {
        nextTime = Robot.instance.getRobotTime() + 1;
        System.out.println("test2 start");
    }

    public void update() {
        if(nextTime < Robot.instance.getRobotTime()) {
            System.out.println("test2 update");
            nextTime = Robot.instance.getRobotTime() + 1;
            count++;
        }
    }

    public void end() {
        System.out.println("test2 end");
    }

    public boolean isComplete() {
        return count > 9;
    }
}