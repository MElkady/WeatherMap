package me.elkady.weathermap.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by mak on 6/17/17.
 */

public class CityDetails {
    private Date date;
    private double temp;
    private double minTemp;
    private double maxTemp;
    private double humidity;
    private double windSpeed;
    private double windDeg;
    private double rainChance;

    public CityDetails() {
    }

    public CityDetails(String jsonObject) throws JSONException {
        this(new JSONObject(jsonObject));
    }

    public CityDetails(JSONObject jsonObject) throws JSONException {
        this.date = new Date(jsonObject.getLong("dt") * 1000);

        JSONObject main = jsonObject.getJSONObject("main");
        temp = main.getDouble("temp");
        minTemp = main.getDouble("temp_min");
        maxTemp = main.getDouble("temp_max");
        humidity = main.getDouble("humidity");

        JSONObject wind = jsonObject.getJSONObject("wind");
        windSpeed = wind.getDouble("speed");
        windDeg = wind.getDouble("deg");

        rainChance = jsonObject.getJSONObject("clouds").getDouble("all");
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public double getRainChance() {
        return rainChance;
    }

    @Override
    public String toString() {
        return "CityDetails{" +
                "temp=" + temp +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDeg=" + windDeg +
                ", rainChance=" + rainChance +
                '}';
    }
}
