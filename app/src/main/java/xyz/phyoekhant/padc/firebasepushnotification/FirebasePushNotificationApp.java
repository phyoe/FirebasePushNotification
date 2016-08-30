package xyz.phyoekhant.padc.firebasepushnotification;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;

/**
 * Created by Phyoe Khant on 8/26/2016.
 */
public class FirebasePushNotificationApp extends Application {
    public static final String TAG = "FirebasePushNotiApp";

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
