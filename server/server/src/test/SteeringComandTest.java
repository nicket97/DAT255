package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import comunication.MopedSteeringHandler;
import server.InputInterpreter;

public class SteeringComandTest {
	
	@Before
	public void setup(){
		
	}
	
	@Test
	public void TestSteering(){
		JSONObject json = new JSONObject();
		try {
			json.put("ACC", false);
			json.put("Platooning", false);
			json.put("Speed", 40);
			json.put("Velocity", 49);
			json.put("Handling", 36);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputInterpreter in = new InputInterpreter(json.toString());
		assertEquals(in.getHandling() == 36, true);
		assertEquals(in.getVelocity() == 49, true);
		assertEquals(MopedSteeringHandler.getHandling() == 36, true);
		assertEquals(MopedSteeringHandler.getVelocity() == 49, true);
		
	}
}
