package me.elkady.weathermap.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.elkady.weathermap.BuildConfig;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;
import me.elkady.weathermap.models.UnitSystem;

/**
 * Created by mak on 6/17/17.
 */

public class CityDataRepositoryImpl implements CityDataRepository {
    @Override
    public void loadCityDetails(City city, UnitSystem unitSystem, final OnDetailsLoaded onDetailsLoaded) {
        try {
            URL u = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + city.getLat() + "&lon=" + city.getLng() + "&appid=" + BuildConfig.OWM_API_KEY + "&units=" + unitSystem.getName());
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

    @Override
    public void loadCityForecast(City city, UnitSystem unitSystem, final OnForecastLoaded onForecastLoaded) {
        try {
            URL u = new URL("http://api.openweathermap.org/data/2.5/forecast?lat=" + city.getLat() + "&lon=" + city.getLng() + "&appid=" + BuildConfig.OWM_API_KEY + "&units=" + unitSystem.getName());
            HttpConnectionUtil.processRequest(u, new HttpConnectionUtil.OnDataReceived() {
                @Override
                public void onDataReceived(String data) {
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        JSONArray jsonArray = jsonObject.getJSONArray("list");

                        List<CityDetails> forecastData = new ArrayList<CityDetails>();
                        for(int i = 0; i < jsonArray.length() && i < 5; i++) {
                            forecastData.add(new CityDetails(jsonArray.getJSONObject(i)));
                        }

                        onForecastLoaded.onForecastLoaded(forecastData);
                    } catch (JSONException e) {
                        onForecastLoaded.onError();
                    }
                }

                @Override
                public void onError(Exception e) {
                    onForecastLoaded.onError();
                }
            });
        } catch (MalformedURLException e) {
            onForecastLoaded.onError();
        }
    }
}
