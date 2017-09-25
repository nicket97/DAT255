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
public class TestValidateIp {
	private String ip;
	private boolean expected;
	private Start start;
	
	@Before
	public void setup(){
		start = new Start();
	}
	
	public TestValidateIp(String ip, boolean expected) {
        this.ip = ip;
        this.expected = expected;
    }
	
	@Parameterized.Parameters
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {"127.0.0.1", true},
                {"255.255.255.255", true},
                {"123.154.111.10", true},
                {"26.10.2.10", true},
                {"2222.22.22.22", false},
                {"a.a.a.1a", false},
                {"22.222.2.999", false},
                {"a", false},
                {"", false},
        });
    }

	@Test
	public void ipTest(){
		System.out.println("Running test for: "+ip);
		assertEquals(start.validateIP(ip), expected);
	}
}