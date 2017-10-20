package test;

import static org.junit.Assert.*;

import org.junit.Test;

import server.FixedDataQueue;
import server.Data;

public class TestFixedDataQueue {
	FixedDataQueue fixedDQ = new FixedDataQueue(5);
	//Data(int dist, int speed, int engineSpeed, int batteryVoltage, long timeStamp){
	Data d1 = new Data(2, 23, 50, 4, 1337);
	Data d2 = new Data(3, 65, 70, 5, 6576);
	@Test
	public void testGetFirst() {
		fixedDQ.addFirst(d1);
		
		Data first = fixedDQ.getFirst();
		
		double firstDist = first.getDist();
		assertTrue(firstDist==2);
		
		fixedDQ.addFirst(d2);
		assertTrue(fixedDQ.get(1).getSpeed()==23);
	}
}