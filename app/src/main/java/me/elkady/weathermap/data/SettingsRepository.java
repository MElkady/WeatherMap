package me.elkady.weathermap.data;

import me.elkady.weathermap.models.UnitSystem;

/**
 * Created by mak on 6/18/17.
 */

public interface SettingsRepository {
    void setUnitSystem(UnitSystem system);
    UnitSystem getUnitSystem();
}
