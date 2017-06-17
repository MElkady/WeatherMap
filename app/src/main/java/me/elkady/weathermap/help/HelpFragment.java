package me.elkady.weathermap.help;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.elkady.weathermap.R;

public class HelpFragment extends Fragment implements HelpContract.View {
    private HelpContract.Presenter mPresenter;

    public HelpFragment() {
    }

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new HelpPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        WebView webView = (WebView) v.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        webView.loadUrl("file:///android_asset/index.html");

        return v;
    }

    @Override
    public void showErrorMessage(@StringRes int error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                .show();
    }
}
