package server;

public class ThreadManager {
	
	Thread appThread;
	Thread imgThread;
	Thread dataThread;
	public ThreadManager(){
		//Thread dataPublisherThread = new Thread();
		//Thread dataReaderThread = new Thread();
		//Thread imageInputThread = new Thread();
		appThread = new Thread(Start.appConnection);
		imgThread = new Thread(Start.imageInput);
		dataThread = new Thread(Start.dataInput);
		
		//dataPublisherThread.start();
		//dataReaderThread.start();
		//imageInputThread.start();
		
	}
	public void initThreads(){
		appThread.start();
		imgThread.start();
		dataThread.start();
	}



}
