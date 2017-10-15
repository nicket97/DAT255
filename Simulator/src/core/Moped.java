package core;

import org.json.JSONException;
import org.json.JSONObject;

public class Moped {
	
	int posX = 0;
	int posY = 0;
	int enginePower = 0;
	int stearing = 0;
	
	// sensor data
	private int inspeed_avg = 0;
	private int odometer = 0;
	private int fodometer = 0;
	private int can_ultra = 0;
	private int can_speed = 0;
	private int can_steer = 0;
	
	/*
	inspeed_avg: providing the speed travelled (unit unknown)
	odometer: The distance travelled (unit unknown)
	fodometer: The same as odometer, we think the "f" stands for "front"
	can_ultra: the reading of the ultrasound sensor in m (expect decimal point)
	can_speed: The motor's speed value in the interval [-100,100]
	can_steer: The motor's styring value in the interval [-100,100]
	timestamp: Unix time in milliseconds indicating the time the JSON object was craeted.
	 */
	
	public String getMopedOutput(){
		JSONObject json = new JSONObject();
		
		try {
			json.put("inspeed_avg", inspeed_avg);
			json.put("odometer", odometer);
			json.put("fodometer", fodometer);
			json.put("can_ultra", can_ultra);
			json.put("can_speed", can_speed);
			json.put("can_steer", can_steer);
			json.put("timestamp", System.currentTimeMillis());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
		
	}
	
	public void sendServerSignal(String readLine) {
		// TODO Auto-generated method stub
		
	}

	
}
