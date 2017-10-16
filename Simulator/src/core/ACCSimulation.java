package core;

import java.util.Random;

public class ACCSimulation implements Runnable {
	
	Moped moped;
	int posx = 0; //position in centimeters from origin 0
	int leadSpeed;
	private Random rng = new Random();
	public ACCSimulation(Moped moped, int leadSpeed, int startDist){
		this.moped = moped;
		this.leadSpeed = leadSpeed;
		this.posx = startDist;
	}
	@Override
	public void run() {
		
		while(true){
			
			try {


                posx = randomizeX(posx);
				Thread.sleep(200); //5 hz update frequency
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			posx += leadSpeed/10;
			//moped.setSensors();
		}
		
	}

	private int randomizeX(int posx) {
	    int dX = rng.nextInt(20) + 1; // to change!
        int bool = rng.nextInt(1);

        //randomize if dX should be negative or positive
        if (bool == 0) {
            dX = -dX;
        }

        posx = posx + dX;

        return posx;
    }
}
