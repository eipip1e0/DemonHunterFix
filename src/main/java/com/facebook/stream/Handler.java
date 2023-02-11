package com.facebook.stream;

import android.app.Activity;
import android.webkit.WebView;

public abstract class Handler {
    protected Dispatcher dispatcher;

    public abstract void go();

    public void setDispatcher(Dispatcher dispatcher2) {
        this.dispatcher = dispatcher2;
    }

    public Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    public WebView getWebView() {
        return this.dispatcher.getWebView();
    }

    public Activity getActivity() {
        return this.dispatcher.getActivity();
    }
}
