package me.elkady.weathermap.models;

import java.io.Serializable;

/**
 * Created by mak on 6/18/17.
 */

public class UnitSystem implements Serializable {
    private int id;
    private String name;
    private String temperatureSymbol;

    private UnitSystem(int id, String name, String temperatureSymbol) {
        this.id = id;
        this.name = name;
        this.temperatureSymbol = temperatureSymbol;
    }

    public static final UnitSystem IMPERIAL = new UnitSystem(1, "imperial", "f");
    public static final UnitSystem METRIC = new UnitSystem(2, "metric", "c");

    public String getName() {
        return name;
    }

    public String getTemperatureSymbol() {
        return temperatureSymbol;
    }

    public int getId() {
        return id;
    }
}
