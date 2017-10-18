package server;

import communication.MopedSteeringHandler;
import gui.IDataGrabber;

public class DataGrabber implements IDataGrabber {
	private static String imagePath;
	private static int posX;
	private static int posY;

	@Override
	public double getVelocity() {
		
		return MopedSteeringHandler.velocity;
	}

	@Override
	public double getHandling() {
		
		return MopedSteeringHandler.handling;
	}
	
	public static void setPosX(int posX) {
		DataGrabber.posX = posX;
	}
	
	public int getPosX() {
		return DataGrabber.posX;
	}
	
	public static void setPosY(int posY) {
		DataGrabber.posY = posY;
	}
	
	public int getPosY() {
		return DataGrabber.posY;
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
