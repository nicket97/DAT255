package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.DATA_CONVERSION;

import acc.ACCController;
import server.Data;

public class ACCSimulationTest {
	
	Thread car;
	@Before
	public void setup(){
		Data.dist = 200;
		car = new Thread(new ACCController(50));
		Data.speed = 0;
		
		
	}
	@Test
	public void testACC(){
		car.start();
		Thread t = new Thread(new ACCSimulator());
		t.start();
		while(true){
			
		}
	}
	
}
