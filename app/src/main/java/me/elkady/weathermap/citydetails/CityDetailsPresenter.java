package me.elkady.weathermap.citydetails;

import java.util.List;

import me.elkady.weathermap.R;
import me.elkady.weathermap.data.CityDataRepository;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;

/**
 * Created by mak on 6/17/17.
 */

public class CityDetailsPresenter implements CityDetailsContract.Presenter {
    private CityDetailsContract.View mView;
    private CityDataRepository mCityDataRepository;

    public CityDetailsPresenter(CityDataRepository cityDataRepository) {
        this.mCityDataRepository = cityDataRepository;
    }

    @Override
    public void attachView(CityDetailsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadCityDetails(City city) {
        if(mView != null) {
            mView.showLoading();
        }
        mCityDataRepository.loadCityDetails(city, new CityDataRepository.OnDetailsLoaded() {
            @Override
            public void onDetailsLoaded(CityDetails cityDetails) {
                if(mView != null) {
                    mView.hideLoading();
                    mView.displayCityDetails(cityDetails);
                }
            }

            @Override
            public void onError() {
                if(mView != null) {
                    mView.hideLoading();
                    mView.showErrorMessage(R.string.cant_load_data);
                }
            }
        });
    }

    @Override
    public void loadForecast(City city) {
        mCityDataRepository.loadCityForecast(city, new CityDataRepository.OnForecastLoaded() {
            @Override
            public void onForecastLoaded(List<CityDetails> cityDetails) {
                if(mView != null) {
                    mView.displayForecast(cityDetails);
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
}
