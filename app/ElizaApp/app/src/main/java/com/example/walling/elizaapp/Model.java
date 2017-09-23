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

public class Model {
    //TODO add helperclass for translation (ex: "set speed to 50 -> translates to V0050H0000")
    private static Model instance;
    private Socket socket;
    private String ip = "192.168.43.183";
    private Integer port = 9000;
    private PrintWriter out;
    private boolean connected = false;
    private boolean isCruiseControlActive = false;

    private Model(){
        socket = new Socket();
    }

    public static Model getInstance(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }

    public void establishConnection(final String ipInput, final int portInput){

        System.out.println("Model register request, starting thread...");
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("Trying to connect to: " + ipInput + " @ port: " + portInput);
                System.out.println("Entering try-catch sequence: ");

                Looper.prepare();

                try {
                    System.out.println("1");
                    InetSocketAddress inetSocketAddres = new InetSocketAddress(ipInput, portInput);
                    System.out.println("2");
                    System.out.println("connecting... message sent");

                    handler.post(new Runnable() {
                        public void run() {
                            MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.CONNECTING));
                        }
                    });

                    socket.connect(inetSocketAddres);
                    System.out.println("3");

                    out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    System.out.println("4");
                    out.println("S0008T0007");
                    System.out.println("5");
                    connected = true;
                    System.out.println("6");
                } catch (Exception e) {
                    System.out.println("7");
                    connected = false;
                    System.out.println("8");
                    e.printStackTrace();
                    if (e.getMessage().equals("Connection refused") || e.getMessage().equals("Socket closed")){
                        System.out.println("sending error message connectio refused");
                        MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.PORT_CLOSED));
                    }
                    //TODO is it really "Already connected" ????. CHECK!
                    else if(e.getMessage().equals("Already Connected")) {
                        MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.ALREADY_CONNECTED));
                    }
                    System.out.println("exception localized message: " + e.getLocalizedMessage());
                    System.out.println("exception message: " + e.getMessage());
                } finally { handler.post(new Runnable() {
                    public void run() {
                        MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.CONNECTION_DONE));                    }
                });
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

    public void sendSteeringCommand(){
        System.out.println(SteeringHelper.getInstance().getCommandString());
        out.println(SteeringHelper.getInstance().getCommandString());
    }
}
