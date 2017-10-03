package server;

public class ThreadManager {
	
	public ThreadManager(){
		//Thread dataPublisherThread = new Thread();
		//Thread dataReaderThread = new Thread();
		//Thread imageInputThread = new Thread();
		Thread appThread = new Thread(Start.appConnection);
		Thread imgThread = new Thread(Start.imageInput);
		Thread dataThread = new Thread(Start.dataInput);
		
		//dataPublisherThread.start();
		//dataReaderThread.start();
		//imageInputThread.start();
		appThread.start();
		imgThread.start();
		dataThread.start();
	}



}
