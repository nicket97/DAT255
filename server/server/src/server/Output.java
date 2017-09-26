package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Output implements Runnable {
	Socket s;
	private BufferedReader in;
	private PrintWriter out;
	
	private String mopedData;
	private String ip;
	private int port;
	
	public Output(String ip, int port){
		this.ip = ip;
		this.port = port;
	
	}

	@Override
	public void run() {
		try {
			
			s = new Socket();
			InetSocketAddress inetSocketAddres = new InetSocketAddress("localhost", 9000);
			s.connect(inetSocketAddres);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		try{
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
			out.println("Connected");
		} catch(IOException e){
			
		}
		while(true){
			out.println(mopedData);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	public void setMopedData(String data){
		mopedData = data;
	}
	public String getMopedData(){
		return mopedData;
	}


}
