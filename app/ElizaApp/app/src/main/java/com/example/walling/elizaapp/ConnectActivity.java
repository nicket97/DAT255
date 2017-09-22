package com.example.walling.elizaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by olofenstrom on 2017-09-18.
 */

public class ConnectActivity extends AppCompatActivity implements IMainView {

    private EditText ipText;
    private EditText portText;
    private Button connectButton, connectBackButton, checkConnectionButton;
    private Controller controller;
    private TextView connectionStatusTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        controller = new Controller(this);

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
            System.out.println("click registered");
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
}