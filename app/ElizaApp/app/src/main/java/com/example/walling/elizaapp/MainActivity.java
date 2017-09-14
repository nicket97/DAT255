package com.example.walling.elizaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IMainView{

    private Controller controller;
    private Button btnForward, btnBackward, btnStop;
    private TextView txtView;

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

        btnForward.setOnClickListener(btnForwardOnCLick);
        btnStop.setOnClickListener(btnStopOnCLick);
        btnBackward.setOnClickListener(btnBackwardOnCLick);
    }

    // Here we initalize listeners


    private View.OnClickListener btnForwardOnCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
            controller.accelerate();
            updateResult("Accelerated");
        }
    };
    private View.OnClickListener btnStopOnCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
            controller.stop();
            updateResult("Stop");
        }
    };
    private View.OnClickListener btnBackwardOnCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
            controller.reverse();
            updateResult("Reversed");
        }
    };


    @Override
    public void updateResult(String res) {
        txtView.setText(res);
    }
}
