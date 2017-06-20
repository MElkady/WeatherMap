package me.elkady.weathermap.data;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.elkady.weathermap.models.UnitSystem;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by MAK on 6/20/17.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SettingsRepositoryTest {
    private SettingsRepository mSettingsRepository;

    @Before
    public void setUp() throws Exception {
        mSettingsRepository = new SettingsRepositoryImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() throws Exception {
        UnitSystem unitSystem = UnitSystem.IMPERIAL;

        mSettingsRepository.setUnitSystem(unitSystem);

        assertThat(mSettingsRepository.getUnitSystem(), is(unitSystem));
    }

}