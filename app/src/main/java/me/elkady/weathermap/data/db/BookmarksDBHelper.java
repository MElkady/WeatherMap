package me.elkady.weathermap.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mak on 6/17/17.
 */

public class BookmarksDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + DBContract.BookmarkedCityEntry.TABLE_NAME + " (" +
                    DBContract.BookmarkedCityEntry._ID + " INTEGER PRIMARY KEY," +
                    DBContract.BookmarkedCityEntry.COLUMN_NAME + " TEXT," +
                    DBContract.BookmarkedCityEntry.COLUMN_LAT + " NUMBER," +
                    DBContract.BookmarkedCityEntry.COLUMN_LNG + " NUMBER)";

    public BookmarksDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This is the first version, nothing to do here...
    }
}
