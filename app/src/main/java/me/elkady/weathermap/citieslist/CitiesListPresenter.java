package me.elkady.weathermap.citieslist;

import java.util.List;

import me.elkady.weathermap.R;
import me.elkady.weathermap.data.BookmarksRepository;
import me.elkady.weathermap.models.City;

/**
 * Created by mak on 6/17/17.
 */

class CitiesListPresenter implements CitiesListContract.Presenter {
    private CitiesListContract.View mView;
    private BookmarksRepository mBookmarksRepository;

    CitiesListPresenter(BookmarksRepository bookmarksRepository) {
        this.mBookmarksRepository = bookmarksRepository;
    }

    @Override
    public void attachView(CitiesListContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mBookmarksRepository.onDestroy();
        mView = null;
    }

    @Override
    public void loadBookmarks() {
        mBookmarksRepository.loadBookmarkedCities(new BookmarksRepository.OnBookmarkedCitiesLoaded() {
            @Override
            public void onBookmarkedCitiesLoaded(List<City> cities) {
                if(mView != null) {
                    mView.setBookmarks(cities);
                }
            }

            @Override
            public void onError() {
                if(mView != null) {
                    mView.showErrorMessage(R.string.cant_load_data);
                }
            }
        });
    }

    @Override
    public void removeBookmark(City city) {
        mBookmarksRepository.removeBookmarkCity(city);
        if(mView != null) {
            mView.onCityBookmarkRemoved(city);
        }
    }

    @Override
    public void showCityDetails(City city) {
        if(mView != null) {
            mView.switchToDetailsScreen(city);
        }
    }

    @Override
    public void searchForCity() {
        if(mView != null) {
            mView.switchToSearchScreen();
        }
    }
}
