package acc;

import comunication.MopedStearingHandeler;
import server.Data;
import server.Start;

public class ACCController implements Runnable {
	
	public int distToCar;
	public int oldDist;
	private long oldClock;
	private int targetSpeed;
	private int targetDist;
	
	public ACCController(int targetDist){
		this.targetDist = targetDist;
		
	}
	
	@Override
	public void run() {
		while(true){
		this.targetSpeed = detreminLeadSpeed();
		this.updateMopedSpeed();
		}
		
		
		
	}
	public void updateMopedSpeed(){
		
		MopedStearingHandeler.enginePercentage = this.getACCSpeed(Start.dataHolder.getFirst().getDist(), targetSpeed, targetDist);
		
		
	
	}

	private int getACCSpeed(int dist, int targetSpeed, int targetDist) {
		
		int speed = 0;
		//TODO update max speed
		if(dist > 200){
			speed = 100;
		}
		// TODO uppdate safe distance
		else if(dist < 20){
			speed = 0;
		}
		else{
			double k = (double)(200-targetDist)/(double)(100-targetSpeed);
			speed = (int) Math.ceil((100 + (dist - 200)/k)) ;
		}
		
		return speed;
	}
	public void setTargetSpeed(int targetSpeed){
		this.targetSpeed = targetSpeed;
	}

	public int detreminLeadSpeed() {
		Data d1 = Start.dataHolder.getFirst();
		Data d2 = Start.dataHolder.get(1);
		int speed = 0;
		long dClock= d1.getTime() - d2.getTime();
		int dDist = d1.getDist() - d2.getDist();
		speed = (int) (dDist*(1000.0/dClock));
		System.out.println("dDist = " + dDist + "  dClock = " + dClock);
		return speed;
	}
}