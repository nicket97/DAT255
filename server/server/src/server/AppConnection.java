package server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.*;

/***
 * Handles connection and sending + receiving messages from and to the app.
 */

public class AppConnection implements Runnable {

	private int appPort;
	private PropertyChangeSupport pcs;
	private static boolean connected;

	public AppConnection(int port, PropertyChangeListener mainServer) {
		this.appPort = port;
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(mainServer);
	}

	@Override
	public void run() {
		while (true) {
			System.out.println("Connecting on port " + appPort);

			try (ServerSocket s = new ServerSocket(appPort);
					Socket client = s.accept();
					PrintWriter out = new PrintWriter(client.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
				String inputLine, outputLine;
				ServerProtocol sp = new ServerProtocol();
				outputLine = sp.processInput(null);
				out.println(outputLine);
				System.out.println("connected");

				client.setSoTimeout(5000); // If no new message on inputLine in 5 seconds, break.

				while ((inputLine = in.readLine()) != null) {
					pcs.firePropertyChange("new message from app", null, inputLine);
					System.out.println("Server received " + inputLine);
					outputLine = sp.processInput(inputLine);
					out.println(outputLine);
					if (outputLine.equals("Bye."))
						break;
				}
			} catch (IOException e) {
				if (e instanceof SocketTimeoutException)
					System.out.println("Connection lost.");
				System.out.println(
						"Exception caught when trying to listen on port " + appPort + " or listening for a connection");
				System.out.println(e.getMessage());
			}
		}
	}

	public static boolean isMopedConnected() {
		return connected;
	}

	public void setMopedConnected(boolean b) {
		connected = b;
	}
}