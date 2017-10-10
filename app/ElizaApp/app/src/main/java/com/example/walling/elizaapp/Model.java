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

/**
 * Created by walling on 2017-09-13.
 *
 * Singleton class for our Model
 */

public class Model {
    //TODO add helperclass for translation (ex: "set speed to 50 -> translates to V0050H0000")
    private boolean isCruiseControlActive = false;
    private static Model instance;
    private String ip = "10.0.2.2";
    private Integer port = 9000;
    private PrintWriter out;
    private boolean connected = false;
    private ServerCommunicator SC;
    private String message;
    private MessageCreator msgCreator = new MessageCreator();

    private Model(){
        //this.SC = new ServerCommunicator(out);

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
                    //InetSocketAddress inetSocketAddres = new InetSocketAddress(ipInput, portInput);
                    System.out.println(ipInput + ", " + Integer.toString(portInput));
                    Socket client = new Socket(ipInput, portInput);
                    System.out.println("client created");


                    handler.post(new Runnable() {
                        public void run() {
                            //MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.CONNECTING));
                        }
                    });

                    //socket.connect(inetSocketAddres);

                     out = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));{
                        String response=in.readLine();
                        //TODO set "message" variable on button click from view
                        message = "V0000H0000";
                        System.out.println("I received: " + response);
                        out.println("V0000H0000");
                        connected = true;
                        while(true) {
                            Thread.sleep(35);
                            message = msgCreator.getCompleteString();
                            //System.out.println("looping in big loop");
                            response = in.readLine();
                            if(response != null) {
                                //System.out.println("looping in response loop");
                                //System.out.println("I received: " + response);
                                response = null;
                            }

                            if(message != null) {
                                if (!message.equals("empty")) {
                                    //System.out.println("Sending: " + message);
                                    out.println(message);
                                    //message = "empty";
                                } else {
                                    out.println(message);
                                }
                                System.out.println("sending " + message);
                            } else {
                                System.out.println("message is.. null");
                            }
                        }
                        /*
                        if (firstResponse.equals("Send over data.")) {
                            while(true){
                                //wait(200);
                                out.println(sendSteeringCommand());

                                if(in.readLine().equals("bye")){
                                    break;
                                }
                            }
                            out.println("hi from app");
                            System.out.println("sent hi from app");
                            String reply = in.readLine();
                            System.out.println("Reply was " + reply);
                            //return reply;

                        }*/
                }

                    //out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    //out.println("S0008T0007");

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
        msgCreator.setSteerString(SteeringHelper.getInstance().getCommandString());
    }

    public void setCruiseControlState(boolean state) {


    }

    public void changeDirection(int direction){
        SteeringHelper.getInstance().setDirection(direction);
        msgCreator.setSteerString(SteeringHelper.getInstance().getCommandString());
    }
    public void changeVelocity(int velocity){
        SteeringHelper.getInstance().setVelocity(velocity);
        msgCreator.setSteerString(SteeringHelper.getInstance().getCommandString());
    }

    public String sendSteeringCommand(){
        msgCreator.setSteerString(SteeringHelper.getInstance().getCommandString());
        return (SteeringHelper.getInstance().getCommandString());
    }

    public void setACC(boolean state) {
        msgCreator.setACC(state);
    }

    public void setPlatooning(boolean state) {
        msgCreator.setPlatooning(state);
    }

    public void setSteerString(String steerString) {
        msgCreator.setSteerString(steerString);
    }

    public void setSpeed(double speed) {
        msgCreator.setSpeed(speed);
    }
}
