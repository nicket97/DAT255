package server;

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
		} else {
			theOutput = "Bye.";
			state = WAITING;
		}
		return theOutput;
	}
}