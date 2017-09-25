package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Input implements Runnable{
	
	ServerSocket s;
	private BufferedReader in;
	private PrintWriter out;
	private Socket client;
	private String line;
	
	public Input(int port){
		try {
			s = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			client = s.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		try{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch(IOException e){
			
		}
		while(true){
			try{
				line = in.readLine();
				
					//InputInterpreter.InterpreterAppInput(line);
					System.out.println(line);
				
			} catch(IOException e){
				System.out.println("Read Failed");
				
				
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void run() {
		
		
	}

}
