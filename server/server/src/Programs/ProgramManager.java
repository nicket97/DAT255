package Programs;


import Programs.acc.ACCController;
import server.Start;

/**
 * Handles threads for ACC and platooning. Also has methods that can be called
 * to know if ACC or platooning are active on the moped.
 */
public class ProgramManager {
	private static boolean programActive = false;
	private static boolean ACCActive = false;
	private static boolean platooningActive = false;
	private static Thread accThread = null;
	static Thread platoonThread = null;

	public static void startACC(int dist) {
		if (!ACCActive) {
			accThread = new Thread(new ACCController(dist));
			accThread.start();
			ACCActive = true;
		}
	}


	@SuppressWarnings("deprecation")
	public static void stopACC() {
		if (ACCActive ) {
			accThread.stop();
			accThread = null;
			ACCActive = false;
		}
	}
	
	public static void startPlatooning(){
		if(!platooningActive){
		platoonThread = new Thread(Start.imgInput);
		platoonThread.start();
		platooningActive = true;


		}
	}

	@SuppressWarnings("deprecation")
	public static void stopPlatooning() {
		if (platooningActive) {
			platooningActive = false;
			platoonThread.stop();
			platoonThread = null;
		}
	}

	public static boolean isACCActive() {
		return ACCActive;
	}

	public static boolean isPlatooningActive() {
		return platooningActive;
	}

	public static boolean isProgramActive() {
		return programActive;
	}
}
