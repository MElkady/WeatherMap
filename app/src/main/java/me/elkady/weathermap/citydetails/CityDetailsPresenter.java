package me.elkady.weathermap.citydetails;

import java.util.List;

import me.elkady.weathermap.R;
import me.elkady.weathermap.data.CityDataRepository;
import me.elkady.weathermap.data.SettingsRepository;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;

/**
 * Created by mak on 6/17/17.
 */

public class CityDetailsPresenter implements CityDetailsContract.Presenter {
    private CityDetailsContract.View mView;
    private CityDataRepository mCityDataRepository;
    private SettingsRepository mSettingsRepository;

    public CityDetailsPresenter(CityDataRepository cityDataRepository, SettingsRepository settingsRepository) {
        this.mCityDataRepository = cityDataRepository;
        this.mSettingsRepository = settingsRepository;
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
    public void loadUnitSystem() {
        if(mView != null) {
            mView.setUnitSystem(mSettingsRepository.getUnitSystem());
        }
    }

    @Override
    public void loadCityDetails(City city) {
        if(mView != null) {
            mView.showLoading();
        }
        mCityDataRepository.loadCityDetails(city, mSettingsRepository.getUnitSystem(), new CityDataRepository.OnDetailsLoaded() {
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
        mCityDataRepository.loadCityForecast(city, mSettingsRepository.getUnitSystem(), new CityDataRepository.OnForecastLoaded() {
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
