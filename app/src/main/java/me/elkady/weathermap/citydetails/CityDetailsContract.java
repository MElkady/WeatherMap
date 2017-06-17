package me.elkady.weathermap.citydetails;

import me.elkady.weathermap.BaseContract;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;

/**
 * Created by mak on 6/17/17.
 */

public interface CityDetailsContract {
    interface View extends BaseContract.BaseView {
        void displayCityDetails(CityDetails cityDetails);
        void showLoading();
        void hideLoading();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadCityDetails(City city);
    }
}
