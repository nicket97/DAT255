package com.example.walling.elizaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements IMainView{

    private Controller controller;
    private Button btnForward, btnBackward, btnStop;
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
        txtView = (TextView) findViewById(R.id.txtResult);
        tbCruiseControl = (ToggleButton) findViewById(R.id.tbCC);

        btnForward.setOnClickListener(btnForwardOnClick);
        btnStop.setOnClickListener(btnStopOnClick);
        btnBackward.setOnClickListener(btnBackwardOnClick);
        tbCruiseControl.setOnClickListener(tbCCOnClick);
    }

    // Here we initalize listeners


    private View.OnClickListener btnForwardOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
            controller.accelerate();
            updateResult("Accelerated");
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
    private View.OnClickListener btnBackwardOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
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


    @Override
    public void updateResult(String res) {
        txtView.setText(res);
    }
}
