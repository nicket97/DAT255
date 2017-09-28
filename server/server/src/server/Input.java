package server;

import java.io.*;
import java.net.*;

public class Input implements Runnable {

	private int mopedPort;

	public Input(int port) {
		this.mopedPort = port;

	}

	@Override
	public void run() {

		System.out.println("Connecting on port " + mopedPort);

		try (ServerSocket s = new ServerSocket(mopedPort);
				Socket client = s.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
			String inputLine, outputLine;
			ServerProtocol sp = new ServerProtocol();
			outputLine = sp.processInput(null);
			out.println(outputLine);
			System.out.println("connected");
			
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server received " + inputLine);
				outputLine = sp.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("Bye."))
					break;
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + mopedPort + " or listening for a connection");
			System.out.println(e.getMessage());
		}

	}
}