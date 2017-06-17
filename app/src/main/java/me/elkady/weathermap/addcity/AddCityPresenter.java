package me.elkady.weathermap.addcity;

import java.util.List;

import me.elkady.weathermap.R;
import me.elkady.weathermap.addcity.AddCityContract.Presenter;
import me.elkady.weathermap.data.BookmarksRepository;
import me.elkady.weathermap.models.City;

/**
 * Created by mak on 6/17/17.
 */

class AddCityPresenter implements Presenter {
    private AddCityContract.View mView;
    private BookmarksRepository mBookmarksRepository;

    AddCityPresenter(BookmarksRepository bookmarksRepository) {
        mBookmarksRepository = bookmarksRepository;
    }


    @Override
    public void attachView(AddCityContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
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
    public void showCityDetails(City city) {
        if(mView != null) {
            mView.switchToDetailsScreen(city);
        }
    }

    @Override
    public void removeBookmark(City city) {
        if(mView != null) {
            mView.switchToDetailsScreen(city);
        }
    }

    @Override
    public void resolveCity(double lat, double lng) {
        City c = mBookmarksRepository.getCity(lat, lng);
        if (c != null) {
            if(mView != null) {
                mView.confirmAddingCity(c);
            }
        } else {
            if(mView != null) {
                mView.showErrorMessage(R.string.unrecognized_city);
            }
        }
    }

    @Override
    public void bookmarkCity(City city) {
        mBookmarksRepository.bookmarkCity(city);
        if(mView != null) {
            mView.onCityBookmarked(city);
        }
    }
}
