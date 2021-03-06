package me.elkady.weathermap;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import me.elkady.weathermap.data.BookmarksRepository;
import me.elkady.weathermap.data.BookmarksRepositoryImpl;
import me.elkady.weathermap.data.CityDataRepository;
import me.elkady.weathermap.data.CityDataRepositoryImpl;
import me.elkady.weathermap.data.SettingsRepository;
import me.elkady.weathermap.data.SettingsRepositoryImpl;

/**
 * Created by mak on 6/17/17.
 */

public class WeatherMapApp extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog() // Log detected violations to the system log.
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath() // Crashes the whole process on violation.
                    .build());
        }
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
    public static SettingsRepository getSettingsRepository(){
        return new SettingsRepositoryImpl();
    }
}
