package com.example.notes.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import com.example.notes.ApplicationClass;

public class Utility {

    /**
     * Method to check network availability
     */
    public static boolean checkNetwork() {
        final Context ctx = ApplicationClass.getInstance();
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
