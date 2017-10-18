package server;

import communication.MopedSteeringHandler;
import gui.IDataGrabber;

public class DataGrabber implements IDataGrabber {
	private static String imagePath;

	@Override
	public double getVelocity() {
		
		return MopedSteeringHandler.velocity;
	}

	@Override
	public double getHandling() {
		
		return MopedSteeringHandler.handling;
	}
	
	public static void setImagePath(String imagePath) {
		DataGrabber.imagePath = imagePath;
	}

	public String getImagePath() {
		return DataGrabber.imagePath;
	}

	@Override
	public boolean getAppConnection() {
		return Start.appConnection.isAppConnected();
	}

	@Override
	public boolean getACCActivated() {
		
		return ProgramManager.isACCActive();
	}

	@Override
	public boolean getPlatooningActivated() {
		// TODO Auto-generated method stub
		return ProgramManager.isPlatooningActive();
	}

}
