package com.example.kimhunz.controlapplication;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import okhttp3.FormBody;

/**
 * Created by KiMHUNZ on 13/3/2560.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
//        OkHttpClient client = new OkHttpClient();
//        FormBody body = new FormBody.Builder()
//                .add("Token",refreshedToken)
//                .build();
//        Request request = new Request.Builder()
//                .url("http://")
//                .post(body)
//                .build();
//
//        try{
//            client.newCall(request).execute();
//        } catch (IOException e){
//          e.printStackTrace();
//        }
    }


}
