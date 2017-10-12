package server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import comunication.MopedSteeringHandler;

/***
 * Receives data sent from moped. Notifies Start when a new value is received.
 * Responds with a value set in MopedSteeringHandler.
 */
public class MopedDataConnection implements Runnable {

	private String hostname;
	private int port;
	private PropertyChangeSupport pcs;
	private PropertyChangeSupport pcsConnected;

	public MopedDataConnection(String hostname, int port, PropertyChangeListener mainServer) {
		this.hostname = hostname;
		this.port = port;
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(mainServer);
		pcsConnected = new PropertyChangeSupport(this);
		pcsConnected.addPropertyChangeListener(mainServer);
	}

	@Override
	public void run() {
		try (Socket client = new Socket(hostname, port);
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
			while (true) {
				String inputLine = in.readLine();
				System.out.println("Server received " + in);
				out.println(MopedSteeringHandler.getSteeringCommand());
				pcs.firePropertyChange("new data from moped", null, inputLine);
			}
		} catch (UnknownHostException e) {
			pcsConnected.firePropertyChange("connection lost", null, "connection lost");
			System.err.println("Don't know about host " + hostname);
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
