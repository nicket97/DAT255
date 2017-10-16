package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import comunication.MopedSteeringHandler;

public class MopedOutputConnection implements Runnable {
	private String hostname;
	private int port;
	private Socket client;

	public MopedOutputConnection(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	@Override
	public void run() {
		client = new Socket();
		try {
			client.connect(new InetSocketAddress(hostname, port));
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
			out.println("S0008T0007");
			out.println("V0000H0050");
		System.out.println("sending");
			while (true) { //Reads the image from the moped
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//out.println(MopedSteeringHandler.getSteeringCommand());	
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
