package communication;

import java.io.File;

import org.bytedeco.javacpp.opencv_shape.ThinPlateSplineShapeTransformer;

/**
 * Stores speed and handling values that can be used by calculations and sent to
 * the moped.
 */
public class MopedSteeringHandler {
	public static String steeringCommand;
	public static int velocity;
	public static int handling;
	public static int enginePercentage = 0;
	public static int stearingPercentage = 0;
	private static int steeringOffset = -10;
	public static File image;

	public static void setVelocity(int velocity) {
		MopedSteeringHandler.velocity = velocity;
	}
	
	public static void setImageFile(File image){
		MopedSteeringHandler.image = image;
	}
	
	public static File getImageFile(){
		return MopedSteeringHandler.image;
	}

	public static void setHandling(int handling) {
		MopedSteeringHandler.handling = handling + steeringOffset;
	}

	public static int getVelocity() {
		return MopedSteeringHandler.velocity;
	}

	public static int getHandling() {
		return MopedSteeringHandler.handling;
	}
}
