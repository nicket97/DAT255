package com.example.walling.elizaapp;

/**
 * Created by walling on 2017-09-13.
 */

public class Controller {
    private IMainView ui;

    public Controller(IMainView ui){
        this.ui = ui;
    }

    public void stop() {
        Model.getInstance().stop();
        //ui.setSpeedBarValue(0);
    }

    public void changeDirection(int direction){
        Model.getInstance().changeDirection(direction);
    }
    public void changeVelocity(int velocity){
        Model.getInstance().changeVelocity(velocity);
    }

    public void establishConnection(String ip, int port) {
        Model.getInstance().establishConnection(ip, port);
    }

    public void setACC(boolean state) {
        Model.getInstance().setACC(state);
    }

    public void setSpeed(double speed) {
        Model.getInstance().setSpeed(speed);
    }

    public void setSteerString(String string) {
        Model.getInstance().setSteerString(string);
    }

    public void setPlatooning(boolean state) {
        Model.getInstance().setPlatooning(state);
    }

    public void disconnect() {
        Model.getInstance().disconnect();
    }

    public boolean getConnectionStatus() {
        return Model.getInstance().isConnected();
    }
}
