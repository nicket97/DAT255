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
    private String ip = "192.168.43.61";
    private Integer port = 9000;
    private PrintWriter out;
    private boolean connected = false;

    private Model(){
        socket = new Socket();
    }

    public static Model getInstance(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }

    public void establishConnection(/*String ip, int port*/){

        //this.ip = ip;
        //this.port = port;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetSocketAddress inetSocketAddres = new InetSocketAddress(ip,port);
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
        if(!isConnected()) {
            establishConnection();
        }
        out.println("V0050H0000");
    }
    public void setBackwardSpeed(){
        if(!isConnected()) {
            establishConnection();
        }
        out.println("V-050H0000");
    }
    public void setSteer(int value){

    }
    public void stop(){
        if(!isConnected()) {
            establishConnection();
        }
        out.println("V0000H0000");
    }

}
