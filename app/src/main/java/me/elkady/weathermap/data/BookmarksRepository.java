package me.elkady.weathermap.data;

import java.util.List;

import me.elkady.weathermap.models.City;

/**
 * Created by mak on 6/17/17.
 */

public interface BookmarksRepository {
    void loadBookmarkedCities(OnBookmarkedCitiesLoaded onBookmarkedCitiesLoaded);
    void bookmarkCity(City city);
    void removeBookmarkCity(City city);
    void onDestroy();
    City getCity(double lat, double lng);

    interface OnBookmarkedCitiesLoaded {
        void onBookmarkedCitiesLoaded(List<City> cities);
        void onError();
    }
}
