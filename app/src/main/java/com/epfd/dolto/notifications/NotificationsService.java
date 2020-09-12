package com.epfd.dolto.notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationsService extends FirebaseMessagingService {

    private final int NOTIFICATION_ID = 1001;
    private final String NOTIFICATION_TAG = "NOTIFICATION_TAG";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            // 1 - Get message sent by Firebase
            String message = remoteMessage.getNotification().getBody();
            String title = remoteMessage.getNotification().getTitle();
            //2 - Show message in console
        }
    }
}
