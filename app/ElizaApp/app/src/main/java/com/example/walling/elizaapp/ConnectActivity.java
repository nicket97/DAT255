package com.example.walling.elizaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by olofenstrom on 2017-09-18.
 *
 *
 */

public class ConnectActivity extends AppCompatActivity implements IMainView, IMessageListener {

    private EditText ipText;
    private EditText portText;
    private Button connectButton;
    private Controller controller;
    Toast toast;
    private ImageView raspPiImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        controller = new Controller(this);


        if (!Model.getInstance().wasEverConnected()) {
            MessageListener.BUS.addListener(this);
        }

        initGUI();
    }

    private void initGUI() {
        ipText = (EditText) findViewById(R.id.ipText);
        portText = (EditText) findViewById(R.id.portText);
        connectButton = (Button) findViewById(R.id.connectButton);
        raspPiImage = (ImageView) findViewById(R.id.imageView);

        connectButton.setOnClickListener(connectButtonClick);

        // set default ip
        ipText.setText("192.168.43.");
        portText.setText("8080");
        raspPiImage.setRotation(348);
    }

    private void allSetEnable(boolean isEnable) {
        ipText.setEnabled(isEnable);
        portText.setEnabled(isEnable);
        connectButton.setEnabled(isEnable);
    }

    private View.OnClickListener connectButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            controller.establishConnection(ipText.getText().toString(), Integer.parseInt(portText.getText().toString()));
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    handler.post(new Runnable() {
                        public void run() {
                            allSetEnable(false);
                        }
                    });
                    try {
                        Thread.sleep(2000);
                        if (Model.getInstance().isConnected()) {
                            startActivity(new Intent(ConnectActivity.this, MainActivity.class));

                            handler.post(new Runnable() {
                                public void run() {
                                    //Toast.makeText(getApplicationContext(), "Connected.", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            handler.post(new Runnable() {
                                public void run() {
                                   // Toast.makeText(getApplicationContext(), "Can't connect.", Toast.LENGTH_LONG).show();
                                }
                            });                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                allSetEnable(true);
                            } catch (Exception e) {

                            }
                        }
                    });
                }
            }).start();
        }
    };

    @Override
    public void updateResult(String res) {

    }

    @Override
    public void update(MessageData msgData) {

        if (toast != null) toast.cancel();

        if (msgData.getMessageType() == MessageData.MessageType.PORT_CLOSED) {
            toast = Toast.makeText(getBaseContext(), "PORT CLOSED", Toast.LENGTH_SHORT);
        } else if(msgData.getMessageType() == MessageData.MessageType.ALREADY_CONNECTED){
            toast = Toast.makeText(getBaseContext(), "ALREADY CONNECTED", Toast.LENGTH_SHORT);
        } else if(msgData.getMessageType() == MessageData.MessageType.CONNECTING) {
            toast = Toast.makeText(getBaseContext(), "CONNECTING, WAIT...", Toast.LENGTH_LONG);
            allSetEnable(false);
        } else if(msgData.getMessageType() == MessageData.MessageType.CONNECTION_DONE) {
            toast = Toast.makeText(getBaseContext(), "DONE!", Toast.LENGTH_SHORT);
            allSetEnable(true);
        }

        if (toast != null) {
           // toast.show();
        }
    }
}