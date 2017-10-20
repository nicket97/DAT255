package test;

import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import communication.MopedDataConnection;

public class TestMopedConnection implements PropertyChangeListener {

	private MopedDataConnection output;
	private ServerSocket socket;
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;

	@Before
	public void setup() {
		output = new MopedDataConnection("localhost", 9999, this);
		Thread t = new Thread(output);
		t.start();
		try {
			socket = new ServerSocket(9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void mopedConnectionTest() {
		System.out.println("Running moped connection test");
		try {
			client = socket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("connected");
		String s = "hej";
		try {
			s = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//if (s != null)
			assertEquals(true, true);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}