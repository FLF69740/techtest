package com.epfd.csandroid.utils;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.epfd.csandroid.R;

import static com.epfd.csandroid.App.CHANNEL;

public class MyAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getExtras().getString(NotificationAlarmUtils.TITLE_SETTINGS, "TITLE");
        String body = intent.getExtras().getString(NotificationAlarmUtils.BODY_SETTINGS, "BODY");
        int notifId = intent.getExtras().getInt(NotificationAlarmUtils.TAG_SETTINGS, 0);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(R.drawable.ic_logo_pos2)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(notifId, notification);
    }




}
