package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import server.Start;

@RunWith(Parameterized.class)
public class TestServerProtocol {
	private String inputLine;
	private String outputLine;
	private Start start;
	
	@Before
	public void setup() {
		start = new Start();		
	}

	public TestServerProtocol(String inputLine, String outputLine) {
		this.inputLine = inputLine;
		this.outputLine = outputLine;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> getTestData() {
		return Arrays.asList(new Object[][] { 
			{ "Hej", "Data received." }, 
			{ "Hello", "Data received." },
			{ "12345", "Data received." },
			{ "    ", "Data received." }  });
	}

	@Test
	public void protocolTest() {
		System.out.println("Running test for: " + inputLine);
		DummyClient dc = new DummyClient("localhost", 8080);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(dc.connectAndSend(inputLine), outputLine);
		System.out.println("Test for " + inputLine + " done.");
		System.out.println(" ");
	}
}