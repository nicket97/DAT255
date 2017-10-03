package test;

import acc.ACCController;
import server.Data;
import server.Start;

public class ACCSimulator implements Runnable {
	public int leadCarSpeed ;
	public int leadCarDist;
	public int mopedDist = 0;
	public int targetDist;
	
	public ACCSimulator(int leadSpeed, int leadDist, int targetDist){
		this.leadCarSpeed = leadSpeed;
		this.leadCarDist = leadDist;
		this.targetDist = targetDist;

	}
	@Override
	public void run() {
		ACCController acc = new ACCController(50);
		while(true){
			
		}

	}

}
