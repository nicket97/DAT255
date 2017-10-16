package test;

import acc.ACCController;
import server.Data;
import server.FixedDataQueue;
import server.ProgramManager;
import server.Start;

public class ACCSimulator implements Runnable {
	public int leadCarSpeed ;
	public int leadCarDist;
	public int mopedDist = 0;
	public int targetDist;
	ProgramManager pm;
	
	
	public ACCSimulator(int leadSpeed, int leadDist, int targetDist){
		Start.setStart(new Start(true));
		this.leadCarSpeed = leadSpeed;
		this.leadCarDist = leadDist;
		this.targetDist = targetDist;
		 pm = new ProgramManager();
		 

	}
	@Override
	public void run() {
		ACCController acc = new ACCController(targetDist);
		Start.start.dataHolder.addFirst(new Data(leadCarDist - mopedDist,0 , 0, 0,System.currentTimeMillis()-1000));
		Start.start.dataHolder.addFirst(new Data(leadCarDist + leadCarSpeed - mopedDist,0, 0, 0,System.currentTimeMillis()));
		while(true){
			System.out.println(Start.start.dataHolder.getFirst().toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("=================================");
			
			Data d = Start.start.dataHolder.getFirst();
			
			
			
			int targetSpeed = (int) (d.speed + acc.detreminLeadSpeed());
			//targetSpeed = 50;
			System.out.println(targetSpeed + "           " + d.getSpeed() + "             " + d.dist);
			
			int speed = acc.getACCSpeed(d.getDist(), targetSpeed, this.targetDist);
			
			System.out.println("leadDist = " + leadCarDist + "  mopedDist = " + mopedDist  + " Dist = " + (leadCarDist - mopedDist) + " Speed = " + speed);
			leadCarDist += leadCarSpeed;
			mopedDist += speed;
			Start.start.dataHolder.addFirst(new Data(leadCarDist - mopedDist,speed , 0, 0,System.currentTimeMillis()));

			
		}

	}

}