package com.GOMAN.gomankurir.KoneksiInternet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.GOMAN.gomankurir.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().isEmpty()){
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }else{
            showNotification(remoteMessage.getData());
        }
    }

    private void showNotification(Map<String, String> data) {
        String title = data.get("title").toString();
        String body = data.get("body").toString();

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_SHANNEL_ID = "GOMAN";

        if (Build.VERSION.SDK_INT >= 27){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_SHANNEL_ID,"Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("GOMAN CHANNEL");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this, NOTIFICATION_SHANNEL_ID);

        notificationbuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentInfo(title)
                .setContentText(body)
                .setContentInfo("Info");

        assert notificationManager != null;
        notificationManager.notify(new Random().nextInt(), notificationbuilder.build());
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_SHANNEL_ID = "GOMAN";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_SHANNEL_ID,"Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("GOMAN CHANNEL");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this, NOTIFICATION_SHANNEL_ID);

        notificationbuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentInfo(title)
                .setContentText(body)
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(), notificationbuilder.build());

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Common.updateToken(this,s, false, true);
    }
}
