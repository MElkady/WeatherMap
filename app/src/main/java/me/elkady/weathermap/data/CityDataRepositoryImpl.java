package me.elkady.weathermap.data;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

import me.elkady.weathermap.BuildConfig;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;

/**
 * Created by mak on 6/17/17.
 */

public class CityDataRepositoryImpl implements CityDataRepository {
    @Override
    public void loadCityDetails(City city, final OnDetailsLoaded onDetailsLoaded) {
        try {
            URL u = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + city.getLat() + "&lon=" + city.getLng() + "&appid=" + BuildConfig.OWM_API_KEY + "&units=metric");
            HttpConnectionUtil.processRequest(u, new HttpConnectionUtil.OnDataReceived() {
                @Override
                public void onDataReceived(String data) {
                    try {
                        onDetailsLoaded.onDetailsLoaded(new CityDetails(data));
                    } catch (JSONException e) {
                        onDetailsLoaded.onError();
                    }
                }

                @Override
                public void onError(Exception e) {
                    onDetailsLoaded.onError();
                }
            });
        } catch (MalformedURLException e) {
            onDetailsLoaded.onError();
        }
    }
}
