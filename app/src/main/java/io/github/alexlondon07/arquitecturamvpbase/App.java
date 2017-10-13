package io.github.alexlondon07.arquitecturamvpbase;

import android.app.Application;
import android.content.IntentFilter;

import io.github.alexlondon07.arquitecturamvpbase.helper.Database;
import io.github.alexlondon07.arquitecturamvpbase.receivers.NetworkStateReceiver;
import io.github.alexlondon07.arquitecturamvpbase.synchronizer.Synchronizer;

/**
 * Created by alexlondon07 on 9/30/17.
 */

public class App extends Application {

    public static Database db;
    private final NetworkStateReceiver  NETWORKSTATERECEIVER = new NetworkStateReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
        registerNetworkReceiver();
        

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
}
