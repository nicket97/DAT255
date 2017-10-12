package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import main.Start;

public class Conection implements Runnable {

	
	ServerSocket server;
	Socket client;
	@Override
	public void run() {
		try {
			server  = new ServerSocket(9999);
			client = server.accept();
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); {
				String inputLine, outputLine;
				
				System.out.println("connected");
		
				while ((inputLine = in.readLine()) != null) {
				
					out.println(Start.start.moped.getMopedOutput());
					Start.start.moped.sendServerSignal(in.readLine());
					Thread.sleep(200);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
