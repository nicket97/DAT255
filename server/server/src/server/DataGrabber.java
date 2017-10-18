package server;

import communication.MopedSteeringHandler;
import programs.ProgramManager;

public class DataGrabber implements IDataGrabber {

	@Override
	public double getVelocity() {
		
		return MopedSteeringHandler.velocity;
	}

	@Override
	public double getHandling() {
		
		return MopedSteeringHandler.handling;
	}

	@Override
	public String getImagePath() {
		
		return null;
	}

	@Override
	public boolean getAppConnection() {
		//TODO fix
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
