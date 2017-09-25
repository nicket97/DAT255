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
		Output hej2 = new Output(9000);
		
		// TODO Auto-generated method stub
		System.out.println("hej");
		
		
	}
	
	public void getConnectionDetails(){
		Scanner s = new Scanner(System.in);
		System.out.println("Input IP: ");
		mopedIP = s.nextLine();
		
		System.out.println("Input port: ");
		mopedPort = s.nextLine();
	}
	

}
