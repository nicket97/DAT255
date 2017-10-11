package com.example.walling.elizaapp;

/**
 * Created by olofenstrom on 2017-10-09.
 */

public class MessageCreator {
    private String steerString;
    private String ACCString;
    private String platooningString;
    private String completeString;
    private String speedString;

    public void setSteerString(String steerString) {
        this.steerString = steerString;
        System.out.println(getCompleteString());
    }

    public void setACC(boolean state) {
        if (state) {
            ACCString = "ACC:YES";
        } else {
            ACCString = "ACC:NO";
        }
        System.out.println(getCompleteString());
    }

    public void setPlatooning(boolean state) {
        if(state) {
            platooningString = "PLATOONING:YES";
        } else {
            platooningString = "PLATOONING:NO";
        }
        System.out.println(getCompleteString());

    }

    public void setSpeed(double speed) {
        if (speed < 0) {
            speedString = "SPEED:NO";
        } else {
            speedString = "SPEED:" + speed;
        }
        System.out.println(getCompleteString());

    }

    public MessageCreator () {
        this.steerString = "V0000H0000";
        this.ACCString = "ACC:NO";
        this.platooningString = "PLATOONING:NO";
        this.speedString = "SPEED:NO";
        this.completeString = getCompleteString();
    }

    public String getCompleteString() {
        completeString = steerString + " " + ACCString + " "
                + platooningString + " " + speedString;
        return completeString;
    }



}
