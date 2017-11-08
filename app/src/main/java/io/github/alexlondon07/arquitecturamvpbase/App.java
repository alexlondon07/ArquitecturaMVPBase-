package io.github.alexlondon07.arquitecturamvpbase;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import io.github.alexlondon07.arquitecturamvpbase.helper.Database;
import io.github.alexlondon07.arquitecturamvpbase.receivers.NetworkStateReceiver;
import io.github.alexlondon07.arquitecturamvpbase.synchronizer.Synchronizer;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.LoginActivity;

/**
 * Created by alexlondon07 on 9/30/17.
 */

public class App extends Application {

    private static final String TAG = "ONESIGNAL";
    public static Database db;
    private final NetworkStateReceiver  NETWORKSTATERECEIVER = new NetworkStateReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
        registerNetworkReceiver();
        
        settingOneSignal();
        

    }

    private void settingOneSignal() {
        OneSignal.startInit(this)
                .autoPromptLocation(false)
                .setNotificationReceivedHandler(new NotificationReceiverHandler())
                .setNotificationOpenedHandler(new NotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(NETWORKSTATERECEIVER, filter);
    }

    private void initDatabase() {
        db = new Database(this);
        db.open();
    }

    @Override
    public void onTerminate() {
        db.close();
        super.onTerminate();
    }

    public void onNetworkStateChange(boolean isConnected) {
        Synchronizer.getInstance().executeSyncLocalToServer(isConnected);
    }




    private class NotificationReceiverHandler implements OneSignal.NotificationReceivedHandler{

        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;
            String notificationId = notification.payload.notificationID;
            String title = notification.payload.title;

            Log.i(TAG, "notificationId" + notificationId );
            if(data !=null){
                String customKey = data.optString("customkey", null);
                if(customKey !=null){
                    Log.i(TAG, "token customkey " + customKey);

                }
            }
        }
    }

    private class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler{

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {

            OSNotificationAction.ActionType actionType = result.action.type;

            if (actionType == OSNotificationAction.ActionType.ActionTaken) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

        }
    }

}
