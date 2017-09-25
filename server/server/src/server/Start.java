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
	private int mopedPort;
	
	
	public static void main(String[] args) {
		Start s = new Start();
	}
	public Start(){
		this.getConnectionDetails();
		input = new Input(9000);
		dataHolder = new Data();
		dataPublisher = new DataPublisher();
		dataReader = new DataReader();
		threadManager = new ThreadManager();

		//output = new Output(mopedIP, mopedPort);
		
		
		//Input hej = new Input(9000); 

		//Output hej2 = new Output("192.2433453546",9000);
		
		// TODO Auto-generated method stub
		System.out.println("hej");

		//Input hej = new Input(9000); 

		//Output hej2 = new Output(9000);		

	}
	
	
	public void getConnectionDetails(){
		Scanner s = new Scanner(System.in);
		System.out.println("Input IP: ");
		this.mopedIP = s.nextLine();

		if(validateIP(mopedIP)){

		System.out.println("Input port: ");
		this.mopedPort = Integer.parseInt(s.nextLine());
		
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
