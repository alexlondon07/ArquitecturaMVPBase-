package io.github.alexlondon07.arquitecturamvpbase;

import android.app.Application;

import io.github.alexlondon07.arquitecturamvpbase.helper.Database;

/**
 * Created by alexlondon07 on 9/30/17.
 */

public class App extends Application {

    public static Database db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new Database(this);
        db.open();
    }

    @Override
    public void onTerminate() {
        db.close();
        super.onTerminate();
    }
}
