package com.example.walling.elizaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by olofenstrom on 2017-09-20.
 */

public class DebugActivity extends AppCompatActivity {

    private Button backButton;
    private TextView debugTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        initGUI();
    }

    private void initGUI() {
        backButton = (Button) findViewById(R.id.buttonBack);
        debugTextView = (TextView) findViewById(R.id.textViewData);

        backButton.setOnClickListener(btnOnBackClick);
    }

    private View.OnClickListener btnOnBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(DebugActivity.this, MainActivity.class));
        }
    };
}
