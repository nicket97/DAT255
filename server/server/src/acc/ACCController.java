package acc;

import server.Data;

public class ACCController implements Runnable {
	
	public int distToCar;
	public int oldDist;
	private long oldClock;
	
	public ACCController(int distToCar){
		this.distToCar = distToCar;
	}
	
	@Override
	public void run() {
		
		
		
		
		
		
	}
	public void updateMopedSpeed(){
		//this.detreminLeadSpeed();
		if(Data.dist > 100){
			Data.speed = Data.maxSpeed;
		}
		else if(Data.dist > distToCar){
			Data.speed += 10;
		}
		else if(Data.dist == distToCar){
			Data.speed = Data.speed;
		}
		else if(Data.dist < distToCar){
			Data.speed -= 10;
			
		}
	
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
