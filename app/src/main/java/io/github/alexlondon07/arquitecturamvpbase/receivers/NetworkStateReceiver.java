package io.github.alexlondon07.arquitecturamvpbase.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.github.alexlondon07.arquitecturamvpbase.App;

/**
 * Created by alexlondon07 on 10/12/17.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    private static boolean connected = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || networkInfo.getState() != NetworkInfo.State.CONNECTED){
            isConnected = false;
        }else {
            isConnected = networkInfo.isConnectedOrConnecting();
        }
        waitAndCheckConnection(isConnected, context);
        connected = isConnected;
    }

    private void waitAndCheckConnection(final boolean isConnected, final Context context) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App application = (App) context.getApplicationContext();
                if(connected == isConnected && application != null){
                    application.onNetworkStateChange(isConnected);
                }
            }
        });
        thread.start();
    }

    public static boolean isConnected() {
        return connected;
    }
}
