package com.facebook.stream;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import java.util.HashMap;

public class Dispatcher {
    private Activity activity;
    HashMap<String, Class> handlers = new HashMap<>();
    boolean isWebViewShown;
    LinearLayout layout;
    private WebView webView;

    public Dispatcher(Activity activity2) {
        this.activity = activity2;
        this.layout = new LinearLayout(activity2);
        activity2.addContentView(this.layout, new LinearLayout.LayoutParams(-1, -1));
        this.isWebViewShown = false;
        showWebView();
    }

    public void addHandler(String name, Class clazz) {
        this.handlers.put(name, clazz);
    }

    public void runHandler(String name) {
        Class clazz = this.handlers.get(name);
        if (clazz != null) {
            try {
                Handler handler = (Handler) clazz.newInstance();
                handler.setDispatcher(this);
                handler.go();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void showWebView() {
        if (!this.isWebViewShown) {
            this.webView = new WebView(this.activity);
            this.webView.setWebViewClient(new AppWebViewClient(this, null));
            this.webView.getSettings().setJavaScriptEnabled(true);
            this.layout.addView(this.webView, new LinearLayout.LayoutParams(-1, -1));
            this.isWebViewShown = true;
        }
    }

    public void hideWebView() {
        this.layout.removeView(this.webView);
        this.isWebViewShown = false;
    }

    public boolean isWebViewShown() {
        return this.isWebViewShown;
    }

    public void loadData(String html) {
        this.webView.loadDataWithBaseURL("http://nada", html, "text/html", "utf8", "");
    }

    public void loadFile(String file) {
        this.webView.loadUrl(getAbsoluteUrl(file));
    }

    public static String getAbsoluteUrl(String file) {
        return "file:///android_asset/" + file;
    }

    public WebView getWebView() {
        return this.webView;
    }

    public Activity getActivity() {
        return this.activity;
    }

    /* access modifiers changed from: private */
    public class AppWebViewClient extends WebViewClient {
        private AppWebViewClient() {
        }

        /* synthetic */ AppWebViewClient(Dispatcher dispatcher, AppWebViewClient appWebViewClient) {
            this();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!url.startsWith("app://")) {
                return false;
            }
            Dispatcher.this.runHandler(url.substring(6));
            return true;
        }
    }
}
