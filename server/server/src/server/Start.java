package server;

import java.util.Scanner;

public class Start {
	public static Data dataHolder;
	public static DataPublisher dataPublisher;
	
	private static String mopedIP;
	private static String mopedPort;
	
	
	public static void main(String[] args) {
		dataHolder = new Data();
		dataPublisher = new DataPublisher();
		// TODO Auto-generated method stub
		System.out.println("hej");
		Input hej = new Input(9000); 
		//Output hej2 = new Output(9000);		
	}
	
	public void getConnectionDetails(){
		Scanner s = new Scanner(System.in);
		System.out.println("Input IP: ");
		mopedIP = s.nextLine();
		if(validateIP(mopedIP)){
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
