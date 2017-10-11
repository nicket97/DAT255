package test;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.junit.Before;
import org.junit.Test;
import server.AppConnection;

public class TestAppConnection implements PropertyChangeListener {
	private AppConnection input;
	private PrintWriter out;
	private BufferedReader in;
	private Socket socket;


	@Before
	public void setup(){
		input = new AppConnection(9000, this);
		Thread t = new Thread(input);
		t.start();
		socket = new Socket();
	}
	
	
	
	@Test
	public void connectionTest(){
		System.out.println("Running test fr�n app server comunication");
		InetSocketAddress inetSocketAddres = new InetSocketAddress("localhost", 9000);
		try {
			socket.connect(inetSocketAddres);
			out = new PrintWriter(socket.getOutputStream(), 
	                 true);
			in = new BufferedReader(new InputStreamReader(
	                socket.getInputStream()));
	     
			String s = in.readLine();
			 System.out.println(s);
			 assertEquals(s.equalsIgnoreCase("connected") ,true);
		     out.println("Connected app");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
}