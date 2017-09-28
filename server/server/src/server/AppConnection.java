package server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.*;
import java.util.Observable;

public class AppConnection implements Runnable {

	private int appPort;
	private String message;
	private PropertyChangeSupport pcs;

	public AppConnection(int port, PropertyChangeListener mainServer) {
		this.appPort = port;
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(mainServer);
	}

	@Override
	public void run() {

		System.out.println("Connecting on port " + appPort);

		try (ServerSocket s = new ServerSocket(appPort);
				//TODO Needs better error handling and some more stuff.
				Socket client = s.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
			String inputLine, outputLine;
			ServerProtocol sp = new ServerProtocol();
			outputLine = sp.processInput(null);
			out.println(outputLine);
			System.out.println("connected");

			while ((inputLine = in.readLine()) != null) {
				pcs.firePropertyChange(inputLine, 0, 1);
				System.out.println("Server received " + inputLine);
				outputLine = sp.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("Bye."))
					break;
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + appPort + " or listening for a connection");
			System.out.println(e.getMessage());
		}

	}
}