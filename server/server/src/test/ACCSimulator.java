package test;

import acc.ACCController;
import server.Data;

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
			acc.setTargetSpeed(acc.detreminLeadSpeed(dist));
			acc.updateMopedSpeed();
			
			leadCarDist += leadCarSpeed;
			mopedDist += Data.speed;
			Data.dist = leadCarDist - mopedDist;
			//System.out.println(acc.detreminLeadSpeed());
			System.out.println("LeadCarDist = " + leadCarDist + "  mopedDist = " + mopedDist + " MoedSpeed = " + Data.speed);
			//System.out.println(acc.detreminLeadSpeed(Data.dist));
			
			try {
				System.out.println("sleeping");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
