package server;

import platooning.PlatooningController;

public class ProgramManager {
	boolean programActive = false;
	boolean ACCActive = false;
	boolean platooningActive = false;
	
	public void startACC(){
		
	}
	
	public void startPlatooning(){
		Thread platoon = new Thread(new PlatooningController());
	}
	public boolean isACCActive(){
		return ACCActive;
	}
	public boolean isPlatooningActive(){
		return platooningActive;
	}
	public boolean isProgramActive(){
		return programActive;
	}
}
