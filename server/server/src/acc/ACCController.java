package acc;

import communication.MopedSteeringHandler;
import server.Data;
import server.Start;

/**
 * Responsible for all ACC calculations. Sends steering values to
 * MopedSteeringHandler.
 */
public class ACCController implements Runnable {

	public int distToCar;
	public int oldDist;
	private int targetSpeed;
	private int targetDist;

	/**
	 * 
	 * @param targetDist2 The targetdist we want to be away from the moped infront
	 */
	public ACCController(int targetDist2) {
		this.targetDist = targetDist2;

	}
	/**
	 * 
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.targetSpeed = detreminLeadSpeed();
			this.updateMopedSpeed();
		}

	}
/**
 * is called by the loop and sets the velocity to send to the moped
 */
	public void updateMopedSpeed() {

		MopedSteeringHandler.velocity = this.getACCSpeed(Start.start.dataHolder.getFirst().getDist(), targetSpeed,
				targetDist, false) ;
		// MopedSteeringHandler.handling = -50;
	}
/**
 * Converts the speed in cm/s to a engine steering value
 * @param speed
 * @return engine steering value
 */
	public int convertSpeedToESCValue(int speed) {
		int value;
		value = (speed - 15) / 2;
		return value;
	}
/**
 * Uses the target speed to create a function from the dist and gives the speed we want to be driving
 * 
 * @param dist
 * @param targetSpeed
 * @param targetDist
 * @return
 */
	public int getACCSpeed(double dist, int targetSpeed, int targetDist, boolean test) {
		if(!test){
		dist *= 100;
		}
		int speed = 0;

		// TODO update max speed
		if (dist > 200) {
			speed = 100;

		}
		// TODO uppdate safe distance
		else if (dist < 30) {

		} 
		else {
			double k = (double) (200 - targetDist) / (double) (100 - targetSpeed);
			if ((100 + (dist - 200) / k) > targetSpeed) {
				speed = (int) Math.ceil((100 + (dist - 200) / k));
			} else if ((100 + (dist - 200) / k) < targetSpeed) {
				speed = (int) Math.round((100 + (dist - 200) / k));
			} else {

				speed = targetSpeed;
			}

		}
		if (speed > 20 && !test)
			speed = 20;
		return speed;
	}
/**
 * Sets the speed we want to target
 * @param targetSpeed
 */
	public void setTargetSpeed(int targetSpeed) {
		this.targetSpeed = targetSpeed;
	}
/**
 * Returns the lead speed of the moped in front by calculating the difference in distance and adding it to our current speed
 * @return speed of the moped in front of us
 */
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
