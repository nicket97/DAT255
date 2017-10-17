package core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.omg.CORBA_2_3.portable.OutputStream;


public class MopedImgConnection implements Runnable {
	private int port;
	FileInputStream in = null;
	BufferedInputStream bis = null;
	java.io.OutputStream os = null;
	ServerSocket servsock = null;
	Socket sock = null;

	public MopedImgConnection(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		while (true) {
			System.out.println("Connecting on port " + port);

			try (ServerSocket s = new ServerSocket(port);
					Socket client = s.accept();) {

				// send file
				while (true) {
					File myFile = new File(FILE_TO_SEND); // TODO change
					byte[] mybytearray = new byte[(int) myFile.length()];
					in = new FileInputStream(myFile);
					bis = new BufferedInputStream(in);
					bis.read(mybytearray, 0, mybytearray.length);
					os = sock.getOutputStream();
					System.out.println("Sending " + "(" + mybytearray.length + " bytes)");
					os.write(mybytearray, 0, mybytearray.length);
					os.flush();
					System.out.println("Done.");
				}				
				}
			} catch (IOException e) {
				if (e instanceof SocketTimeoutException) {
					System.out.println("Connection lost.");
				}
				System.out.println(
						"Exception caught when trying to listen on port " + port + " or listening for a connection");
				System.out.println(e.getMessage());
			}
		}
}