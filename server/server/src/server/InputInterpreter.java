package server;

import org.json.JSONException;
import org.json.JSONObject;

/***
 * This class interprets the JSON object sent from the app that contains
 * steering signal and whether ACC and platooning are active or not.
 */
public class InputInterpreter {

	private String signal;
	private boolean acc;
	private boolean platooning;
	private double speed;

	public InputInterpreter(String input) {
		try {
			JSONObject json = new JSONObject(input);
			this.signal = json.getString("Steering"); // steering signal
			this.acc = json.getBoolean("ACC"); // is acc active?
			this.platooning = json.getBoolean("Platooning"); // is platooning active?
			this.speed = json.getDouble("Speed"); // cruise control speed
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

	public String getSignal() {
		return signal;
	}

	public double getSpeed() {
		return speed;
	}
}
