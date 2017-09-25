package com.example.walling.elizaapp;
/**
 * Created by Klickor on 2017-09-18.
 */
public class SteeringHelper {
    public static SteeringHelper instance;

    //V0000H0000
    //V0000 = STOP
    //V-100 = Full reverse
    //V0100 = Max forward
    //H0000 = Straight forward
    //H0100 = Max Left turn
    //H-100 = Max Right turn
    //Values between -100 to 100.

    private int velocity;
    private int direction;
    private String commandString;
    private String velocityString;
    private String directionString;
    private SteeringHelper(){
        this.velocity = 0;
        this.direction = 0;
        this.commandString = "V0000H0000";
        this.velocityString = "V0000";
        this.directionString = "H0000";
    }
    public static SteeringHelper getInstance(){
        if(instance == null){
            instance = new SteeringHelper();
        }
        return instance;
    }

    public void setVelocity(int newVelocity){
        this.velocity = newVelocity;
        updateCommandString();
    }
    public void setDirection(int newDirection){
        this.direction = newDirection;
        updateCommandString();
    }
    public void changeVelocity(int changeInVelocity){
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
    public void changeDirection(int changeInDirection){
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
    public String getVelocityString(){
        return velocityString;
    }
    public String getDirectionString(){
        return directionString;
    }
    public String getCommandString(){
        return commandString;
    }
    private void updateCommandString(){
        updateVelocityString();
        updateDirectionString();
        this.commandString = velocityString + directionString;
    }
    private void updateVelocityString(){
        String velocityString;
        if(velocity == 100){
            velocityString = "V0100";
        }else if(velocity == -100){
            velocityString = "V-100";
        }else if(velocity > -10 && velocity < 0){
            velocityString = "V-00" + Math.abs(velocity);
        }else if(velocity < -9){
            velocityString = "V-0" + Math.abs(velocity);
        }else if(velocity < 10){
            velocityString = "V000" + velocity;
        }else{
            velocityString = "V00" + velocity;
        }
        this.velocityString = velocityString;
    }
    private void updateDirectionString() {
        String directionString;
        if (direction == 100) {
            directionString = "H0100";
        } else if (direction == -100) {
            directionString = "H-100";
        } else if (direction > -10 && direction < 0) {
            directionString = "H-00" + Math.abs(direction);
        } else if (direction < -9) {
            directionString = "H-0" + Math.abs(direction);
        } else if (direction < 10) {
            directionString = "H000" + direction;
        } else {
            directionString = "H00" + direction;
        }
        this.directionString = directionString;
    }
}