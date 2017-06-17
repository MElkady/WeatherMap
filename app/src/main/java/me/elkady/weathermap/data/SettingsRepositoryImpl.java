package me.elkady.weathermap.data;

import android.content.Context;
import android.content.SharedPreferences;

import me.elkady.weathermap.WeatherMapApp;
import me.elkady.weathermap.models.UnitSystem;

/**
 * Created by mak on 6/18/17.
 */

public class SettingsRepositoryImpl implements SettingsRepository {
    private static final String PREF_FILE = "settings.pref";
    private static final String SETTING_UNIT_SYSTEM = "unit_system";


    @Override
    public void setUnitSystem(UnitSystem unitSystem) {
        SharedPreferences sharedPreferences = WeatherMapApp.getContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SETTING_UNIT_SYSTEM, unitSystem.getId());
        editor.apply();
    }

    @Override
    public UnitSystem getUnitSystem() {
        SharedPreferences sharedPreferences = WeatherMapApp.getContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        int storedValue = sharedPreferences.getInt(SETTING_UNIT_SYSTEM, UnitSystem.METRIC.getId());

        return (storedValue == UnitSystem.METRIC.getId())? UnitSystem.METRIC : UnitSystem.IMPERIAL;
    }
}
