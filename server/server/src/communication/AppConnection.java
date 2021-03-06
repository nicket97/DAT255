package communication;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Handles connection and sending + receiving messages from and to the app.
 */

public class AppConnection implements Runnable {

	private int appPort;
	private PropertyChangeSupport pcs;
	private static boolean connected = true;
	private static boolean appConnected = false;
	private JSONObject exceptionMessage;

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
				setAppConnected(true);

				client.setSoTimeout(5000); // If no new message on inputLine in 5 seconds, break.

				while ((inputLine = in.readLine()) != null) {
					pcs.firePropertyChange("new message from app", null, inputLine);
					outputLine = sp.processInput(inputLine);
					out.println(outputLine);
				}
			} catch (IOException e) {
				if (e instanceof SocketTimeoutException) {
					System.out.println("Connection lost.");
				}
				setAppConnected(false);
				pcs.firePropertyChange("new message from app", null, createExceptionMessage().toString());
				System.out.println(
						"Exception caught when trying to listen on port " + appPort + " or listening for a connection");
				System.out.println(e.getMessage());
			}
		}
	}

	private void setAppConnected(boolean b) {
		appConnected = b;
	}

	public static boolean isAppConnected() {
		return appConnected;
	}

	public static boolean isMopedConnected() {
		return connected;
	}

	public static void setMopedConnected(boolean b) {
		connected = b;
	}

	private JSONObject createExceptionMessage() {
		exceptionMessage = new JSONObject();

		try {
			exceptionMessage.put("Velocity", 0);
			exceptionMessage.put("Handling", 0);
			exceptionMessage.put("ACC", false);
			exceptionMessage.put("Platooning", false);
			exceptionMessage.put("Speed", 0.0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exceptionMessage;
	}
}