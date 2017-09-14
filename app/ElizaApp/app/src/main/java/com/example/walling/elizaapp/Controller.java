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
        Model.getInstance().setForwardSpeed();
    }

    public void stop() {
        Model.getInstance().stop();
    }

    public void reverse() {
        Model.getInstance().setBackwardSpeed();
    }
}
