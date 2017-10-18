package com.example.walling.elizaapp;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestModelConnection implements Runnable {

    private ServerSocket socket;
    @Before
    public void setup(){
        try {
            socket = new ServerSocket(9000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectionTest() throws Exception {
        Thread t = new Thread(this);
        t.start();
        Socket client = socket.accept();

        assertTrue(client.isConnected());
    }

    @Override
    public void run() {
        Model.getInstance().establishConnection("localhost",9000);
    }
}