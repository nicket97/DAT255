package comunication;

public class MopedSteeringHandler {
	public static String steeringCommand;
	public static int velocity;
	public static int handling;
	public static int enginePercentage = 0;
	public static int stearingPercentage = 0;

	public static void setVelocity(int velocity) {
		MopedSteeringHandler.velocity = velocity;
	}

	public static void setHandling(int handling) {
		MopedSteeringHandler.handling = handling;
	}

	public static int getVelocity() {
		return velocity;
	}

	public static int getHandling() {
		return handling;
	}
}
