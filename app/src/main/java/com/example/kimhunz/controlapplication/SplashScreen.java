package com.example.kimhunz.controlapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;


import io.fabric.sdk.android.Fabric;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    Thread thread;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        checkConnect();

//        Thread thread = new Thread(){
//            @Override
//            public void run(){
//                try{
//                    sleep(1000);
//                   checkConnect();
//                    finish();
//                } catch (Fragment.InstantiationException e){
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        thread.start();

    }
    private void checkConnect(){
        DetectConnectInternet detectConnectInternet = new DetectConnectInternet();
        boolean connectResult = detectConnectInternet.isConnected();
        if(connectResult){
            toHomeActivity();
        }else{

            new LovelyStandardDialog(this)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorPrimaryDark)
                    .setIcon(R.drawable.ic_error_outline_white_48dp)
                    .setTitle(R.string.internet_aleart_title)
                    .setMessage(R.string.internet_aleart_message)
                    .setPositiveButton(R.string.send, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           finish();
                        }
                    })
                    .show();
        }
    }
    private void toHomeActivity() {
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }


}
