package com.example.walling.elizaapp;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by walling on 2017-09-13.
 *
 * Singleton class for our Model
 */

public class DrivingModel {
    //TODO add helperclass for translation (ex: "set speed to 50 -> translates to V0050H0000")
    /*
    private static DrivingModel instance;
    private boolean isCruiseControlActive = false;

    private DrivingModel(){
    }

    public static DrivingModel getInstance(){
        if(instance == null){
            instance = new DrivingModel();
        }
        return instance;
    }

    public void stop(){
        SteeringHelper.getInstance().setVelocity(0);
        setCruiseControlState(false);
        sendSteeringCommand();
    }

    public void setCruiseControlState(boolean state){
        this.isCruiseControlActive = state;
    }

    //If in reverse, go slower and slower until you go forward again
    public void increaseForwardSpeed(){
        if(!isCruiseControlActive) {
            SteeringHelper.getInstance().changeVelocity(5);
            sendSteeringCommand();
        }
    }
    //Decrease enough and you go into revers
    public void decreaseForwardSpeed(){
        if(!isCruiseControlActive) {
            SteeringHelper.getInstance().changeVelocity(-5);
            sendSteeringCommand();
        }
    }

    public void turnLeft(){
        SteeringHelper.getInstance().changeDirection(5);
        sendSteeringCommand();
    }
    public void turnRight(){
        SteeringHelper.getInstance().changeDirection(-5);
        sendSteeringCommand();
    }

    public String sendSteeringCommand(){
        System.out.println(SteeringHelper.getInstance().getCommandString());
        return SteeringHelper.getInstance().getCommandString();
    }
    */
}
