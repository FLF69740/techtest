package com.epfd.dolto.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import java.util.Calendar;

public class NotificationAlarmUtils {

    public static final String TITLE_SETTINGS = "TITLE_SETTINGS";
    public static final String BODY_SETTINGS = "BODY_SETTINGS";
    public static final String TAG_SETTINGS = "TAG_SETTINGS";

    public static PendingIntent getAlarmManagerPendingIntent(Context context, String title, String body, int tag){
        Intent intent = new Intent(context, MyAlarmReceiver.class);
        intent.putExtra(TAG_SETTINGS, tag);
        intent.putExtra(TITLE_SETTINGS, title);
        intent.putExtra(BODY_SETTINGS, body);
        return PendingIntent.getBroadcast(context, tag, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static Boolean getAlarmManagerPendingExist(Context context, int tag){
        return PendingIntent.getBroadcast(context, tag, new Intent(context, MyAlarmReceiver.class), PendingIntent.FLAG_NO_CREATE) != null;
    }

    public static void startAlarm(Context context, PendingIntent pendingIntent, int dayOfYear, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 15);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }

    public static void stopAlarm(Context context, PendingIntent pendingIntent){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) alarmManager.cancel(pendingIntent);
    }




}
