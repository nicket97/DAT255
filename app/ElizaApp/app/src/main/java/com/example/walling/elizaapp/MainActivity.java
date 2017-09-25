package com.example.walling.elizaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements IMainView{

    private Controller controller;
    private Button btnForward, btnBackward, btnStop, btnSwitchScreen, buttonDebug, btnLeft, btnRight;
    private TextView txtView;
    private ToggleButton tbCruiseControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new Controller(this);

        initGUI();
    }



    private void initGUI(){
        btnForward = (Button) findViewById(R.id.btnForward);
        btnBackward = (Button) findViewById(R.id.btnBackward);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnSwitchScreen = (Button) findViewById(R.id.switchScreen);
        buttonDebug = (Button) findViewById(R.id.buttonDebug);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);

        txtView = (TextView) findViewById(R.id.txtResult);
        tbCruiseControl = (ToggleButton) findViewById(R.id.tbCC);

        btnForward.setOnClickListener(btnForwardOnClick);
        btnStop.setOnClickListener(btnStopOnClick);
        btnBackward.setOnClickListener(btnBackwardOnClick);
        btnSwitchScreen.setOnClickListener(btnSwitchScreenOnClick);
        tbCruiseControl.setOnClickListener(tbCCOnClick);
        buttonDebug.setOnClickListener(btnOnDebugClick);
        btnRight.setOnClickListener(btnRightOnClick);
        btnLeft.setOnClickListener(btnLeftOnClick);
    }

    // Here we initalize listeners


    private View.OnClickListener btnForwardOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            controller.accelerate();
            updateResult("Accelerated");
        }
    };

    private View.OnClickListener btnBackwardOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            controller.reverse();
            updateResult("Reversed");
        }
    };

    private View.OnClickListener tbCCOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
            if(tbCruiseControl.isChecked()){
                controller.activateCruiseControl();
            } else {
                controller.deActivateCruiseControl();
            }
            updateResult("Cruise Control: " + tbCruiseControl.isChecked());
        }
    };

    private View.OnClickListener btnStopOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
            controller.stop();
            updateResult("Stop");
        }
    };

    private View.OnClickListener btnSwitchScreenOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, ConnectActivity.class));
        }
    };

    private View.OnClickListener btnOnDebugClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, DebugActivity.class));
        }
    };

    private View.OnClickListener btnLeftOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            controller.turnLeft();
            updateResult("left");
        }
    };

    private View.OnClickListener btnRightOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            controller.turnRight();
            updateResult("right");
        }
    };



    @Override
    public void updateResult(String res) {
        txtView.setText(res);
    }
}
