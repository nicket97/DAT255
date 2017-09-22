package test;

import static org.junit.Assert.*;
import org.junit.Test;
import server.Start;

public class TestValidateIp {

	@Test
	public void test(){
		Start start = new Start();
		assertTrue(start.validateIP("127.0.0.1"));
		assertTrue(start.validateIP("255.255.255.255"));
		assertTrue(start.validateIP("132.254.111.10"));
		assertTrue(start.validateIP("26.10.2.10"));
		assertFalse(start.validateIP("2222.22.22.22"));
		assertFalse(start.validateIP("a.a.a.a"));
		assertFalse(start.validateIP("222.222.2.999"));
		assertFalse(start.validateIP("10.10.10"));
		assertFalse(start.validateIP("hej"));
	}
}
