package com.example.walling.elizaapp;

import android.graphics.PorterDuff;

import java.net.Socket;

/**
 * Created by walling on 2017-09-26.
 */

public class ServerCommunicator implements Runnable {
    //Current ammount of tics that the app sleeps between communicating info to server
    private int ticRate = 15;
    private String newCommand;
    private String currentCommand;
    private Socket cmd;


    public ServerCommunicator(){

    }
    @Override
    public void run() {
        while (true){
            try {
                //TODO
                newCommand = Model.getInstance().sendSteeringCommand();

                if (currentCommand==null || !newCommand.equals(currentCommand))
                {
                    currentCommand=newCommand;
                }

                //TODO take socket from Model class and send it up to the server
                Thread.sleep(ticRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
