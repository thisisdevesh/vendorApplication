package com.gts.coordinator.fcm;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gts.coordinator.Notification.MyNotificationManager;
import com.gts.coordinator.ui.notification.ActivityNotification;

import org.json.JSONObject;


public class FcmListenerService extends FirebaseMessagingService {
    private static final String TAG = FcmListenerService.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "ActivityNotification Body: " + remoteMessage.getNotification().getBody());
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            String body = notification.getBody();
            String title =notification.getTitle() ;
            Intent intent = new Intent(this, ActivityNotification.class);
            intent.putExtra("stetus",1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            MyNotificationManager.showNotification(getApplicationContext(),title,body,pendingIntent);

        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
