package acc;

import comunication.MopedSteeringHandler;
import server.Data;
import server.Start;

public class ACCController implements Runnable {

	public int distToCar;
	public int oldDist;
	private int targetSpeed;
	private int targetDist;

	public ACCController(int targetDist) {
		this.targetDist = targetDist;

	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.targetSpeed = detreminLeadSpeed();
			this.updateMopedSpeed();
		}

	}

	public void updateMopedSpeed() {

		MopedSteeringHandler.velocity = this.getACCSpeed(Start.start.dataHolder.getFirst().getDist(), targetSpeed,
				targetDist);
		// MopedSteeringHandler.handling = -50;
	}

	public int convertSpeedToESCValue(int speed) {
		int value;
		value = (speed - 15) / 2;
		return value;
	}

	public int getACCSpeed(double dist, int targetSpeed, int targetDist) {
		// dist *= 100;
		int speed = 0;
<<<<<<< HEAD
		// TODO update max speed
		if (dist > 200) {
			speed = 100;
=======
		//TODO update max speed
		if(dist > 200){
			speed = 25;
>>>>>>> f6971343f398457b9e4d492c7714a65edb7d4775
		}
		// TODO uppdate safe distance
		else if (dist < 20) {
			speed = 0;
<<<<<<< HEAD
		} else {
			double k = (double) (200 - targetDist) / (double) (100 - targetSpeed);
			if ((100 + (dist - 200) / k) > targetSpeed) {
				speed = (int) Math.ceil((100 + (dist - 200) / k));
			} else if ((100 + (dist - 200) / k) < targetSpeed) {
				speed = (int) Math.round((100 + (dist - 200) / k));
			} else {
=======
		}
		else{
			double k = (double)(200-targetDist)/(double)(230-targetSpeed);
			if((100 + (dist - 200)/k) > targetSpeed){
				speed = (int) Math.ceil((100 + (dist - 200)/k)) ;
			}
			else if ((100 + (dist - 200)/k) < targetSpeed){
				speed = (int) Math.round((100 + (dist - 200)/k)) ;
			}
			else{
>>>>>>> f6971343f398457b9e4d492c7714a65edb7d4775
				speed = targetSpeed;
			}

		}
		if (speed > 100)
			speed = 100;
		return speed;
	}

	public void setTargetSpeed(int targetSpeed) {
		this.targetSpeed = targetSpeed;
	}

	public int detreminLeadSpeed() {
		Data d1 = Start.start.dataHolder.getFirst();
		Data d2 = Start.start.dataHolder.get(1);
		int speed = 0;
		long dClock = d2.getTime() - d1.getTime();
		double dDist = d2.getDist() - d1.getDist();
		speed = (int) (dDist * (1000.0 / dClock));
		System.out.println("dDist = " + dDist + "  dClock = " + dClock + " speed = " + speed);
		return speed;
	}
}
