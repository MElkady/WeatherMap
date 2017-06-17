package me.elkady.weathermap.citieslist;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.elkady.weathermap.HomeInterface;
import me.elkady.weathermap.R;
import me.elkady.weathermap.WeatherMapApp;
import me.elkady.weathermap.data.BookmarksRepositoryImpl;
import me.elkady.weathermap.models.City;

public class CitiesListFragment extends Fragment implements CitiesListContract.View {
    private CitiesListContract.Presenter mPresenter;

    private List<City> mBookmarkedCities;

    private RecyclerView mRecyclerView;
    private HomeInterface mListener;

    public CitiesListFragment() {
        // Required empty public constructor
    }

    public static CitiesListFragment newInstance() {
        return new CitiesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mPresenter = new CitiesListPresenter(WeatherMapApp.getBookmarksRepository());
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cities_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_cities);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        return v;
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
        refresh();

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        if(mListener != null && mListener.shouldShowAddCityButton()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.searchForCity();
                }
            });
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(mListener != null && mListener.shouldShowAddCityButton()) {
            inflater.inflate(R.menu.menu_cities_list, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_add_city) {
            mPresenter.searchForCity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setBookmarks(List<City> cities) {
        mBookmarkedCities = cities;
        mAdapter.notifyDataSetChanged();

        if(cities == null || cities.size() < 1) {
            getView().findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            getView().findViewById(R.id.empty_view).setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void switchToSearchScreen() {
        if(mListener != null) {
            mListener.switchToSearchScreen();
        }
    }

    @Override
    public void switchToDetailsScreen(City city) {
        if(mListener != null) {
            mListener.switchToDetailsScreen(city);
        }
    }

    @Override
    public void onCityBookmarkRemoved(City city) {
        mBookmarkedCities.remove(city);
        mAdapter.notifyDataSetChanged();
        if(mListener != null) {
            mListener.onBookmarksUpdated();
        }
    }

    @Override
    public void showErrorMessage(@StringRes int error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.loadBookmarks();
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
    }

    public void refresh() {
        mPresenter.loadBookmarks();
    }

    private class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivBookmark;
        CityViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_city_name);
            ivBookmark = (ImageView) itemView.findViewById(R.id.iv_bookmark);
        }

        void bindView(final City city) {
            tvTitle.setText(city.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.showCityDetails(city);
                }
            });
            ivBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.removeBookmark(city);
                }
            });
        }
    }

    private RecyclerView.Adapter<CityViewHolder> mAdapter = new RecyclerView.Adapter<CityViewHolder>() {
        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CityViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_bookmarked_city, parent, false));
        }

        @Override
        public void onBindViewHolder(CityViewHolder holder, int position) {
            if(mBookmarkedCities != null && mBookmarkedCities.size() > position) {
                holder.bindView(mBookmarkedCities.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return (mBookmarkedCities != null)? mBookmarkedCities.size() : 0;
        }
    };
}
