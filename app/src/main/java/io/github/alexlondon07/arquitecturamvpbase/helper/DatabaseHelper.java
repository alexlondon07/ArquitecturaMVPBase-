package io.github.alexlondon07.arquitecturamvpbase.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import io.github.alexlondon07.arquitecturamvpbase.schemes.IProductScheme;

/**
 * Created by alexlondon07 on 9/30/17.
 */

class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(IProductScheme.PRODUCT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(Constants.DATABASE_NAME, " update version to " + newVersion);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + IProductScheme.PRODUCT_TABLE);

        switch (oldVersion)
        {
            case 2:
                //upgrade from version 1 to 2
                sqLiteDatabase.execSQL(IProductScheme.PRODUCT_ALTER_CREATE_VERSION_1);
            case 3:
                //upgrade from version 2 to 3
        }
    }
}
