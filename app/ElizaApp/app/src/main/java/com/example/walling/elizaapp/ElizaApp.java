package com.example.walling.elizaapp;

import android.app.Application;

/**
 * Created by walling on 2017-09-15.
 */

public class ElizaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Model.getInstance().establishConnection();

    }


}
