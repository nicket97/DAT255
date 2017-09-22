package com.example.walling.elizaapp;

/**
 * Created by walling on 2017-09-13.
 */

public class Controller {
    private IMainView ui;


    public Controller(IMainView ui){
        this.ui = ui;
    }

    public void accelerate() {
        Model.getInstance().setForwardSpeed();
    }

    public void stop() {
        Model.getInstance().stop();
    }

    public void reverse() {
        Model.getInstance().setBackwardSpeed();
    }

    public void activateCruiseControl(){
        Model.getInstance().setCruiseControlState(true);
    }
    public void deActivateCruiseControl(){
        Model.getInstance().setCruiseControlState(false);
    }

    public void establishConnection(String ip, int port) {
        System.out.println("controller sending to model");
        Model.getInstance().establishConnection(ip, port);
    }

    public boolean getConnectionStatus() {
        return Model.getInstance().isConnected();
    }
}
