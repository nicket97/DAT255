package server;
/***
 * Manages all threads that are needed for connecting to the MOPED and the app.
 */
public class ThreadManager {

	Thread appThread;
	Thread imgThread;
	Thread dataThread;
	Thread dataOutThread;

	public ThreadManager() {
		// Thread dataPublisherThread = new Thread();
		// Thread dataReaderThread = new Thread();
		// Thread imageInputThread = new Thread();
		appThread = new Thread(Start.appConnection);
		imgThread = new Thread(Start.imageInput);
		dataThread = new Thread(Start.dataInput);
		dataOutThread = new Thread(Start.dataOutput);

		// dataPublisherThread.start();
		// dataReaderThread.start();
		// imageInputThread.start();
		initThreads();

	}

	public void initThreads() {
		 appThread.start();
		// imgThread.start();
		// dataThread.start();
		//dataOutThread.start();
	}

}
