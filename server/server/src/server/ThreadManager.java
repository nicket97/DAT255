package server;

public class ThreadManager {
	
	public ThreadManager(){
		//Thread dataPublisherThread = new Thread();
		//Thread dataReaderThread = new Thread();
		//Thread imageInputThread = new Thread();
		Thread inputThread = new Thread(Start.input);
		//Thread outputThread = new Thread(Start.output);
		
		//dataPublisherThread.start();
		//dataReaderThread.start();
		//imageInputThread.start();
		inputThread.start();
		//outputThread.start();
	}



}
