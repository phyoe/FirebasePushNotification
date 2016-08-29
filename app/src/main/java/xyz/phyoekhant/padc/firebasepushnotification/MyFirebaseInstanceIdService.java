package xyz.phyoekhant.padc.firebasepushnotification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Phyoe Khant on 8/13/2016.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String GENERAL_ENGAGEMENT_TOPIC="m-general-enagement-topic";
    private static final String TAG = "InstanceIdService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM InstanceID Token:" + token);

        /**
        FirebaseMessaging.getInstance()
                .subscribeToTopic(GENERAL_ENGAGEMENT_TOPIC);
        /**/
    }
}
