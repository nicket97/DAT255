package server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Scanner;

import model.FixedDataQue;

public class Start implements PropertyChangeListener {
	public static FixedDataQue dataHolder;
	public static DataPublisher dataPublisher;
	public static DataReader dataReader;
	public static ImageInput imageInput;
	public static ThreadManager threadManager;
	public static AppConnection input;
	public static Output output;
	
	private String mopedIP;
	private int mopedPort;
	private int serverPort;
	
	
	public static void main(String[] args) {
		Start s = new Start();
	}
	public Start(){
		this.getConnectionDetails();
		input = new AppConnection(serverPort, this);
		output = new Output(mopedIP, mopedPort);
		dataHolder = new FixedDataQue(10);
		dataPublisher = new DataPublisher();
		dataReader = new DataReader();
		threadManager = new ThreadManager();

		
		
		
		//Input hej = new Input(9000); 

		//Output hej2 = new Output("192.2433453546",9000);
		
		// TODO Auto-generated method stub
		//System.out.println("hej");

		//Input hej = new Input(9000); 

		//Output hej2 = new Output(9000);		

	}
	
	
	public void getConnectionDetails(){
		Scanner s = new Scanner(System.in);
		System.out.println("Input IP: ");
		this.mopedIP = s.nextLine();

		if(validateIP(mopedIP)){

		System.out.println("Input moped port: ");
		this.mopedPort = Integer.parseInt(s.nextLine());
		
		if(validatePort(mopedPort)){
			
			System.out.println("Input server port: ");
			this.serverPort = Integer.parseInt(s.nextLine());

			if (validatePort(serverPort)){
				System.out.println("Valid moped port, server port and IP");
			}
			
			else { 
				System.out.println("The server port is not valid");
			}
		}
		
		}
		else{
			System.out.println("The IP is not valid");
			this.getConnectionDetails();
		}
		s.close();
	}
	
	public boolean validateIP(final String ip){
		String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
	    return ip.matches(ipPattern);
	}
	
	public boolean validatePort(int port){
		int portNumber = port;
		if (portNumber < 80 && portNumber > 65535){
			System.out.println("Port was not validated");
			return false;
		}
		if (mopedPort == serverPort){
			return false;
		}
		return true;
		
	}
	@Override
	public void propertyChange(PropertyChangeEvent arg) {
		System.out.println("RECEIVED EVENT");
		System.out.println(InputInterpreter.interpretString(arg.getPropertyName().toString()));
		if(arg.getPropertyName().equals("message_received")) {
			System.out.println("App sent a message");
		}
		
	}
	

}
