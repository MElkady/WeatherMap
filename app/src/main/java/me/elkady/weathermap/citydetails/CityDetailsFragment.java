package me.elkady.weathermap.citydetails;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import me.elkady.weathermap.R;
import me.elkady.weathermap.WeatherMapApp;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.models.CityDetails;


public class CityDetailsFragment extends Fragment implements CityDetailsContract.View {
    private static final String ARG_CITY = "arg_city";
    private CityDetailsContract.Presenter mPresenter;

    private City mCity;
    private CityDetails mTodayWeather;
    private List<CityDetails> mForecast;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

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
        mPresenter.loadForecast(mCity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city_details, container, false);
        v.findViewById(R.id.backdrop).setVisibility(View.GONE);
        ((CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar)).setTitle(mCity.getName());

        RecyclerView rvForecase = (RecyclerView) v.findViewById(R.id.rv_forecast);
        rvForecase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvForecase.setAdapter(mAdapter);

        return v;
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
        mTodayWeather = cityDetails;

        ImageView iv = (ImageView) getView().findViewById(R.id.backdrop);
        iv.setVisibility(View.VISIBLE);

        if(cityDetails.getTemp() > 35) {
            iv.setImageResource(R.drawable.hot);
        } else if(cityDetails.getTemp() > 25) {
            iv.setImageResource(R.drawable.warm);
        } else if(cityDetails.getTemp() > 10) {
            iv.setImageResource(R.drawable.good);
        } else {
            iv.setImageResource(R.drawable.cold);
        }

        ((TextView) getView().findViewById(R.id.tv_temperature)).setText(getString(R.string.temperature_is, cityDetails.getTemp()));
        ((TextView) getView().findViewById(R.id.tv_min_temperature)).setText(getString(R.string.min_temperature_is, cityDetails.getMinTemp()));
        ((TextView) getView().findViewById(R.id.tv_max_temperature)).setText(getString(R.string.max_temperature_is, cityDetails.getMaxTemp()));
        ((TextView) getView().findViewById(R.id.tv_humidity)).setText(getString(R.string.humidity_is, cityDetails.getHumidity()));
        ((TextView) getView().findViewById(R.id.tv_rain)).setText(getString(R.string.rain_is, cityDetails.getRainChance()));
        ((TextView) getView().findViewById(R.id.tv_wind_direction)).setText(getString(R.string.wind_direction_is, cityDetails.getWindDeg()));
        ((TextView) getView().findViewById(R.id.tv_wind_speed)).setText(getString(R.string.wind_speed_is, cityDetails.getWindSpeed()));



    }

    @Override
    public void displayForecast(List<CityDetails> cityDetails) {
        mForecast = cityDetails;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        getView().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    class ForecaseViewHolder extends RecyclerView.ViewHolder {
        public ForecaseViewHolder(View itemView) {
            super(itemView);
        }

        void bindView(CityDetails cityDetails) {
            ((TextView) itemView.findViewById(R.id.tv_date)).setText(sdf.format(cityDetails.getDate()));
            ((TextView) itemView.findViewById(R.id.tv_temperature)).setText(getString(R.string.temperature_is, cityDetails.getTemp()));
            ((TextView) itemView.findViewById(R.id.tv_min_temperature)).setText(getString(R.string.min_temperature_is, cityDetails.getMinTemp()));
            ((TextView) itemView.findViewById(R.id.tv_max_temperature)).setText(getString(R.string.max_temperature_is, cityDetails.getMaxTemp()));
            ((TextView) itemView.findViewById(R.id.tv_humidity)).setText(getString(R.string.humidity_is, cityDetails.getHumidity()));
            ((TextView) itemView.findViewById(R.id.tv_rain)).setText(getString(R.string.rain_is, cityDetails.getRainChance()));
            ((TextView) itemView.findViewById(R.id.tv_wind_direction)).setText(getString(R.string.wind_direction_is, cityDetails.getWindDeg()));
            ((TextView) itemView.findViewById(R.id.tv_wind_speed)).setText(getString(R.string.wind_speed_is, cityDetails.getWindSpeed()));
        }
    }

    private RecyclerView.Adapter<ForecaseViewHolder> mAdapter = new RecyclerView.Adapter<ForecaseViewHolder>() {
        @Override
        public ForecaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ForecaseViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_forecast, parent, false));
        }

        @Override
        public void onBindViewHolder(ForecaseViewHolder holder, int position) {
            if(mForecast != null && mForecast.size() > position) {
                holder.bindView(mForecast.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return (mForecast != null)? mForecast.size() : 0;
        }
    };
}
