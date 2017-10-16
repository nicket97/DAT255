package main;

import java.util.Scanner;

import core.Conection;
import core.Moped;
import core.SimulationHandler;

public class Start {
	public Moped moped;
	public static Start start;
	public SimulationHandler simulationHandler;
	
	public static void main(String [] args){
		start = new Start();
	}
	
	public Start(){
		moped = new Moped();
		Conection c = new Conection();
		Thread t = new Thread(c);
		t.start();
		Scanner s = new Scanner(System.in);
		while(true){
			System.out.println("Enter a comand");
			String in = s.nextLine();
			if(in.equalsIgnoreCase("ACC start")){
				simulationHandler.startACCSimulator();
			}
			else if(in.equalsIgnoreCase("ACC stop")){
				simulationHandler.stopACCSimulator();
			}
			else if (in.equalsIgnoreCase("platoning start")){
				simulationHandler.startPlatooning();
			}
			else if (in.equalsIgnoreCase("Platoning stop")){
				simulationHandler.stopPlatooning();
			}
		}
		
	}
}
