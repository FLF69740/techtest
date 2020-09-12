package com.epfd.dolto;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL = "CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL, "NOTIFCATION", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Cette notification permets au parent d'être alerter sur les évènements de l'école privée Françoise DOLTO");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
