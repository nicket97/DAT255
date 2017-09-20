package com.example.walling.elizaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by olofenstrom on 2017-09-18.
 */

public class ConnectActivity extends AppCompatActivity {

    private EditText ipText;
    private EditText portText;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        initGUI();
    }

    private void initGUI() {
        ipText = (EditText) findViewById(R.id.ipText);
        portText = (EditText) findViewById(R.id.portText);
        connectButton = (Button) findViewById(R.id.connectButton);
    }
}