package me.elkady.weathermap;

import me.elkady.weathermap.models.City;

/**
 * Created by mak on 6/17/17.
 */

public interface HomeInterface {
    void switchToSearchScreen();
    void switchToDetailsScreen(City city);
    void onBookmarksUpdated();
}
