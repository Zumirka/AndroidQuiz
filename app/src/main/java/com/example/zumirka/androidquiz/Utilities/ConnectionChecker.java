package com.example.zumirka.androidquiz.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionChecker
{
    public static Boolean checkInternetConnection(Context ctx)
    {
        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
        if(connectivity !=null)

        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
