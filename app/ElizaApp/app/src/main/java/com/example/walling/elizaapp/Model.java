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
 * Establishes connection to server and continuously sends JSON objects
 * Notifies views via Listeners if a change happens in model e.g. connection lost
 * Views notify the model of changes via controller class
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


    /**
     * Gives the instance of the Model
     * @return the instance of the Model
     */
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

    /**
     * Establishes connection to server. Repeatedly sends JSON Objects to server.
     * @param ipInput IP to connect to
     * @param portInput Port to connect to
     */
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
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.CONNECTION_LOST1));
                                try {
                                    Thread.sleep(2500);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.CONNECTION_LOST2));
                            }
                        });
                    }
                }
            }
        }).start();
    }

    /**
     * Checks if the app is connected to server
     * @return Returns boolean state of connection
     */
    public boolean isConnected(){
        return connected;
    }

    /**
     * Stops the MOPED
     */
    public void stop(){
        SteeringHelper.getInstance().setVelocity(0);
        setSteerString(SteeringHelper.getInstance().getCommandString());
    }

    /**
     * Checks if the app ever was connected to the server since last start of app
     * @return Returns boolean, true if ever was connected, false if not
     */
    public boolean wasEverConnected() {
        return wasEverConnected;
    }

    /**
     * Changes the direction of the MOPED
     * @param direction a direction as integer, -100 to 100
     */
    public void changeDirection(int direction){
        SteeringHelper.getInstance().setDirection(direction);
        setSteerString(SteeringHelper.getInstance().getCommandString());
    }

    /**
     * Changes the velocity of the MOPED
     * @param velocity velocity, given as integer, -100 to 100
     */
    public void changeVelocity(int velocity){
        SteeringHelper.getInstance().setVelocity(velocity);
        setSteerString(SteeringHelper.getInstance().getCommandString());
    }

    /*
    Set state of Adaptive Cruise Control
     */
    public void setACC(boolean state) {
        try {
            json.put("ACC", state);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    Set state of Platooning
     */
    public void setPlatooning(boolean state) {
        try {
            json.put("Platooning", state);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    Disconnect from the server
     */
    public void disconnect() {
        connected = false;
    }

    /*
    Set the steer-string which later is sent to the server
     */
    public void setSteerString(String steerString) {
        try {
            int vel = Integer.parseInt(steerString.substring(1, 5));
            vel = adjustVel(vel);
            int handling = Integer.parseInt(steerString.substring(6,10));
            json.put("Velocity", vel);
            json.put("Handling", -handling);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    Set the speed of the MOPED
     */
    public void setSpeed(double speed) {
        try {
            json.put("Speed", speed);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private int adjustVel(int vel) {
        vel = vel + 50;

        if (vel > 0 && vel <= 25) {
            vel = vel / 8;
        } else if (vel > 25 && vel <= 50) {
            vel = vel / 6;
        } else if (vel > 50 && vel <= 75) {
            vel = vel / 4;
        } else if (vel > 75 && vel <= 100) {
            vel = vel / 2;
        } else if (vel > 100) {
            vel = vel - 50;
        }

        System.out.println("VEL: " + vel);
        return vel;
    }
}
