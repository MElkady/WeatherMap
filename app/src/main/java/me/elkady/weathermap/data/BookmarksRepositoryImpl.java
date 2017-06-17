package me.elkady.weathermap.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.elkady.weathermap.WeatherMapApp;
import me.elkady.weathermap.data.db.BookmarksDBHelper;
import me.elkady.weathermap.data.db.DBContract;
import me.elkady.weathermap.models.City;

/**
 * Created by mak on 6/17/17.
 */

public class BookmarksRepositoryImpl implements BookmarksRepository {
    private static SQLiteDatabase mSqLiteDatabase;

    private SQLiteDatabase getDB() {
        if(mSqLiteDatabase == null) {
            BookmarksDBHelper dbHelper = new BookmarksDBHelper(WeatherMapApp.getContext());
            mSqLiteDatabase = dbHelper.getWritableDatabase();
        }
        return mSqLiteDatabase;
    }

    @Override
    public void loadBookmarkedCities(OnBookmarkedCitiesLoaded onBookmarkedCitiesLoaded) {
        String[] projection = {
                DBContract.BookmarkedCityEntry._ID,
                DBContract.BookmarkedCityEntry.COLUMN_NAME,
                DBContract.BookmarkedCityEntry.COLUMN_LAT,
                DBContract.BookmarkedCityEntry.COLUMN_LNG
        };

        Cursor cursor = getDB().query(DBContract.BookmarkedCityEntry.TABLE_NAME, projection, null, null, null, null, null);

        List<City> cities = new ArrayList<>();
        while(cursor.moveToNext()) {
            City c = new City();
            c.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.BookmarkedCityEntry._ID)));
            c.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.BookmarkedCityEntry.COLUMN_NAME)));
            c.setLat(cursor.getDouble(cursor.getColumnIndexOrThrow(DBContract.BookmarkedCityEntry.COLUMN_LAT)));
            c.setLng(cursor.getDouble(cursor.getColumnIndexOrThrow(DBContract.BookmarkedCityEntry.COLUMN_LNG)));

            cities.add(c);
        }

        cursor.close();

        if(onBookmarkedCitiesLoaded != null) {
            onBookmarkedCitiesLoaded.onBookmarkedCitiesLoaded(cities);
        }
    }

    @Override
    public void bookmarkCity(City city) {
        ContentValues values = new ContentValues();
        values.put(DBContract.BookmarkedCityEntry.COLUMN_NAME, city.getName());
        values.put(DBContract.BookmarkedCityEntry.COLUMN_LAT, city.getLat());
        values.put(DBContract.BookmarkedCityEntry.COLUMN_LNG, city.getLng());

        getDB().insert(DBContract.BookmarkedCityEntry.TABLE_NAME, null, values);
    }

    @Override
    public void removeBookmarkCity(City city) {
        getDB().delete(DBContract.BookmarkedCityEntry.TABLE_NAME, DBContract.BookmarkedCityEntry._ID + " = ?", new String[] {city.getId() + ""});
    }

    @Override
    public void clear() {
        getDB().delete(DBContract.BookmarkedCityEntry.TABLE_NAME, null, null);
    }

    @Override
    public void onDestroy() {
        if(mSqLiteDatabase != null) {
            mSqLiteDatabase.close();
            mSqLiteDatabase = null;
        }
    }

    @Override
    public City getCity(double lat, double lng) {
        City city = null;
        Geocoder gcd = new Geocoder(WeatherMapApp.getContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                if(addresses.get(0).getLocality() != null) {
                    city = new City(addresses.get(0).getLocality(), lat, lng);
                } else if (addresses.get(0).getAdminArea() != null) {
                    city = new City(addresses.get(0).getAdminArea(), lat, lng);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }
}
