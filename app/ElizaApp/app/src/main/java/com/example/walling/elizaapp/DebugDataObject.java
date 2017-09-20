package com.example.walling.elizaapp;

/**
 * Created by olofenstrom on 2017-09-20.
 */

public class DebugDataObject {

    private int batteryPercentage;
    private int batteryWattage;
    private int speed;

    private int batteryMaxWattage;

    public DebugDataObject(int batteryWattage, int speed) {
        this.batteryMaxWattage = 350; //just an example, dont actually know

        this.batteryWattage = batteryWattage;
        this.speed = speed;

        this.batteryPercentage = batteryWattage / this.batteryMaxWattage;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public int getBatteryWattage() {
        return batteryWattage;
    }

    public void setBatteryWattage(int batteryWattage) {
        this.batteryWattage = batteryWattage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBatteryMaxWattage() {
        return batteryMaxWattage;
    }

    public void setBatteryMaxWattage(int batteryMaxWattage) {
        this.batteryMaxWattage = batteryMaxWattage;
    }
}
