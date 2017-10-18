package com.example.walling.elizaapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements IMainView, IMessageListener {

    private Controller controller;
    private Button btnStop, dcButton, setSpeedButton, centerButton;
    private ToggleButton cruiseControlButton, autoSteerButton, platooningButton;
    private SeekBar speedBar, steerBar;
    private EditText setSpeedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        controller = new Controller(this);
        if (Model.getInstance().wasEverConnected()) {
            MessageListener.BUS.addListener(this);
        }

        initGUI();
    }

    private void initGUI(){
        btnStop = (Button) findViewById(R.id.btnStop);
        dcButton = (Button) findViewById(R.id.dcButton);
        setSpeedButton = (Button) findViewById(R.id.setSpeedButton);
        cruiseControlButton = (ToggleButton) findViewById(R.id.cruiseControlButton);
        autoSteerButton = (ToggleButton) findViewById(R.id.autoSteerButton);
        setSpeedEditText = (EditText) findViewById(R.id.setSpeedEditText);
        centerButton = (Button) findViewById(R.id.centerButton);
        platooningButton = (ToggleButton) findViewById(R.id.platoonButton);

        platooningButton.setOnCheckedChangeListener(platooningButtonListener);
        btnStop.setOnClickListener(btnStopOnClick);
        dcButton.setOnClickListener(dcButtonOnClick);
        setSpeedButton.setOnClickListener(setSpeedOnClick);
        autoSteerButton.setOnCheckedChangeListener(autoSteeringButtonListener);
        cruiseControlButton.setOnCheckedChangeListener(cruiseControlButtonListener);
        centerButton.setOnClickListener(centerButtonOnClick);
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
        speedBar.setProgress(speedBar.getMax() / 2 - 50);
        speedBar.setOnSeekBarChangeListener(changeSpeedListener);
    }

    private void setSpeedBarValue(int newValue){
        speedBar.setProgress(newValue, true);
    }

    private void ableUI(boolean state) {
        dcButton.setEnabled(state);
        setSpeedButton.setEnabled(state);
        setSpeedEditText.setEnabled(state);
        centerButton.setEnabled(state);
        cruiseControlButton.setEnabled(state);
        steerBar.setEnabled(state);
        btnStop.setEnabled(state);
        autoSteerButton.setEnabled(state);
        speedBar.setEnabled(state);
        platooningButton.setEnabled(state);
    }

    private void ableUI(boolean state, ToggleButton toggleButton) {
        dcButton.setEnabled(state);
        setSpeedButton.setEnabled(state);
        setSpeedEditText.setEnabled(state);
        if (toggleButton == autoSteerButton) {
            centerButton.setEnabled(state);
            cruiseControlButton.setEnabled(state);
            steerBar.setEnabled(state);
        } else if (toggleButton == cruiseControlButton) {
            btnStop.setEnabled(state);
            autoSteerButton.setEnabled(state);
            speedBar.setEnabled(state);
        } else if (toggleButton == platooningButton) {
            centerButton.setEnabled(state);
            btnStop.setEnabled(state);
            autoSteerButton.setEnabled(state);
            cruiseControlButton.setEnabled(state);
            speedBar.setEnabled(state);
            steerBar.setEnabled(state);
        }
    }

    private void zeroUI() {
        steerBar.setProgress(100, true);
        speedBar.setProgress(50, true);
        controller.setSpeed(0);
        setSpeedEditText.setText("");
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

    private SeekBar.OnSeekBarChangeListener changeSpeedListener = new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            controller.changeVelocity(progress-100);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            controller.setSpeed(0);
            setSpeedEditText.setText("");
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private View.OnClickListener centerButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            steerBar.setProgress(100, true);
        }
    };

    private View.OnClickListener setSpeedOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = setSpeedEditText.getText().toString();
            if (!str.equals("")) {
                if (str.contains(",")) {
                    str = str.replace(",", ".");
                }

                try {
                    double speed = Double.parseDouble(str);
                    if (speed >= -100 && speed <= 100) {
                        controller.setSpeed(speed);
                    }
                    speedBar.setProgress(50, true);
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), "Speed not provided correctly.",
                     //       Toast.LENGTH_LONG).show();
                }

            } else {
                //Toast.makeText(getApplicationContext(), "You must provide a speed.",
                 //       Toast.LENGTH_LONG).show();
            }

        }
    };

    private CompoundButton.OnCheckedChangeListener platooningButtonListener =
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ableUI(false, platooningButton);
                        controller.setPlatooning(true);
                        controller.setACC(true);
                    } else {
                        controller.setPlatooning(false);
                        controller.setACC(false);
                        zeroUI();
                        ableUI(true, platooningButton);
                    }
                }
            };

    private CompoundButton.OnCheckedChangeListener autoSteeringButtonListener =
            new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                zeroUI();
                controller.setPlatooning(true);
                ableUI(false, autoSteerButton);
            } else {
                controller.setPlatooning(false);
                ableUI(true, autoSteerButton);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener cruiseControlButtonListener =
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        zeroUI();
                        controller.setACC(true);
                        ableUI(false, cruiseControlButton);
                    } else {
                        controller.setACC(false);
                        ableUI(true, cruiseControlButton);
                    }
                }
            };

    private View.OnClickListener btnStopOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateResult("Stop");
            setSpeedBarValue(50);
        }
    };

    private View.OnClickListener dcButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Looper.prepare();
                        controller.setSpeed(0);
                        speedBar.setProgress(50, true);
                        steerBar.setProgress(100, true);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ableUI(false);
                            }
                        });

                        Thread.sleep(2000);
                        controller.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    };

    @Override
    public void updateResult(String res) {

    }

    @Override
    public void update(MessageData msgData) {
        if (msgData.getMessageType() == MessageData.MessageType.CONNECTION_LOST1) {
           // Toast.makeText(getApplicationContext(), "Connection lost", Toast.LENGTH_LONG).show();
            ableUI(false, null);
            autoSteerButton.setEnabled(false);
            cruiseControlButton.setEnabled(false);
        }

        if (msgData.getMessageType() == MessageData.MessageType.CONNECTION_LOST2) {
            startActivity(new Intent(MainActivity.this, ConnectActivity.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        speedBar.setProgress(50, true);
        steerBar.setProgress(100, true);
    }
}
