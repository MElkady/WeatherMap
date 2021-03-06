package me.elkady.weathermap.citydetails;

import java.util.List;

import me.elkady.weathermap.BaseContract;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;
import me.elkady.weathermap.models.UnitSystem;

/**
 * Created by mak on 6/17/17.
 */

public interface CityDetailsContract {
    interface View extends BaseContract.BaseView {
        void displayCityDetails(CityDetails cityDetails);
        void displayForecast(List<CityDetails> cityDetails);
        void showLoading();
        void hideLoading();
        void setUnitSystem(UnitSystem unitSystem);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadUnitSystem();
        void loadCityDetails(City city);
        void loadForecast(City city);
    }
}
