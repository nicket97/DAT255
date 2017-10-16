package core;

public class SimulationHandler {
	
	Thread accThread;
	boolean ACCActive = false;
	Thread platooningThread;
	boolean platooningActive = false;
	
	public void startACCSimulator(){
		accThread = new Thread();
		ACCActive = true;
		accThread.start();
	}

	public void stopACCSimulator() {
		accThread.stop();
		ACCActive = false;
		
	}

	public void startPlatooning() {
		platooningThread = new Thread(new PlatooningSimulator());
		platooningActive = true;
		platooningThread.start();
		
	}

	public void stopPlatooning() {
		platooningThread.stop();
		platooningActive = false;
		
	}
}
