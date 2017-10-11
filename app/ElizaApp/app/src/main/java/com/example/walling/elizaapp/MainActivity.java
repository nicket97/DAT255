package com.example.walling.elizaapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements IMainView{

    private Controller controller;
    private Button btnStop, btnSwitchScreen, buttonDebug, setSpeedButton;
    private ToggleButton cruiseControlButton, platooningButton;
    private SeekBar speedBar, steerBar;
    private EditText setSpeedEditText;


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
        setSpeedButton = (Button) findViewById(R.id.setSpeedButton);
        cruiseControlButton = (ToggleButton) findViewById(R.id.cruiseControlButton);
        platooningButton = (ToggleButton) findViewById(R.id.platooningButton);
        setSpeedEditText = (EditText) findViewById(R.id.setSpeedEditText);

        btnStop.setOnClickListener(btnStopOnClick);
        btnSwitchScreen.setOnClickListener(btnSwitchScreenOnClick);
        buttonDebug.setOnClickListener(btnOnDebugClick);
        setSpeedButton.setOnClickListener(setSpeedOnClick);
        platooningButton.setOnCheckedChangeListener(platooningButtonListener);
        cruiseControlButton.setOnCheckedChangeListener(cruiseControlButtonListener);
        initSpeedBar();
        initSteerBar();
    }
    private void initSteerBar(){
        steerBar = (SeekBar) findViewById(R.id.steerBar);
        steerBar.setMax(200);
        steerBar.setProgress(steerBar.getMax() / 2);
        steerBar.setOnSeekBarChangeListener(changeSteerListener);
    }
    private void initSpeedBar(){
        speedBar = (SeekBar) findViewById(R.id.speedBar);
        speedBar.setMax(200);
        speedBar.setProgress(speedBar.getMax() / 2);
        speedBar.setOnSeekBarChangeListener(changeSpeedListener);
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


    private View.OnClickListener setSpeedOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Set speed to " + setSpeedEditText.getText().toString() + " cm/s.");
            controller.setSpeed(Double.parseDouble(setSpeedEditText.getText().toString()));
        }
    };

    private CompoundButton.OnCheckedChangeListener platooningButtonListener =
            new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                System.out.println("Platooning ON");
                controller.setPlatooning(true);
            } else {
                System.out.println("Platooning OFF");
                controller.setPlatooning(false);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener cruiseControlButtonListener =
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        System.out.println("Cruise control ON");
                        controller.setACC(true);
                    } else {
                        System.out.println("Cruise control OFF");
                        controller.setACC(false);
                    }
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
        //txtView.setText(res);
    }
}
