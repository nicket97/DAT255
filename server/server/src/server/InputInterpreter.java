package server;

public class InputInterpreter {
	
	private static int batteryVoltage;
	private static int dist;
	private static int engineSpeed;
	private static int speed;
	private static int timeStamp;

	public InputInterpreter(String input) {
		this.batteryVoltage = 0;
		this.dist = 0;
		this.engineSpeed = 0;
		this.speed = 0;
		this.timeStamp = 0;
	}
	
	public static String interpretString(String input) {
		String interpret = " * THIS STRING IS MODIFIED *";
		input = input + interpret;
		return input;
	}

	public static void interpretObject(Data mopedData, Object newValue) {
		//TODO Interpret JSON string
		setData(mopedData, batteryVoltage, dist, engineSpeed, speed, timeStamp);
	}
	private static void setData(Data mopedData,int batteryVoltage, int dist, int engineSpeed, int speed, int timestamp) {
		mopedData.setBatteryVoltage(batteryVoltage);
		mopedData.setDist(dist);
		mopedData.setEngineSpeed(engineSpeed);
		mopedData.setSpeed(speed);
		mopedData.setTimeStamp(timeStamp);
		return;
	}
}
