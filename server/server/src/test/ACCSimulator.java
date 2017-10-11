package test;

import acc.ACCController;
import model.FixedDataQueue;
import server.Data;
import server.ProgramManager;
import server.Start;

public class ACCSimulator implements Runnable {
	public int leadCarSpeed ;
	public int leadCarDist;
	public int mopedDist = 0;
	public int targetDist;
	ProgramManager pm;
	Start start = new Start(true);
	
	public ACCSimulator(int leadSpeed, int leadDist, int targetDist){
		this.leadCarSpeed = leadSpeed;
		this.leadCarDist = leadDist;
		this.targetDist = targetDist;
		 pm = new ProgramManager();
		 

	}
	@Override
	public void run() {
		ACCController acc = new ACCController(50);
		start.dataHolder.addFirst(new Data(leadCarDist - mopedDist,0 , 0, 0,System.currentTimeMillis()-1000));
		start.dataHolder.addFirst(new Data(leadCarDist + 50 - mopedDist,0, 0, 0,System.currentTimeMillis()));
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("=================================");
			
			Data d = Start.start.dataHolder.getFirst();
			
			
			
			int targetSpeed = d.speed + acc.detreminLeadSpeed();
			//targetSpeed = 50;
			System.out.println(targetSpeed + "           " + d.getSpeed() + "             " + d.dist);
			
			int speed = acc.getACCSpeed(d.getDist(), targetSpeed, this.targetDist);
			
			System.out.println("leadDist = " + leadCarDist + "  mopedDist = " + mopedDist  + " Dist = " + (leadCarDist - mopedDist) + " Speed = " + speed);
			leadCarDist += leadCarSpeed;
			mopedDist += speed;
			start.dataHolder.addFirst(new Data(leadCarDist - mopedDist,speed , 0, 0,System.currentTimeMillis()));
			
			
		}

	}

}