package xyz.phyoekhant.padc.firebasepushnotification;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by winthanhtike on 8/21/16.
 */
public class NetworkUtils {

    public static boolean isOnline(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        return (nf != null && nf.isConnected());
    }

}
