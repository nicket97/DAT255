package com.example.walling.elizaapp;

/**
 * Created by Klickor on 2017-09-18.
 */

public class SteeringHelper {

    //V0000H0000
    //V0000 = STOP
    //V-100 = Full reverse
    //V0100 = Max forward
    //H0000 = Straight forward
    //H0100 = Max Right turn
    //H-100 = Max Left turn

    //Values between -100 to 100.
    private int velocity;
    private int direction;
    private String commandString;

    public SteeringHelper(){
        this.velocity = 0;
        this.direction = 0;
        this.commandString = "V0000H0000";
    }

    public void setVelocity(int changeInVelocity){
        int newVelocity = this.velocity + changeInVelocity;
        if (newVelocity > 100){
            this.velocity = 100;
        }else if(newVelocity < -100){
            this.velocity = -100;
        }else {
            this.velocity = newVelocity;
        }
        updateCommandString();
    }
    public void setDirection(int changeInDirection){
        int newDirection = this.direction + changeInDirection;
        if (newDirection > 100){
            this.direction = 100;
        }else if(newDirection< -100){
            this.direction = -100;
        }else {
            this.direction = newDirection;
        }
        updateCommandString();
    }

    public int getVelocity(){
        return velocity;
    }
    public int getDirection(){
        return direction;
    }
    public String getCommandString(){
        return commandString;
    }

    private void updateCommandString(){
        this.commandString = getVelocityString()+ getDirectionString();
    }

    private String getVelocityString(){
        String velocityString;
        if(velocity == 100){
            velocityString = "V0" + velocity;
        }else if(velocity == -100){
            velocityString = "V-" + velocity;
        }else if(velocity > -10 && velocity < 0){
            velocityString = "V-00" + velocity;
        }else if(velocity < -9){
            velocityString = "V-0" + velocity;
        }else if(velocity < 10){
            velocityString = "V000" + velocity;
        }else{
            velocityString = "V00" + velocity;
        }
        return velocityString;
    }

    private String getDirectionString() {
        String directionString;
        if (direction == 100) {
            directionString = "H0" + direction;
        } else if (direction == -100) {
            directionString = "H-" + direction;
        } else if (direction > -10 && direction < 0) {
            directionString = "H-00" + direction;
        } else if (direction < -9) {
            directionString = "H-0" + direction;
        } else if (direction < 10) {
            directionString = "H000" + direction;
        } else {
            directionString = "H00" + direction;
        }
        return directionString;
    }

}
