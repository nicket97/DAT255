package com.example.walling.elizaapp;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

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
    private boolean wasEverConnected = false;
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
            json.put("Velocity", 0000);
            json.put("Handling", 0000);
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
                    Socket client = new Socket();
                    client.connect(new InetSocketAddress(ipInput, portInput), 1000);
                    connected = true;
                    wasEverConnected = true;
                    System.out.println("client created");

                     out = new PrintWriter(client.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));{
                        String response=in.readLine();
                        System.out.println("I received: " + response);
                        out.println(json);

                        while(connected) {
                            Thread.sleep(200);
                            response = in.readLine();

                            if(response != null) {
                                if (response.equals("Connection to moped lost.")) {
                                    connected = false;
                                }
                                //Todo if "Connection to moped lost."
                                System.out.println("From server: " + response);
                            } else {
                                connected = false;
                            }

                            System.out.println("sending json");
                            out.println(json);
                        }
                }

                } catch (Exception e) {
                    connected = false;
                    System.out.println(e.getMessage());
                    if (e.getMessage().equals("Connection refused") || e.getMessage().equals("Socket closed")
                            || e.getMessage().equals("connect timed out")){
                        MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.PORT_CLOSED));
                    }

                    //TODO is it really "Already connected" ????. CHECK!
                    else if(e.getMessage().equals("Already Connected")) {
                        MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.ALREADY_CONNECTED));
                    }
                } finally {
                    if (wasEverConnected) {
                        MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.CONNECTION_LOST));
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

    public boolean wasEverConnected() {
        return wasEverConnected;
    }

    public void changeDirection(int direction){
        SteeringHelper.getInstance().setDirection(direction);
        setSteerString(SteeringHelper.getInstance().getCommandString());
    }
    public void changeVelocity(int velocity){
        SteeringHelper.getInstance().setVelocity(velocity);
        setSteerString(SteeringHelper.getInstance().getCommandString());
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

    public void disconnect() {
        connected = false;
    }

    public void setSteerString(String steerString) {
        try {
            int vel = Integer.parseInt(steerString.substring(1, 5));
            int handling = Integer.parseInt(steerString.substring(6,10));
            json.put("Velocity", vel);
            json.put("Handling", -handling);
            System.out.println(json.get("Velocity"));
            System.out.println(json.get("Handling"));
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
