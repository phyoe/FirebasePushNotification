package xyz.phyoekhant.padc.firebasepushnotification;

import android.app.Application;
import android.content.Context;

/**
 * Created by Phyoe Khant on 8/26/2016.
 */
public class FirebasePushNotificationApp extends Application {
    public static final String TAG = "FirebasePushNotiApp";

    private static Context context;

    public static Context getContext() {
        return context;
    }
}
