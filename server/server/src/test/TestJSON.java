package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import server.Data;

@RunWith(Parameterized.class)
public class TestJSON {
	private Data testData;
	private String s;
	private double d;
	private double e;
	private double f;

	public TestJSON(String s, double d, double e, double f) {
		this.s = s;
		this.d = d;
		this.e = e;
		this.f = f;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> getTestData() {
		return Arrays.asList(new Object[][] { {
				"{\"inspeed_avg\":\"123456\", \"fodometer\":\"134\", \"odometer\":\"976\", \"can_ultra\":\"2.2\",\"can_speed\":\"45\",\"can_steer\":\"50\",\"timestamp\":\"100\"}",
				123456, 2.2, 45 }, });
	}

	@Test
	public void testJSON() {
		testData = new Data(s);
		assertEquals(d, testData.getSpeed(), 0.001);
		assertEquals(e, testData.getDist(), 0.001);
		assertEquals(f, testData.getEngineSpeed(), 0.001);
	}

}