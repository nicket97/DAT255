package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import comunication.MopedSteeringHandler;

public class MopedOutputConnection implements Runnable {
	private String hostname;
	private int port;

	public MopedOutputConnection(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	@Override
	public void run() {
		try (Socket client = new Socket(hostname, port);
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);) {
			while (true) { //Reads the image from the moped
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.println(MopedSteeringHandler.getSteeringCommand());	
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostname);
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
