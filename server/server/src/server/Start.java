package server;

import java.util.Scanner;

public class Start {
	public static Data dataHolder;
	public static DataPublisher dataPublisher;
	public static DataReader dataReader;
	public static ImageInput imageInput;
	public static ThreadManager threadManager;
	public static Input input;
	public static Output output;
	
	private String mopedIP;
	private String mopedPort;
	
	
	public static void main(String[] args) {
		dataHolder = new Data();
		dataPublisher = new DataPublisher();
		dataReader = new DataReader();
		threadManager = new ThreadManager();
		input = new Input(12345);
		output = new Output(12345);
		
		
		//Input hej = new Input(9000); 
<<<<<<< HEAD
		Output hej2 = new Output(9000);
		
		// TODO Auto-generated method stub
		System.out.println("hej");
		
		
=======
		//Output hej2 = new Output(9000);		
>>>>>>> c8167b64b07aa72fc4f19fe76e4ebc883921a33c
	}
	
	public void getConnectionDetails(){
		Scanner s = new Scanner(System.in);
		System.out.println("Input IP: ");
		mopedIP = s.nextLine();
<<<<<<< HEAD
		
=======
		if(validateIP(mopedIP)){
>>>>>>> c8167b64b07aa72fc4f19fe76e4ebc883921a33c
		System.out.println("Input port: ");
		mopedPort = s.nextLine();
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
	

}
