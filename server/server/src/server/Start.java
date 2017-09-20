package server;

public class Start {
	public static Data dataHolder;
	public static DataPublisher dataPublisher;
	
	public static void main(String[] args) {
		dataHolder = new Data();
		dataPublisher = new DataPublisher();
		// TODO Auto-generated method stub
		System.out.println("hej");
		//Input hej = new Input(9000); 
		Output hej2 = new Output(9000);
	}

}
