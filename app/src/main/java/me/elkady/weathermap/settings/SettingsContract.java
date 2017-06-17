package me.elkady.weathermap.settings;

import me.elkady.weathermap.BaseContract;
import me.elkady.weathermap.models.UnitSystem;

/**
 * Created by mak on 6/18/17.
 */

public interface SettingsContract {
    interface View extends BaseContract.BaseView {
        void updateUI(UnitSystem unitSystem);
        void onBookmarksReset();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void setUnitSystem(UnitSystem unitSystem);
        void loadSettings();
        void resetBookmarks();
    }
}
