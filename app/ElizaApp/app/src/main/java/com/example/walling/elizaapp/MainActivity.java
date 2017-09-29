package com.example.walling.elizaapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements IMainView{

    private Controller controller;
    private Button btnStop, btnSwitchScreen, buttonDebug;
    private TextView txtView;
    private ToggleButton tbCruiseControl;
    private SeekBar speedBar, steerBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        controller = new Controller(this);

        initGUI();
    }

    private void initGUI(){
        btnStop = (Button) findViewById(R.id.btnStop);
        btnSwitchScreen = (Button) findViewById(R.id.switchScreen);
        buttonDebug = (Button) findViewById(R.id.buttonDebug);

        txtView = (TextView) findViewById(R.id.txtResult);
        tbCruiseControl = (ToggleButton) findViewById(R.id.tbCC);

        btnStop.setOnClickListener(btnStopOnClick);
        btnSwitchScreen.setOnClickListener(btnSwitchScreenOnClick);
        tbCruiseControl.setOnClickListener(tbCCOnClick);
        buttonDebug.setOnClickListener(btnOnDebugClick);
        initSpeedBar();
        initSteerBar();
    }
    private void initSteerBar(){
        steerBar = (SeekBar) findViewById(R.id.steerBar);
        steerBar.setOnSeekBarChangeListener(changeSteerListener);
        steerBar.setMax(200);
        steerBar.setProgress(100);
    }
    private void initSpeedBar(){
        speedBar = (SeekBar) findViewById(R.id.speedBar);
        speedBar.setOnSeekBarChangeListener(changeSpeedListener);
        speedBar.setMax(200);
        speedBar.setProgress(100);
    }

    private void setSpeedBarValue(int newValue){
        speedBar.setProgress(newValue);
    }


    // Here we initalize listeners

    private SeekBar.OnSeekBarChangeListener changeSteerListener= new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            controller.changeDirection(progress-100);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    private SeekBar.OnSeekBarChangeListener changeSpeedListener= new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            controller.changeVelocity(progress-100);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
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
            setSpeedBarValue(100);
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

    @Override
    public void updateResult(String res) {
        txtView.setText(res);
    }
}
