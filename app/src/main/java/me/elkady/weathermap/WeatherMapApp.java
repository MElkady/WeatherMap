package me.elkady.weathermap;

import android.app.Application;
import android.content.Context;

import me.elkady.weathermap.data.BookmarksRepository;
import me.elkady.weathermap.data.BookmarksRepositoryImpl;
import me.elkady.weathermap.data.CityDataRepository;
import me.elkady.weathermap.data.CityDataRepositoryImpl;

/**
 * Created by mak on 6/17/17.
 */

public class WeatherMapApp extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }


    // This is dependency injectors...
    public static Context getContext() {
        return sContext;
    }
    public static BookmarksRepository getBookmarksRepository() {
        return new BookmarksRepositoryImpl();
    }
    public static CityDataRepository getCityDataRepository(){
        return new CityDataRepositoryImpl();
    }
}
