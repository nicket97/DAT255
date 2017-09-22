package com.example.walling.elizaapp;

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

public class Model {
    //TODO add helperclass for translation (ex: "set speed to 50 -> translates to V0050H0000")
    private static Model instance;
    private Socket socket;
    private String ip = "192.168.43.183";
    private Integer port = 9000;
    private PrintWriter out;
    private boolean connected = false;
    private boolean isCruiseControlActive = false;
    private SteeringHelper steerHelp;



    private Model(){
        socket = new Socket();
        steerHelp = new SteeringHelper();
    }

    public static Model getInstance(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }

    public void establishConnection(final String ipInput, final int portInput){

        System.out.println("Model register request, starting thread...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("Trying to connect to: " + ipInput + " @ port: " + portInput);
                System.out.println("Entering try-catch sequence: ");

                try {
                    InetSocketAddress inetSocketAddres = new InetSocketAddress(ipInput, portInput);
                    socket.connect(inetSocketAddres);

                    out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println("S0008T0007");
                    connected = true;
                } catch (Exception e) {
                    connected = false;
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public boolean isConnected(){
        return connected;
    }

    public void setForwardSpeed(){
        /*if(!isConnected()) {
            establishConnection();
        }*/
        if(!isCruiseControlActive) {
            out.println("V0050H0000");
            //
        }
    }
    public void setBackwardSpeed(){
        /*if(!isConnected()) {
            establishConnection();
        }*/
        if(!isCruiseControlActive) {
            out.println("V-050H0000");
        }
    }

    public void stop(){
        /*if(!isConnected()) {
            establishConnection();
        }*/
        steerHelp.setVelocity(0);
        setCruiseControlState(false);
        sendSteeringCommand();
    }

    public void setCruiseControlState(boolean state){
        this.isCruiseControlActive = state;
    }


    //If in reverse, go slower and slower until you go forward again
    public void increaseForwardSpeed(){
        steerHelp.setVelocity(5);
        sendSteeringCommand();
    }
    //Decrease enough and you go into revers
    public void decreaseForwardSpeed(){
        steerHelp.setVelocity(-5);
        sendSteeringCommand();
    }

    public void turnLeft(){
        steerHelp.setDirection(-5);
        sendSteeringCommand();
    }
    public void turnRight(){
        steerHelp.setDirection(5);
        sendSteeringCommand();
    }

    public void sendSteeringCommand(){
        out.println(steerHelp.getCommandString());
    }
}
