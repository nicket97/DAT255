package com.example.walling.elizaapp;

/**
 * Created by walling on 2017-09-13.
 * Controller for our MVC design pattern. Lets the views (activities) communicate
 * with the Model through one channel.
 */

public class Controller {

    /**
     * Instantiate the controller
     */
    public Controller(){
    }

    /**
     * Stop the MOPED
     */
    public void stop() {
        Model.getInstance().stop();
    }

    /**
     * Change the direction of the MOPED's steering
     * @param direction The direction given as a MOPED-steering signal.
     */
    public void changeDirection(int direction){
        Model.getInstance().changeDirection(direction);
    }

    /**
     * Change the velocity of the MOPED's driving.
     * @param velocity The velocity which is to be changed to. Given as a MOPED-velocity signal.
     */
    public void changeVelocity(int velocity){
        Model.getInstance().changeVelocity(velocity);
    }

    /**
     * Establish connection between the App and the Server
     * @param ip The IP which to connect to
     * @param port The port which to connect to
     */
    public void establishConnection(String ip, int port) {
        Model.getInstance().establishConnection(ip, port);
    }

    /**
     * Set the state of ACC
     * @param state True or false, new state of ACC
     */
    public void setACC(boolean state) {
        Model.getInstance().setACC(state);
    }

    /**
     * Set the speed
     * @param speed Speed of the moped, given as double
     */
    public void setSpeed(double speed) {
        Model.getInstance().setSpeed(speed);
    }

    /**
     * Set the steerstring of the moped, given as a proper steerstring. ex: "V0000H0000"
     * @param string given as a String, see example above.
     */
    public void setSteerString(String string) {
        Model.getInstance().setSteerString(string);
    }

    /**
     * Set state of the platooning.
     * @param state boolean state of platooning, true false
     */
    public void setPlatooning(boolean state) {
        Model.getInstance().setPlatooning(state);
    }

    /**
     * Disconnect from the server
     */
    public void disconnect() {
        Model.getInstance().disconnect();
    }

    /**
     * Get the connection status the App-server
     * @return boolean state of the connection, true or false
     */
    public boolean getConnectionStatus() {
        return Model.getInstance().isConnected();
    }
}
