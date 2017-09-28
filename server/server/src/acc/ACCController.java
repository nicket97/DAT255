package acc;

import server.Data;

public class ACCController implements Runnable {
	
	public int distToCar;
	public int oldDist;
	private long oldClock;
	private int targetSpeed;
	private int targetDist;
	
	public ACCController(int distToCar, int targetSpeed, int targetDist){
		this.distToCar = distToCar;
		this.targetSpeed = targetSpeed;
		this.targetDist = targetDist;
		
	}
	
	@Override
	public void run() {
		
		
		
		
		
		
	}
	public void updateMopedSpeed(){
		
		//this.detreminLeadSpeed();
		Data.speed = getACCSpeed(Data.dist, targetSpeed, targetDist);
		
	
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

	public int detreminLeadSpeed(int dist) {
		System.out.println("==================================== " + Thread.currentThread().getName());
		int speed = 0;
		long clock = System.currentTimeMillis();
		long dClock = clock - oldClock;
		int dDist = dist - oldDist;
		speed = (int) (dDist*(1000.0/dClock));
		System.out.println("dDist = " + dDist + "  dClock = " + dClock);
		oldClock = clock;
		oldDist = dist;
		return speed;
	}
}
