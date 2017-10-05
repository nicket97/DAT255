package server;
	
public class Data {
	public static final int maxSpeed = 100;
	public int dist;
	public int speed;
	public int engineSpeed;
	public int batteryVoltage;
	public long timeStamp;

	public Data() {
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

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getEngineSpeed() {
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
