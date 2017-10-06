package comunication;

public class MopedSteeringHandler {
	public static String steeringCommand;
	public static int enginePercentage = 0;
	public static int stearingPercentage = 0;
	
	public void setSpeed(int speed){
		//TODO make the cm/s convert to a engine setting
		
	}
	
	public static void setSteeringCommand(String sc) {
		steeringCommand = sc;
	}
	
	public static String getSteeringCommand() {
		return steeringCommand;
	}
}
