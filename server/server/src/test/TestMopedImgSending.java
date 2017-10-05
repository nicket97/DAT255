package test;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import server.MopedImgConnection;
import server.Start;

@RunWith(Parameterized.class)
public class TestMopedImgSending {
	private int i;
	private int expected;
	private DummyServer server;
	private MopedImgConnection client;
	private static Thread t;

	@Before
	public void setup() throws ClassNotFoundException, IOException, SQLException, Exception {
		server = new DummyServer(8080);
		server.connect();
		client = new MopedImgConnection("localhost", 8080, null);
	}


	public TestMopedImgSending(int i, int expected) {
		this.i = i;
		this.expected = expected;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> getTestData() {
		return Arrays.asList(new Object[][] { { 0, 1 }, { 1, 2 }, });
	}

	@Test
	public void imgSendTest() throws AWTException, IOException {
		System.out.println("Running test for: " + i);
		client.run();
		//server.connect();
		//client.run();
		assertEquals(server.send(i), expected);
		server.close();
		System.out.println("Test for " + i + " done.");
		System.out.println(" ");
	}
}