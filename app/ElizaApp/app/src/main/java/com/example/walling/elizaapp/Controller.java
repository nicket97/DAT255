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
        DrivingModel.getInstance().increaseForwardSpeed();
    }

    public void stop() {
        DrivingModel.getInstance().stop();
    }

    public void reverse() {
        DrivingModel.getInstance().decreaseForwardSpeed();
    }

    public void turnLeft(){
        DrivingModel.getInstance().turnLeft();
    }

    public void turnRight(){
        DrivingModel.getInstance().turnRight();
    }

    public void activateCruiseControl(){
        DrivingModel.getInstance().setCruiseControlState(true);
    }
    public void deActivateCruiseControl(){
        DrivingModel.getInstance().setCruiseControlState(false);
    }

    public void establishConnection(String ip, int port) {
        Model.getInstance().establishConnection(ip, port);
    }

    public boolean getConnectionStatus() {
        return Model.getInstance().isConnected();
    }
}
