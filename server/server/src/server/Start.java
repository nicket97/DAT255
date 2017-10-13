package server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Scanner;

import camera.ImageRecognition;
import comunication.MopedSteeringHandler;
import model.FixedDataQueue;

public class Start implements PropertyChangeListener {
	public FixedDataQueue dataHolder;
	public DataPublisher dataPublisher;
	public DataReader dataReader;
	public static ImageInput imageInput;
	public ThreadManager threadManager;
	public static AppConnection appConnection;
	public MopedImgConnection imgInput;
	public static MopedDataConnection dataInput;
	public static MopedOutputConnection dataOutput;
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
		appConnection = new AppConnection(8080, this);

		// imgInput = new MopedImgConnection("192.168.43.183", 3500, this);
		// imgInput.run();
		dataInput = new MopedDataConnection("192.168.43.230", 9999, this);
		// dataOutput = new MopedOutputConnection("192.168.43.183", 9000);

		init();
	}

	public Start(boolean testcase) {
		init();
	}

	public void init() {
		dataHolder = new FixedDataQueue(10);
		dataPublisher = new DataPublisher();
		dataReader = new DataReader();
		threadManager = new ThreadManager();
		img = new ImageRecognition();
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

	// TODO Fix this logic
	@Override
	public void propertyChange(PropertyChangeEvent arg) {
		System.out.println("RECEIVED EVENT");
		if (arg.getPropertyName().equals("new message from app")) {
			input = new InputInterpreter(arg.getNewValue().toString());
			if (input.startACC())
				ProgramManager.startACC(10); // TODO Change to proper value after testing
			else if (input.startPlatooning())
				ProgramManager.startPlatooning();
			else if (ProgramManager.ACCActive)
				ProgramManager.stopACC();
			else if (ProgramManager.platooningActive)
				ProgramManager.stopPlatooning();
			MopedSteeringHandler.setVelocity(input.getVelocity());
			MopedSteeringHandler.setHandling(input.getHandling());
			System.out.println("App sent a message: " + arg.getNewValue());
		} else if (arg.getPropertyName().equals("new data from moped")) {
			System.out.println("New data from moped: " + arg.getNewValue());
			dataHolder.addFirst(new Data(arg.getNewValue().toString()));
		} else if (arg.getPropertyName().equals("new image")) {
			img.locateImage(arg.getNewValue());
			System.out.println("new image received from moped");
		} else if (arg.getPropertyName().equals("connection lost")) {
			appConnection.setMopedConnected(false);
		}
	}
}
