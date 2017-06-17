package me.elkady.weathermap.data;

import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;

/**
 * Created by mak on 6/17/17.
 */

public interface CityDataRepository {
    void loadCityDetails(City city, OnDetailsLoaded onDetailsLoaded);


    public interface OnDetailsLoaded {
        void onDetailsLoaded(CityDetails cityDetails);
        void onError();
    }
}
