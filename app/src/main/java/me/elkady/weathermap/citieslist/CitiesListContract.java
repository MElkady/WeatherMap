package me.elkady.weathermap.citieslist;

import java.util.List;

import me.elkady.weathermap.BaseContract;
import me.elkady.weathermap.models.City;

/**
 * Created by mak on 6/17/17.
 */

public interface CitiesListContract {
    interface View extends BaseContract.BaseView {
        void setBookmarks(List<City> cities);
        void switchToSearchScreen();
        void switchToDetailsScreen(City city);
        void onCityBookmarkRemoved(City city);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadBookmarks();
        void removeBookmark(City city);
        void showCityDetails(City city);
        void searchForCity();
    }
}
