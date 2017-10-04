package server;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;

public class MopedImgConnection implements Runnable {

	private String hostname;
	private int port;
	static BufferedImage bimg;
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
		try (Socket client = new Socket(hostname, port);
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
				BufferedImage img=null;
			while () { //Reads the image from the moped
				try {
					//img = ImageIO.read(ImageIO.createImageInputStream(in));
					System.out.println(in.readLine());
				}
				catch (IllegalArgumentException e){
					continue;
				}

				System.out.println("received image");
				out.println("image received on server");
				pcs.firePropertyChange("new image", null, img);	
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
