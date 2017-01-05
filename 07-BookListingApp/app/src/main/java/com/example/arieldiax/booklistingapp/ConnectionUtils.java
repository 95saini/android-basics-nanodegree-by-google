package com.example.arieldiax.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class ConnectionUtils {

    /**
     * Creates a new ConnectionUtils object (no, it won't).
     */
    private ConnectionUtils() {
        // Required empty private constructor (to prevent instantiation).
    }

    /**
     * Determines whether or not the device has Internet connection.
     *
     * @param context Instance of the Context class.
     * @return Whether or not the device has Internet connection.
     */
    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
