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

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                try {
                    InetSocketAddress inetSocketAddres = new InetSocketAddress(ipInput, portInput);
                    handler.post(new Runnable() {
                        public void run() {
                            MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.CONNECTING));
                        }
                    });

                    socket.connect(inetSocketAddres);

                    out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println("S0008T0007");
                    connected = true;
                } catch (Exception e) {
                    connected = false;
                    e.printStackTrace();
                    if (e.getMessage().equals("Connection refused") || e.getMessage().equals("Socket closed")){
                        MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.PORT_CLOSED));
                    }

                    //TODO is it really "Already connected" ????. CHECK!
                    else if(e.getMessage().equals("Already Connected")) {
                        MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.ALREADY_CONNECTED));
                    }

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
}
