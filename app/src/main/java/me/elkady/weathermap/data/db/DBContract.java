package me.elkady.weathermap.data.db;

import android.provider.BaseColumns;

/**
 * Created by mak on 6/17/17.
 */

public final class DBContract {
    private DBContract(){}

    public static class BookmarkedCityEntry implements BaseColumns {
        public static final String TABLE_NAME = "bookmarks";
        public static final String COLUMN_NAME= "name";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LNG = "lng";
    }
}
