package server;

import acc.ACCController;
import platooning.PlatooningController;

public class ProgramManager {
	static boolean programActive = false;
	static boolean ACCActive = false;
	static boolean platooningActive = false;
	static Thread accThread = null;
	static Thread platoonThread = null;
	
	public static void startACC(int dist){
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
	
	public static void startPlatooning(){
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
	public static boolean isProgramActive(){
		return programActive;
	}
}
