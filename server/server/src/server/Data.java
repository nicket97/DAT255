package server;

import org.json.JSONException;
import org.json.JSONObject;

/***
 * Holds data sent from MOPED to be used in calculations for ACC and platooning.
 */
public class Data {

	public static final int maxSpeed = 100;
	public double dist;
	public double speed;
	public double engineSpeed;
	public int batteryVoltage;
	public long timeStamp;

	/***
	 * Constructor for the Data class.
	 * 
	 * @param data
	 *            JSON string sent from MOPED.
	 */
	public Data(String data) {
		try {
			JSONObject json = new JSONObject(data);

			this.speed = json.getDouble("inspeed_avg"); // typ speed?
			// fodometer = json.getDouble("fodometer"); //??
			// odometer = json.getDouble("odometer"); //avstånd beräkna hastighet
			this.dist = json.getDouble("can_ultra"); // sensor avstånd i meter
			this.engineSpeed = json.getDouble("can_speed"); // motorns kraft 0-100
			// can_steer = json.getDouble("can_steer"); //styrning
			this.timeStamp = json.getLong("timestamp");
			//System.out.println("Dist = " + dist);
			//System.out.println(this.timeStamp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Data(int dist, int speed, int engineSpeed, int batteryVoltage, long timeStamp){
  		this.dist = dist;
  		this.speed = speed;
  		this.engineSpeed = engineSpeed;
  		this.batteryVoltage = batteryVoltage;
  		this.timeStamp = timeStamp;
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
