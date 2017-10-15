package core;

public class ACCSimulation implements Runnable {
	
	Moped moped;
	int posx = 0;
	int leadSpeed;
	public ACCSimulation(Moped moped, int leadSpeed, int startDist){
		this.moped = moped;
		this.leadSpeed = leadSpeed;
		this.posx = startDist;
	}
	@Override
	public void run() {
		
		while(true){
			
			try {
				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			posx += leadSpeed/10;
			moped.setSensors();
		}
		
	}

}
