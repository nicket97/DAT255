package server;

import communication.AppConnection;

/**
 * Protocol that creates echo messages that are to be sent to the app. If the
 * connection with the moped is lost it sends the app an error message.
 */
public class ServerProtocol {

	boolean connected = true;
	private static final int WAITING = 0;
	private static final int CONNECTED = 1;

	private int state = WAITING;

	public String processInput(String theInput) {
		String theOutput = null;

		if (state == WAITING) {
			theOutput = "Send over data.";
			state = CONNECTED;
		} else if (state == CONNECTED) {
			if (AppConnection.isMopedConnected())
				theOutput = "Data received.";
			else
				theOutput = "Connection to moped lost.";
		}
		return theOutput;
	}
}