package com.example.walling.elizaapp;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.json.*;

/**
 * Created by walling on 2017-09-13.
 *
 * Singleton class for our Model
 */

public class Model {
    //TODO add helperclass for translation (ex: "set speed to 50 -> translates to V0050H0000")
    private static Model instance;
    private PrintWriter out;
    private boolean connected = false;
    private MessageCreator msgCreator = new MessageCreator();
    private JSONObject json = new JSONObject();

    private Model(){
        initJSON();
    }

    public static Model getInstance(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }

    private void initJSON() {
        try {
            json.put("Steering", "V0000H0000");
            json.put("ACC", false);
            json.put("Platooning", false);
            json.put("Speed", 0.0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void establishConnection(final String ipInput, final int portInput){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    System.out.println(ipInput + ", " + Integer.toString(portInput));
                    Socket client = new Socket(ipInput, portInput);
                    System.out.println("client created");

                     out = new PrintWriter(client.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));{
                        String response=in.readLine();
                        System.out.println("I received: " + response);
                        out.println(json);
                        connected = true;

                        while(true) {
                            Thread.sleep(50);
                            response = in.readLine();

                            if(response != null) {
                                System.out.println("From server: " + response);
                            }

                            System.out.println("sending json: " + json.get("Steering"));
                            out.println(json);
                        }
                }

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
                }
            }
        }).start();

    }
    public boolean isConnected(){
        return connected;
    }

    public void stop(){
        SteeringHelper.getInstance().setVelocity(0);
        setSteerString(SteeringHelper.getInstance().getCommandString());
    }

    public void changeDirection(int direction){
        SteeringHelper.getInstance().setDirection(direction);
        setSteerString(SteeringHelper.getInstance().getCommandString());
    }
    public void changeVelocity(int velocity){
        SteeringHelper.getInstance().setVelocity(velocity);
        setSteerString(SteeringHelper.getInstance().getCommandString());
    }

    public String sendSteeringCommand(){
        msgCreator.setSteerString(SteeringHelper.getInstance().getCommandString());
        return (SteeringHelper.getInstance().getCommandString());
    }

    public void setACC(boolean state) {
        try {
            json.put("ACC", state);
            System.out.println(json.get("ACC").toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setPlatooning(boolean state) {
        try {
            json.put("Platooning", state);
            System.out.println(json.get("Platooning").toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setSteerString(String steerString) {
        try {
            json.put("Steering", steerString);
            System.out.println(json.get("Steering").toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setSpeed(double speed) {
        try {
            json.put("Speed", speed);
            System.out.println(json.get("Speed").toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
