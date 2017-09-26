package test;

import acc.ACCController;
import server.Data;

public class ACCSimulator implements Runnable {
	public int leadCarSpeed = 50;
	public int leadCarDist = 200;
	public int mopedDist = 0;
	@Override
	public void run() {
		ACCController acc = new ACCController(50);
		while(true){
			acc.updateMopedSpeed();
			leadCarDist += leadCarSpeed;
			mopedDist += Data.speed;
			Data.dist = leadCarDist - mopedDist;
			System.out.println("LeadCarDist = " + leadCarDist + "  mopedDist = " + mopedDist + " MoedSpeed = " + Data.speed);
			
			
			try {
				System.out.println("sleeping");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
