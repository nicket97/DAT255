package server;
	
public class Data {
	public static final int maxSpeed = 100;
	public int dist;
	public int speed;
	public int engineSpeed;
	public int batteryVoltage;
	public int timeStamp;
	
	public Data(int dist, int speed, int engineSpeed, int batteryVoltage, int timeStamp){
		this.dist = dist;
		this.speed = speed;
		this.engineSpeed = engineSpeed;
		this.batteryVoltage = batteryVoltage;
		this.timeStamp = timeStamp;
	}

	public int getTime() {
		
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

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static int getMaxspeed() {
		return maxSpeed;
	}
	
}
