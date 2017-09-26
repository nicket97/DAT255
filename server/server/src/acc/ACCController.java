package acc;

import server.Data;

public class ACCController implements Runnable {
	
	public int distToCar;
	
	public ACCController(int distToCar){
		this.distToCar = distToCar;
	}
	
	@Override
	public void run() {
		
		
		
	}
	public void updateMopedSpeed(){
		
		if(Data.dist > 100){
			Data.speed = Data.maxSpeed;
		}
		else if(Data.dist > distToCar){
			Data.speed += 10;
		}
		else if(Data.dist == distToCar){
			Data.speed = Data.speed;
		}
		else if(Data.dist < distToCar){
			Data.speed -= 10;
			
		}
		
	}
}
