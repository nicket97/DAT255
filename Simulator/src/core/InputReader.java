package core;

import org.json.JSONException;
import org.json.JSONObject;

import main.Start;

public class InputReader {

	public static void readInput(String readLine) {
		try {
			JSONObject json = new JSONObject(readLine);
			Start.start.moped.can_speed = json.getInt("velocity");
			Start.start.moped.can_steer = (int) json.getInt("steering");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
