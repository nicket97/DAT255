package server;

import acc.ACCController;
import platooning.PlatooningController;

public class ProgramManager {
	boolean programActive = false;
	boolean ACCActive = false;
	boolean platooningActive = false;
	Thread accThread = null;
	Thread platoonThread = null;
	
	public void startACC(int dist){
		accThread = new Thread(new ACCController(dist));
		accThread.start();
		ACCActive = true;
	}
	@SuppressWarnings("deprecation")
	public void stopACC(){
		if(accThread.isAlive()){
			accThread.stop();
			accThread = null;
			ACCActive = false;
		}
	}
	
	public void startPlatooning(){
		platoonThread = new Thread(new PlatooningController());
		platoonThread.start();
		platooningActive = true;
	}
	@SuppressWarnings("deprecation")
	public void stopPlatooning(){
		if(platoonThread.isAlive()){
			platoonThread.stop();
			platoonThread = null;
		}
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
