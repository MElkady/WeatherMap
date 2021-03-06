package me.elkady.weathermap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.elkady.weathermap.addcity.AddCityFragment;
import me.elkady.weathermap.citieslist.CitiesListFragment;
import me.elkady.weathermap.citydetails.CityDetailsFragment;
import me.elkady.weathermap.help.HelpFragment;
import me.elkady.weathermap.models.City;
import me.elkady.weathermap.settings.SettingsFragment;

public class HomeActivity extends AppCompatActivity implements HomeInterface {
    private boolean mDualPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View detailsFrame = findViewById(R.id.home_list_fragment);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;


        Fragment citiesListFragment = getSupportFragmentManager().findFragmentById(R.id.home_list_fragment);
        if(mDualPane && citiesListFragment == null) {
            citiesListFragment = CitiesListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.home_list_fragment, citiesListFragment).commit();
        }

        if(mDualPane) {
            Fragment detailsFragment = getSupportFragmentManager().findFragmentById(R.id.home_details_fragment);
            if (detailsFragment != null && detailsFragment instanceof CitiesListFragment) {
                detailsFragment = AddCityFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.home_details_fragment, detailsFragment).commit();
            }
        } else {
            Fragment detailsFragment = getSupportFragmentManager().findFragmentById(R.id.home_details_fragment);
            if (detailsFragment == null) {
                detailsFragment = CitiesListFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.home_details_fragment, detailsFragment).commit();
            }
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_help) {
            Fragment fragment = HelpFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_details_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
            return  true;
        } else if (item.getItemId() == R.id.item_settings) {
            Fragment fragment = SettingsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_details_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchToSearchScreen() {
        Fragment fragment = AddCityFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_details_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void switchToDetailsScreen(City city) {
        Fragment fragment = CityDetailsFragment.newInstance(city);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_details_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBookmarksUpdated() {
        Fragment listFragment = getSupportFragmentManager().findFragmentById(R.id.home_list_fragment);
        if(listFragment != null && listFragment instanceof CitiesListFragment) {
             ((CitiesListFragment) listFragment).refresh();
        }

        Fragment detailsFragment = getSupportFragmentManager().findFragmentById(R.id.home_details_fragment);
        if(detailsFragment != null && detailsFragment instanceof AddCityFragment) {
            ((AddCityFragment) detailsFragment).refresh();
        }
    }
}
