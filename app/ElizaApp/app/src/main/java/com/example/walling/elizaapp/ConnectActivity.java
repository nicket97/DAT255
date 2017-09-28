package com.example.walling.elizaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by olofenstrom on 2017-09-18.
 */

public class ConnectActivity extends AppCompatActivity implements IMainView, IMessageListener {

    private EditText ipText;
    private EditText portText;
    private Button connectButton, connectBackButton, checkConnectionButton;
    private Controller controller;
    private TextView connectionStatusTxtView;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        controller = new Controller(this);

        MessageListener.BUS.addListener(this);

        initGUI();
    }

    private void initGUI() {
        ipText = (EditText) findViewById(R.id.ipText);
        portText = (EditText) findViewById(R.id.portText);
        connectButton = (Button) findViewById(R.id.connectButton);
        connectBackButton = (Button) findViewById(R.id.connectBackButton);
        connectionStatusTxtView = (TextView) findViewById(R.id.textViewConnectStatus);
        checkConnectionButton = (Button) findViewById(R.id.checkConnectionButton);

        checkConnectionButton.setOnClickListener(checkConnectionButtonClick);
        connectButton.setOnClickListener(connectButtonClick);
        connectBackButton.setOnClickListener(backButtonOnClick);

        // set default ip
        ipText.setText("10.0.2.2");
        portText.setText("9000");

        connectionStatusTxtView.setText("Connection status: " + controller.getConnectionStatus());
    }

    private void allSetEnable(boolean isEnable) {
        ipText.setEnabled(isEnable);
        portText.setEnabled(isEnable);
        connectButton.setEnabled(isEnable);
        connectBackButton.setEnabled(isEnable);
        connectionStatusTxtView.setEnabled(isEnable);
        checkConnectionButton.setEnabled(isEnable);
    }

    private View.OnClickListener backButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ConnectActivity.this, MainActivity.class));
        }
    };

    private View.OnClickListener connectButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            controller.establishConnection(ipText.getText().toString(), Integer.parseInt(portText.getText().toString()));
        }
    };

    private View.OnClickListener checkConnectionButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            connectionStatusTxtView.setText("Connection status: " + controller.getConnectionStatus());
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
        toast.show();
    }
}