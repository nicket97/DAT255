package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Output {
	Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private Socket client;
	private String line;
	
	public Output(int port){
		try {
			s = new Socket("192.168.43.121", port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			client = s.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} */
		try{
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
		} catch(IOException e){
			
		}
		out.println("hej igen");
		System.exit(1);
	}


}
