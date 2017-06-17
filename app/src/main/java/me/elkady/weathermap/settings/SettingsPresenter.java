package me.elkady.weathermap.settings;

import me.elkady.weathermap.data.BookmarksRepository;
import me.elkady.weathermap.data.SettingsRepository;
import me.elkady.weathermap.models.UnitSystem;

/**
 * Created by mak on 6/18/17.
 */

public class SettingsPresenter implements SettingsContract.Presenter {
    private SettingsContract.View mView;

    private SettingsRepository mSettingsRepository;
    private BookmarksRepository mBookmarksRepository;

    public SettingsPresenter(SettingsRepository settingsRepository, BookmarksRepository bookmarksRepository) {
        this.mSettingsRepository = settingsRepository;
        this.mBookmarksRepository = bookmarksRepository;
    }

    @Override
    public void attachView(SettingsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void setUnitSystem(UnitSystem unitSystem) {
        mSettingsRepository.setUnitSystem(unitSystem);
    }

    @Override
    public void loadSettings() {
        if(mView != null) {
            mView.updateUI(mSettingsRepository.getUnitSystem());
        }
    }

    @Override
    public void resetBookmarks() {
        mBookmarksRepository.clear();
        if(mView != null) {
            mView.onBookmarksReset();
        }
    }
}
