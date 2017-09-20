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
		//Input hej = new Input(9000); 
		Output hej2 = new Output(9000);
		
	}
	
	public void getConnectionDetails(){
		Scanner s = new Scanner(System.in);
		System.out.println("Input IP: ");
		mopedIP = s.nextLine();
		System.out.println("Input port: ");
		mopedPort = s.nextLine();
	}
	

}
