package gui;

import comunication.MopedSteeringHandler;
import server.ProgramManager;
import server.Start;

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
		return true;
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
