package server;

/***
 * Manages all threads that are needed for connecting to the MOPED and the app.
 */
public class ThreadManager {

	Thread appThread;
	Thread imgThread;
	Thread dataThread;

	public ThreadManager() {
		// Thread dataPublisherThread = new Thread();
		// Thread dataReaderThread = new Thread();
		appThread = new Thread(Start.appConnection);
		//imgThread = new Thread(Start.imgInput);
		dataThread = new Thread(Start.mopedDataInput);

		initThreads();
	}

	public void initThreads() {
		appThread.start();
		//imgThread.start();
		dataThread.start();

		// dataPublisherThread.start();
		// dataReaderThread.start();
	}

}
