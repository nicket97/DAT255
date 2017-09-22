package com.example.walling.elizaapp;

/**
 * Created by walling on 2017-09-13.
 */

public class Controller {
    private IMainView ui;


    public Controller(MainActivity ui){
        this.ui = ui;
    }

    public void accelerate() {
        Model.getInstance().increaseForwardSpeed();
    }

    public void stop() {
        Model.getInstance().stop();
    }

    public void reverse() {
        Model.getInstance().decreaseForwardSpeed();
    }

    public void turnLeft(){
        Model.getInstance().turnLeft();
    }

    public void turnRight(){
        Model.getInstance().turnRight();
    }

    public void activateCruiseControl(){
        Model.getInstance().setCruiseControlState(true);
    }
    public void deActivateCruiseControl(){
        Model.getInstance().setCruiseControlState(false);
    }
}
