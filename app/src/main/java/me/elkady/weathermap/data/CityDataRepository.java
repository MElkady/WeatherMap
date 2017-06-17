package me.elkady.weathermap.data;

import java.util.List;

import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;

/**
 * Created by mak on 6/17/17.
 */

public interface CityDataRepository {
    void loadCityDetails(City city, OnDetailsLoaded onDetailsLoaded);
    void loadCityForecast(City city, OnForecastLoaded onForecastLoaded);


    interface OnDetailsLoaded {
        void onDetailsLoaded(CityDetails cityDetails);
        void onError();
    }

    interface OnForecastLoaded {
        void onForecastLoaded(List<CityDetails> cityDetails);
        void onError();
    }
}
