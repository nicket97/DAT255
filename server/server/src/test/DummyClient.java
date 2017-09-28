package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class DummyClient {
	String hostname;
	int port;

	public DummyClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public String connectAndSend(String message) {
		try (Socket client = new Socket(hostname, port);
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
			String firstResponse=in.readLine();
			if (firstResponse.equals("Send over data.")) {
				out.println(message);
				System.out.println("sent " + message);
				String reply = in.readLine();
				System.out.println("Reply was " + reply);
				return reply;
			}
				
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostname);
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return "No reply";
	}
}
