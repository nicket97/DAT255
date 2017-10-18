package server;

/***
 * Manages all threads that are needed for connecting to the MOPED and the app.
 */
public class ThreadManager {

	Thread appThread;
	Thread imgThread;
	Thread dataThread;
	Thread guiThread;

	public ThreadManager() {
		appThread = new Thread(Start.appConnection);
		imgThread = new Thread(Start.imgInput);
		dataThread = new Thread(Start.mopedDataInput);
		//guiThread = new Thread(Start.gui);
		initThreads();
	}

	public void initThreads() {
		System.out.println("starting Threads");
		//guiThread.start();
		appThread.start();
		imgThread.start();
		dataThread.start();
		
	}

}
