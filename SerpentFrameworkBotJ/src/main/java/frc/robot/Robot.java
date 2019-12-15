/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import org.swampscottcurrents.serpentframework.*;

public class Robot extends FastRobot {
    public void robotStart() {
        //loadWaypoints();
        
    }

    public double[] theAverageTen = new double[10];
    public double nextSpeakTime;
    public int xPos;

    public void robotUpdate() {
        //loop benchmarking test
        /*if(xPos == 10) {
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
        }*/
    }

    private void loadWaypoints() {
        ArrayList<Waypoint> pts = new ArrayList<Waypoint>();
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 11.364374999998574, 21.728694374473893));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 11.364374999998574, 23.43157010915053));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 11.364374999998574, 25.270675902601297));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 15.285416666664748, 25.20256087321423));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 15.285416666664748, 23.43157010915053));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 15.285416666664748, 21.728694374473893));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 14.0891666666649, 18.527287993281817));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 12.427708333331775, 18.59540302266888));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 1.7943749999997751, 17.84613769941116));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 1.7943749999997751, 20.162048698571386));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 24.85541666666355, 17.778022670024097));
        pts.add(new Waypoint( WaypointType.PLACE_PANEL, 24.921874999996874, 20.162048698571386));    
        pts.add(new Waypoint( WaypointType.GRAB_PANEL, 24.257291666663622, 0.20434508816119648));
        pts.add(new Waypoint( WaypointType.GRAB_PANEL, 2.326041666666375, 0.0681150293870655));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 23.52624999999705, 5.926007556674699));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 4.5191666666661, 5.789777497900567));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 22.064166666663898, 11.715785054575266));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 10.699791666665325, 11.6476700251882));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 4.91791666666605, 11.579554995801134));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 7.709166666665699, 15.938916876573327));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 11.896041666665175, 15.598341729637998));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 14.6208333333315, 15.462111670863868));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 20.136874999997474, 15.053421494541475));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 19.738124999997527, 19.753358522248995));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 20.4691666666641, 24.998215785053034));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 22.5958333333305, 23.159109991602268));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 23.991458333330325, 15.053421494541475));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 17.345624999997824, 21.45623425692563));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 17.41208333333115, 23.159109991602268));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 17.67791666666445, 25.40690596137543));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 6.845208333332475, 20.638853904280847));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 9.17124999999885, 21.388119227538567));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 9.104791666665525, 23.2953400503764));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 8.90541666666555, 25.40690596137543));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 4.851458333332725, 24.65764063811771));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 3.05708333333295, 23.704030226698794));
        pts.add(new Waypoint( WaypointType.NAVIGATION, 2.326041666666375, 14.576616288832016));
        NetworkBinding.setWaypoints((Waypoint[])pts.toArray(new Waypoint[pts.size()]));
    }
}
