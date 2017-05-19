package com.example.kimhunz.controlapplication;

import android.app.Application;
import android.content.Context;

import com.example.kimhunz.controlapplication.Contextor;

/**
 * Created by KiMHUNZ on 20/5/2560.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
}
