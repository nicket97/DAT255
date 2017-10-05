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


    public void activateCruiseControl(){
        Model.getInstance().setCruiseControlState(true);
    }
    public void deActivateCruiseControl(){
        Model.getInstance().setCruiseControlState(false);
    }

    public void establishConnection(String ip, int port) {
        Model.getInstance().establishConnection(ip, port);
    }

    public boolean getConnectionStatus() {
        return Model.getInstance().isConnected();
    }
}
