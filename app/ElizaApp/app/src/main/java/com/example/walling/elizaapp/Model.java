package com.example.walling.elizaapp;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.json.*;

/**
 * Created by walling on 2017-09-13.
 *
 * Singleton class for our Model Establishes connection to server and
 * continuously sends JSON objects Notifies views via Listeners if a change
 * happens in model e.g. connection lost Views notify the model of changes via
 * controller class
 */

public class Model {
	// TODO add helperclass for translation (ex: "set speed to 50 -> translates to
	// V0050H0000")
	private static Model instance;
	private PrintWriter out;
	private boolean connected = false;
	private boolean wasEverConnected = false;
	private JSONObject json = new JSONObject();

	private Model() {
		initJSON();
	}

	/**
	 * Gives the instance of the Model
	 * 
	 * @return the instance of the Model
	 */
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	private void initJSON() {
		try {
			json.put("Velocity", 0000);
			json.put("Handling", 0000);
			json.put("ACC", false);
			json.put("Platooning", false);
			json.put("Speed", 0.0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Establishes connection to server. Repeatedly sends JSON Objects to server.
	 * 
	 * @param ipInput
	 *            IP to connect to
	 * @param portInput
	 *            Port to connect to
	 */
	public void establishConnection(final String ipInput, final int portInput) {
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					System.out.println(ipInput + ", " + Integer.toString(portInput));
					Socket client = new Socket();
					client.connect(new InetSocketAddress(ipInput, portInput), 1000);
					connected = true;
					wasEverConnected = true;
					System.out.println("client created");

					out = new PrintWriter(client.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					{
						String response = in.readLine();
						System.out.println("I received: " + response);
						initJSON();
						out.println(json);

						while (connected) {
							Thread.sleep(200);
							response = in.readLine();

							if (response != null) {
								if (response.equals("Connection to moped lost.")) {
									connected = false;
								}
								// Todo if "Connection to moped lost."
								System.out.println("From server: " + response);
							} else {
								connected = false;
							}

							System.out.println("sending json: " + json.toString());
							out.println(json);
						}
					}

				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					connected = false;
					System.out.println(e.getMessage());
					if (e.getMessage().equals("Connection refused") || e.getMessage().equals("Socket closed")
							|| e.getMessage().equals("connect timed out")) {
						MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.PORT_CLOSED));
					}

					// TODO is it really "Already connected" ????. CHECK!
					else if (e.getMessage().equals("Already Connected")) {
						MessageListener.BUS.updateMessage(new MessageData(MessageData.MessageType.ALREADY_CONNECTED));
					}
				} finally {
					if (wasEverConnected) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								MessageListener.BUS
										.updateMessage(new MessageData(MessageData.MessageType.CONNECTION_LOST1));
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
								MessageListener.BUS
										.updateMessage(new MessageData(MessageData.MessageType.CONNECTION_LOST2));
							}
						});
					}
				}
			}
		}).start();
	}

	/**
	 * Checks if the app is connected to server
	 * 
	 * @return Returns boolean state of connection
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Stops the MOPED.
	 */
	public void stop() {
		SteeringHelper.getInstance().setVelocity(0);
		setSteerString(SteeringHelper.getInstance().getCommandString());
	}

	/**
	 * Checks if the app ever was connected to the server since last start of app
	 * 
	 * @return Returns boolean, true if ever was connected, false if not
	 */
	public boolean wasEverConnected() {
		return wasEverConnected;
	}

	/**
	 * Changes the direction of the MOPED
	 * 
	 * @param direction
	 *            a direction as integer, -100 to 100
	 */
	public void changeDirection(int direction) {
		SteeringHelper.getInstance().setDirection(direction);
		setSteerString(SteeringHelper.getInstance().getCommandString());
	}

	/**
	 * Changes the velocity of the MOPED
	 * 
	 * @param velocity
	 *            velocity, given as integer, -100 to 100
	 */
	public void changeVelocity(int velocity) {
		SteeringHelper.getInstance().setVelocity(velocity);
		setSteerString(SteeringHelper.getInstance().getCommandString());
	}

    /**
     * Set the state of ACC
     * @param state boolean argument, true or false, that sets ACC
     */
	public void setACC(boolean state) {
		try {
			json.put("ACC", state);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

    /**
     * Set the state of platooning
     * @param state boolean argument, true or false, that sets the platooning
     */
	public void setPlatooning(boolean state) {
		try {
			json.put("Platooning", state);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

    /**
     * Disconnect the App from the server
     */
	public void disconnect() {
		connected = false;
	}

    /**
     * Set the steerString of the MOPED
     * @param steerString must be provided as a valid MOPED steerstring, ex: V0000H0000
     */
	public void setSteerString(String steerString) {
		try {
			int vel = Integer.parseInt(steerString.substring(1, 5));
			vel = adjustVel(vel);
			int handling = Integer.parseInt(steerString.substring(6, 10));
			json.put("Velocity", vel);
			json.put("Handling", -handling);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

    /**
     * Set the speed of the MOPED
     * @param speed Speed given as a double
     */
	public void setSpeed(double speed) {
		try {
			json.put("Speed", speed);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private int adjustVel(int vel) {
		vel = vel + 50;

		if (vel > 0) {
			if (vel >= 150) {
				vel = 100;
			} else {
				double vel2 = vel;
				vel2 = 7.16381 + 0.262285 * vel - 0.00155898 * Math.pow(vel, 2) + 0.0000286057 * Math.pow(vel, 3);
				vel = (int) vel2;
			}
		}
		/**
		 * // denna koden nedanför är jag särskilt stolt över. //blixxten // om ni
		 * någonsin behöver hjälp med programmering, kontakta mig. jag är duktig. :^) if
		 * (vel > 0) { if (vel < 10) { vel = 12; } else if (vel < 18) { vel = 13; } else
		 * if (vel < 25) { vel = 14; } else if (vel < 33) { vel = 15; } else if (vel <
		 * 39) { vel = 16; } else if (vel < 50) { vel = 17; } else if (vel < 55) { vel =
		 * 18; } else if (vel < 60) { vel = 19; } else if (vel < 65) { vel = 20; } else
		 * if (vel < 70) { vel = 22; } else if (vel < 75) { vel = 25; } else if (vel <
		 * 80) { vel = 28; } else if (vel < 90) { vel = 33; } else if (vel < 100) { vel
		 * = 40; } else if (vel < 110) { vel = 54; } else if (vel < 120) { vel = 64; }
		 * else if (vel < 130) { vel = 78; } else if (vel < 140) { vel = 90; } else if
		 * (vel <= 150) { vel = 100; } }
		 */

		System.out.println("VEL: " + vel);
		return vel;
	}
}
