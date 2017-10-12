package main;

import core.Moped;

public class Start {
	public Moped moped;
	public static Start start;
	
	public static void main(String [] args){
		start = new Start();
	}
	
	public Start(){
		moped = new Moped();
	}
}
