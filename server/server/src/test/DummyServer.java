package test;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

public class DummyServer implements Runnable {
	Image newimg;
	static BufferedImage bimg;
	byte[] bytes;
	Socket server;
	private int port;

	public DummyServer(int port){
		this.port = port;
	}

	public void run() {
		try (ServerSocket s = new ServerSocket(port);
				Socket client = s.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
			Robot bot;
			bot = new Robot();
			bimg = bot.createScreenCapture(new Rectangle(0, 0, 200, 100));
			ImageIO.write(bimg, "JPG", client.getOutputStream());
			out.println("image received");
			System.out.println("sent image");
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}