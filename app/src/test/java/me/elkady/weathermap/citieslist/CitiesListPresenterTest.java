package me.elkady.weathermap.citieslist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import me.elkady.weathermap.data.BookmarksRepository;
import me.elkady.weathermap.models.City;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by mak on 6/17/17.
 */
public class CitiesListPresenterTest {
    private CitiesListContract.Presenter mPresenter;

    @Mock
    private BookmarksRepository mBookmarksRepository;

    @Mock
    private CitiesListContract.View mView;

    @Captor
    private ArgumentCaptor<BookmarksRepository.OnBookmarkedCitiesLoaded> mOnBookmarkedCitiesLoaded;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mPresenter = new CitiesListPresenter(mBookmarksRepository);
        mPresenter.attachView(mView);
    }

    @After
    public void cleanup(){
        mPresenter.detachView();
    }

    @Test
    public void loadBookmarks() throws Exception {
        List<City> cities = new ArrayList<>();
        cities.add(new City("Test1", 1, 1));
        cities.add(new City("Test2", 2, 2));


        mPresenter.loadBookmarks();

        verify(mBookmarksRepository).loadBookmarkedCities(mOnBookmarkedCitiesLoaded.capture());
        mOnBookmarkedCitiesLoaded.getValue().onBookmarkedCitiesLoaded(cities);

        verify(mView).setBookmarks(cities);
    }

    @Test
    public void removeBookmark() throws Exception {
        City city = new City("Test1", 1, 1);

        mPresenter.removeBookmark(city);
        verify(mBookmarksRepository).removeBookmarkCity(city);
        verify(mView).onCityBookmarkRemoved(city);
    }

}