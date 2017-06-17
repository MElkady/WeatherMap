package me.elkady.weathermap.addcity;

import java.util.List;

import me.elkady.weathermap.BaseContract;
import me.elkady.weathermap.models.City;

/**
 * Created by mak on 6/17/17.
 */

public interface AddCityContract {
    interface View extends BaseContract.BaseView {
        void setBookmarks(List<City> cities);
        void switchToDetailsScreen(City city);
        void confirmAddingCity(City city);
        void onCityBookmarked(City city);
        void onCityBookmarkRemoved(City city);
    }


    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadBookmarks();
        void showCityDetails(City city);
        void removeBookmark(City city);
        void resolveCity(double lat, double lng);
        void bookmarkCity(City city);
    }
}
