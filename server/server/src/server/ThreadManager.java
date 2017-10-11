package server;

public class ThreadManager {
	
	Thread appThread;
	Thread imgThread;
	Thread dataThread;
	Thread dataOutThread;
	
	public ThreadManager(){
		//Thread dataPublisherThread = new Thread();
		//Thread dataReaderThread = new Thread();
		//Thread imageInputThread = new Thread();
		appThread = new Thread(Start.start.appConnection);
		imgThread = new Thread(Start.start.imageInput);
		dataThread = new Thread(Start.start.dataInput);
		dataOutThread = new Thread(Start.start.dataOutput);
		
		//dataPublisherThread.start();
		//dataReaderThread.start();
		//imageInputThread.start();
		initThreads();
		
	}
	public void initThreads(){
		//appThread.start();
		//imgThread.start();
		//dataThread.start();
		dataOutThread.start();
	}



}
