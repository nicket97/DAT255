package server;

import org.json.JSONException;
import org.json.JSONObject;

import comunication.MopedSteeringHandler;

/***
 * This class interprets the JSON object sent from the app that contains
 * steering signal and whether ACC and platooning are active or not.
 */
public class InputInterpreter {

	private int velocity;
	private int handling;
	private boolean acc;
	private boolean platooning;
	private double speed;

	public InputInterpreter(String input) {
		try {
			JSONObject json = new JSONObject(input);
			this.velocity = json.getInt("Velocity"); // speed
			this.handling = json.getInt("Handling"); // horizontal steering
			this.acc = json.getBoolean("ACC"); // is acc active?
			this.platooning = json.getBoolean("Platooning"); // is platooning active?
			this.speed = json.getDouble("Speed"); // cruise control speed
			
			//MopedSteeringHandler.setHandling(handling);
			//MopedSteeringHandler.setVelocity(velocity);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean startACC() {
		return acc;
	}

	public boolean startPlatooning() {
		return platooning;
	}

	public int getVelocity() {
		return velocity;
	}
	
	public int getHandling() {
		return handling;
	}

	public double getSpeed() {
		return speed;
	}
}
