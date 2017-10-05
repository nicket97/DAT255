package server;

import org.json.JSONException;
import org.json.JSONObject;

public class Data {
	
	public static Data instance;
	
	public static final int maxSpeed = 100;
	public double dist;
	public double speed;
	public double engineSpeed;
	public int batteryVoltage;
	public long timeStamp;

	public static synchronized Data getInstance() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}
	
	private Data() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param dist
	 * @param speed
	 * @param engineSpeed
	 * @param batteryVoltage
	 * @param timeStamp
	 */
	private Data(int dist, int speed, int engineSpeed, int batteryVoltage, long timeStamp){
		this.dist = dist;
		this.speed = speed;
		this.engineSpeed = engineSpeed;
		this.batteryVoltage = batteryVoltage;
		this.timeStamp = timeStamp;
	}
	
	public void update(String data) {
		 try {
	            JSONObject json = new JSONObject(data);

	            speed = json.getDouble("inspeed_avg"); //typ speed?
	            //fodometer = json.getDouble("fodometer"); //??
	            //odometer = json.getDouble("odometer"); //avstånd beräkna hastighet
	            dist = json.getDouble("can_ultra"); //sensor avstånd i meter
	            engineSpeed = json.getDouble("can_speed"); //motorns kraft 0-100
	            //can_steer = json.getDouble("can_steer"); //styrning

	        } catch (JSONException e){
	            e.printStackTrace();
	        }
	}

	public long getTime() {
		
		return timeStamp;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getEngineSpeed() {
		return engineSpeed;
	}

	public void setEngineSpeed(int engineSpeed) {
		this.engineSpeed = engineSpeed;
	}

	public int getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(int batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}


	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static int getMaxspeed() {
		return maxSpeed;
	}
	
}
