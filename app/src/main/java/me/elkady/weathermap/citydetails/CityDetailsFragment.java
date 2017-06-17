package me.elkady.weathermap.citydetails;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.elkady.weathermap.R;
import me.elkady.weathermap.WeatherMapApp;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;


public class CityDetailsFragment extends Fragment implements CityDetailsContract.View {
    private static final String ARG_CITY = "arg_city";
    private CityDetailsContract.Presenter mPresenter;

    private City mCity;
    private CityDetails mCityDetails;

    public CityDetailsFragment() {
    }

    public static CityDetailsFragment newInstance(City city) {
        Bundle b = new Bundle();
        b.putSerializable(ARG_CITY, city);

        CityDetailsFragment fragment = new CityDetailsFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_CITY);
        }

        mPresenter = new CityDetailsPresenter(WeatherMapApp.getCityDataRepository());
        mPresenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadCityDetails(mCity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_details, container, false);
    }

    @Override
    public void showErrorMessage(@StringRes int error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.loadCityDetails(mCity);
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
    }

    @Override
    public void displayCityDetails(CityDetails cityDetails) {
        mCityDetails = cityDetails;
        Log.d("TEST", "Details -> " + mCityDetails.toString());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
