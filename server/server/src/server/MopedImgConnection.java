package server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/***
 * Responsible for receiving images sent from the moped. Notifies Start when a
 * new image is received.
 */
public class MopedImgConnection implements Runnable {

	private String hostname;
	private int port;
	byte[] bytes;
	private PropertyChangeSupport pcs;

	public MopedImgConnection(String hostname, int port, PropertyChangeListener mainServer) {
		this.hostname = hostname;
		this.port = port;
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(mainServer);
	}

	@Override
	public void run() {

		FileOutputStream fileOut;

		try (Socket clientSocket = new Socket(hostname, port);
				PrintWriter out = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
				InputStream is = clientSocket.getInputStream();) {

			String filename;
			while (true) {
				String data = "";

				Integer fileSize = 0;

				byte[] b = new byte[100];
				int ix = 0;
				while (true) {
					int rd = is.read();
					if ((rd == (int) '\n' || (rd < 0)))
						break;

					b[ix++] = (byte) rd;
				}

				data = new String(b, 0, ix);

				filename = data.split(",")[0];
				File file = new File(filename);
				fileSize = Integer.valueOf(data.split(",")[1]);
				fileOut = new FileOutputStream(file);
				//System.out.println(data);

				int recievedSize = 0;
				if (fileSize != 0) {
					byte[] buffer = new byte[1];
					while (recievedSize < fileSize) {
						int byteread = is.read(buffer);
						fileOut.write(buffer);
						recievedSize += byteread;
					}
					;
					
					//System.out.println("\nFile downloaded");
					pcs.firePropertyChange("new image", null, file);
					out.println("OK");
					fileOut.close();
				}
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