package me.elkady.weathermap.addcity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.elkady.weathermap.HomeInterface;
import me.elkady.weathermap.R;
import me.elkady.weathermap.WeatherMapApp;
import me.elkady.weathermap.models.City;


public class AddCityFragment extends SupportMapFragment implements OnMapReadyCallback, AddCityContract.View, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private AddCityContract.Presenter mPresenter;
    private Map<Marker, City> markers = new HashMap<>();
    private HomeInterface mListener;

    public AddCityFragment() {
    }

    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new AddCityPresenter(WeatherMapApp.getBookmarksRepository());
        mPresenter.attachView(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeInterface) {
            mListener = (HomeInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getMapAsync(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        refresh();
    }

    @Override
    public void showErrorMessage(@StringRes int error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mPresenter.resolveCity(latLng.latitude, latLng.longitude);
    }

    @Override
    public void setBookmarks(List<City> cities) {
        markers.clear();
        mMap.clear();
        if(cities != null) {
            for(City city : cities) {
                LatLng latLng = new LatLng(city.getLat(), city.getLng());
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(city.getName()));
                markers.put(marker, city);
            }
        }
    }

    @Override
    public void switchToDetailsScreen(City city) {
        if(mListener != null) {
            mListener.switchToDetailsScreen(city);
        }
    }

    @Override
    public void confirmAddingCity(final City city) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.bookmark_city);
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_add_this_city, city.getName()));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.bookmarkCity(city);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onCityBookmarked(City city) {
        LatLng latLng = new LatLng(city.getLat(), city.getLng());
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(city.getName()));
        markers.put(marker, city);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        if(mListener != null) {
            mListener.onBookmarksUpdated();
        }
    }

    @Override
    public void onCityBookmarkRemoved(City city) {
        if(mListener != null) {
            mListener.onBookmarksUpdated();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        City city = markers.get(marker);
        mPresenter.showCityDetails(city);
        return true;
    }

    public void refresh() {
        mPresenter.loadBookmarks();
    }
}
