package xyz.phyoekhant.padc.firebasepushnotification;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Phyoe Khant on 8/13/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFPN";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "FCM Message Id:" + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message:" + remoteMessage.getNotification().getBody());
        Log.d(TAG, "FCM Notification Data:" + remoteMessage.getData());

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
    }
}
