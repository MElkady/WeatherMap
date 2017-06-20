package me.elkady.weathermap.data;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import me.elkady.weathermap.models.City;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by MAK on 6/20/17.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class BookmarksRepositoryTest {
    private BookmarksRepository mBookmarksRepository;
    private static final City TEST_CITY = new City("Test", 1, 1);

    @Before
    public void setUp() throws Exception {
        mBookmarksRepository = new BookmarksRepositoryImpl();
        mBookmarksRepository.clear();
    }

    @Test
    public void testAddLoad() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        mBookmarksRepository.bookmarkCity(TEST_CITY);
        mBookmarksRepository.loadBookmarkedCities(new BookmarksRepository.OnBookmarkedCitiesLoaded() {
            @Override
            public void onBookmarkedCitiesLoaded(List<City> cities) {
                assertThat(cities.size(), is(1));
                assertThat(cities.get(0).getName(), is(TEST_CITY.getName()));

                createDelay();
                signal.countDown();
            }

            @Override
            public void onError() {
                fail();
                createDelay();
                signal.countDown();
            }
        });

        signal.await();
    }

    private void createDelay(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}