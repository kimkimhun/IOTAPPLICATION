package com.example.kimhunz.controlapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by KiMHUNZ on 12/3/2560.
 */

public class FirebaseMessingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        Intent intent;
        intent = new Intent(this, HomeActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pi = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder ncb = new NotificationCompat.Builder(this);
        ncb.setContentTitle("EGG INCUBATOR NOTIFICATION");
        ncb.setContentText(remoteMessage.getNotification().getBody());
        ncb.setAutoCancel(true);
        ncb.setSmallIcon(R.mipmap.ic_logo);
        ncb.setContentIntent(pi);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,ncb.build());
    }
}
