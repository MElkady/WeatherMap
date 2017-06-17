package me.elkady.weathermap.settings;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import me.elkady.weathermap.R;
import me.elkady.weathermap.WeatherMapApp;
import me.elkady.weathermap.models.UnitSystem;

public class SettingsFragment extends Fragment implements SettingsContract.View {
    private SettingsContract.Presenter mPresenter;

    public SettingsFragment() {
    }
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new SettingsPresenter(WeatherMapApp.getSettingsRepository(), WeatherMapApp.getBookmarksRepository());
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ((RadioButton)v.findViewById(R.id.rb_metric)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mPresenter.setUnitSystem(UnitSystem.METRIC);
                }
            }
        });
        ((RadioButton)v.findViewById(R.id.rb_imperial)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mPresenter.setUnitSystem(UnitSystem.IMPERIAL);
                }
            }
        });
        v.findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.resetBookmarks();
            }
        });
        return v;
    }

    @Override
    public void showErrorMessage(@StringRes int error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void updateUI(UnitSystem unitSystem) {
        if(unitSystem == UnitSystem.IMPERIAL) {
            ((RadioButton) getView().findViewById(R.id.rb_imperial)).setChecked(true);
        } else {
            ((RadioButton) getView().findViewById(R.id.rb_metric)).setChecked(true);
        }
    }

    @Override
    public void onBookmarksReset() {
        Snackbar.make(getView(), R.string.all_bookmarks_removed, Snackbar.LENGTH_LONG)
                .show();
    }
}
