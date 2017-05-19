package com.example.kimhunz.controlapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kimhunz.controlapplication.Contextor;

/**
 * Created by KiMHUNZ on 20/5/2560.
 */

public class DetectConnectInternet {

    boolean connected = false;


    public DetectConnectInternet() {
        //todo
    }

    public boolean isConnected() {
        Context mContext = Contextor.getInstance().getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            connected = true;
        }
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
