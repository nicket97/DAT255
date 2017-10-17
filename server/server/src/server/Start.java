package server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Scanner;

import camera.ImageRecognition;
import comunication.MopedSteeringHandler;

/**
 * Main class of the server. Initiates all the connections, and validates the
 * given port and ip.
 */
public class Start implements PropertyChangeListener {
	public FixedDataQueue dataHolder;
	public ThreadManager threadManager;
	public static MopedImgConnection imgInput;
	public static AppConnection appConnection;
	public static MopedDataConnection mopedDataInput;
	public InputInterpreter input;
	public ImageRecognition img;

	public Data mopedData;

	private String mopedIP;
	private int mopedPort;
	private int serverPort;
	public static Start start;

	public static void main(String[] args) {
		start = new Start();
		
	}

	public Start() {
		// this.getConnectionDetails();
		appConnection = new AppConnection(8080, start);
		imgInput = new MopedImgConnection("192.168.43.61", 3000, start);
		mopedDataInput = new MopedDataConnection("192.168.43.61", 9999, start);

		init();
	}

	public Start(boolean testcase) {
		init();
	}

	public void init() {
		img = new ImageRecognition();
		dataHolder = new FixedDataQueue(10);
		threadManager = new ThreadManager();
	}

	public static void initConnections() {

	}

	public void getConnectionDetails() {
		Scanner s = new Scanner(System.in);
		System.out.println("Input IP: ");
		this.mopedIP = s.nextLine();

		if (validateIP(mopedIP)) {

			System.out.println("Input moped port: ");
			this.mopedPort = Integer.parseInt(s.nextLine());

			if (validatePort(mopedPort)) {
				System.out.println("Input server port: ");
				this.serverPort = Integer.parseInt(s.nextLine());
				if (validatePort(serverPort)) {
					System.out.println("Valid moped port, server port and IP");
				} else {
					System.out.println("The server port is not valid");
				}
			}

		} else {
			System.out.println("The IP is not valid");
			this.getConnectionDetails();
		}
		s.close();
	}

	/***
	 * Validates the given ip by regex check.
	 * 
	 * @param ip
	 * @return
	 */
	public boolean validateIP(final String ip) {
		String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
		return ip.matches(ipPattern);
	}

	/***
	 * Validates the given port by checking that it is in range.
	 * 
	 * @param port
	 * @return
	 */
	public boolean validatePort(int portNumber) {

		if (portNumber < 80 || portNumber > 65535) {
			System.out.println("Port was not validated");
			return false;
		}
		if (mopedPort == serverPort) {
			return false;
		}
		return true;

	}

	/**
	 * Method that handles messages sent from the app and data/images sent from the
	 * moped. Forwards the data to classes that can use them in calculations.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent arg) {
		if (arg.getPropertyName().equals("new message from app")) {
			input = new InputInterpreter(arg.getNewValue().toString());
			if (input.startACC()) {
				ProgramManager.startACC(30); // TODO Change to proper value after testing
			} else {
				ProgramManager.stopACC();
			}
			if (input.startPlatooning()) {
				ProgramManager.startPlatooning();
			} else {
				ProgramManager.stopPlatooning();
			}
			if (!ProgramManager.isACCActive())
				MopedSteeringHandler.setVelocity(input.getVelocity());
			if (!ProgramManager.isPlatooningActive())
				MopedSteeringHandler.setHandling(input.getHandling());
			System.out.println("App sent a message: " + arg.getNewValue());
		} else if (arg.getPropertyName().equals("new data from moped")) {
			System.out.println("New data from moped: " + arg.getNewValue());
			Data d = new Data(arg.getNewValue().toString());
			if (d.dist < 6) {
				dataHolder.addFirst(d);
			}
		} else if (arg.getPropertyName().equals("new image")) {
			System.out.println("started!!!");
			if (ProgramManager.isPlatooningActive()) {
				MopedSteeringHandler.setHandling(new ImageRecognition().locateImage(arg.getNewValue()));
			} else {
				((File) arg.getNewValue()).delete();
			}
			System.out.println("new image received from moped");
		} else if (arg.getPropertyName().equals("connection lost")) {
			appConnection.setMopedConnected(false);
		}
	}

	public static void setStart(Start start2) {
		start = start2;
	}
}
