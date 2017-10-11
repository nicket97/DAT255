package test;
 
 import static org.junit.Assert.assertEquals;
 
 import org.junit.Before;
 import org.junit.Test;
 import server.Start;
 
 public class ACCSimulationTest {
 	
	Thread car;
 	@Before
 	public void setup(){
 		
 		
 		
 	}
 	@SuppressWarnings("deprecation")
 	@Test
 	public void testACC(){
 		int targetSpeed = 50;
 		int targetDist = 50;
 		int startDist = 200;
 		ACCSimulator acc = new ACCSimulator(targetSpeed,startDist,targetDist);
 		Thread t = new Thread(acc);
 		t.start();
 		
 		while(true){
 			try {
 				Thread.sleep(1000);
 			} catch (InterruptedException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			//System.out.println("Dist = " + Data.dist + " speed = " + Data.speed);
 			if(Start.start.dataHolder.getFirst().getDist() == targetDist && Start.start.dataHolder.getFirst().getSpeed() == targetSpeed){
 				assertEquals(true, true);
 				t.stop();
 				break;
 				
 			}
 		}
 		
 		
 		targetSpeed = 30;
 		targetDist = 80;
 		startDist = 100;
 		ACCSimulator acc2 = new ACCSimulator(targetSpeed,startDist,targetDist);
 		Thread t2 = new Thread(acc2);
 		t2.start();
 		
 		while(true){
 			try {
 				Thread.sleep(1000);
 			} catch (InterruptedException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			//System.out.println("Dist = " + Data.dist + " speed = " + Data.speed);
 			if(Start.start.dataHolder.getFirst().getDist() == targetDist && Start.start.dataHolder.getFirst().getSpeed() == targetSpeed){
 				assertEquals(true, true);
 				break;
 			}
 		}
 		
 	}
 	
 	
 }