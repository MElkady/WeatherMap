package me.elkady.weathermap;

import android.support.annotation.StringRes;

/**
 * Created by mak on 6/17/17.
 */

public interface BaseContract {
    interface BaseView {
        void showErrorMessage(@StringRes int error);
    }

    interface BasePresenter<T extends BaseView> {
        void attachView(T view);
        void detachView();
    }
}
