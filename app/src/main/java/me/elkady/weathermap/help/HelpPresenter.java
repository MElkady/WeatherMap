package me.elkady.weathermap.help;

/**
 * Created by mak on 6/17/17.
 */

public class HelpPresenter implements HelpContract.Presenter {
    private HelpContract.View mView;

    @Override
    public void attachView(HelpContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
