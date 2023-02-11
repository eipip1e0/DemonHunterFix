package com.facebook.android;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.android.Facebook;

public class FbDialog extends Dialog {
    static final float[] DIMENSIONS_LANDSCAPE = {460.0f, 260.0f};
    static final float[] DIMENSIONS_PORTRAIT = {280.0f, 420.0f};
    static final String DISPLAY_STRING = "touch";
    static final int FB_BLUE = -9599820;
    static final String FB_ICON = "icon.png";
    static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(-1, -1);
    static final int MARGIN = 4;
    static final int PADDING = 2;
    private LinearLayout mContent;
    private Facebook.DialogListener mListener;
    private ProgressDialog mSpinner;
    private TextView mTitle;
    private String mUrl;
    private WebView mWebView;

    public FbDialog(Context context, String url, Facebook.DialogListener listener) {
        super(context);
        this.mUrl = url;
        this.mListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSpinner = new ProgressDialog(getContext());
        this.mSpinner.requestWindowFeature(1);
        this.mSpinner.setMessage("Loading...");
        this.mContent = new LinearLayout(getContext());
        this.mContent.setOrientation(1);
        setUpTitle();
        setUpWebView();
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        float scale = getContext().getResources().getDisplayMetrics().density;
        float[] dimensions = display.getWidth() < display.getHeight() ? DIMENSIONS_PORTRAIT : DIMENSIONS_LANDSCAPE;
        addContentView(this.mContent, new FrameLayout.LayoutParams((int) ((dimensions[0] * scale) + 0.5f), (int) ((dimensions[1] * scale) + 0.5f)));
    }

    private void setUpTitle() {
        requestWindowFeature(1);
        Drawable icon = getContext().getResources().getDrawable(getContext().getResources().getIdentifier("facebook_icon.png", null, null));
        this.mTitle = new TextView(getContext());
        this.mTitle.setText("Facebook");
        this.mTitle.setTextColor(-1);
        this.mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        this.mTitle.setBackgroundColor(FB_BLUE);
        this.mTitle.setPadding(6, 4, 4, 4);
        this.mTitle.setCompoundDrawablePadding(6);
        this.mTitle.setCompoundDrawablesWithIntrinsicBounds(icon, (Drawable) null, (Drawable) null, (Drawable) null);
        this.mContent.addView(this.mTitle);
    }

    private void setUpWebView() {
        this.mWebView = new WebView(getContext());
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.setWebViewClient(new FbWebViewClient(this, null));
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.loadUrl(this.mUrl);
        this.mWebView.setLayoutParams(FILL);
        this.mContent.addView(this.mWebView);
    }

    /* access modifiers changed from: private */
    public class FbWebViewClient extends WebViewClient {
        private FbWebViewClient() {
        }

        /* synthetic */ FbWebViewClient(FbDialog fbDialog, FbWebViewClient fbWebViewClient) {
            this();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("Facebook-WebView", "Redirect URL: " + url);
            if (url.startsWith(Facebook.REDIRECT_URI)) {
                Bundle values = Util.parseUrl(url);
                String error = values.getString("error");
                if (error == null) {
                    error = values.getString("error_type");
                }
                if (error == null) {
                    FbDialog.this.mListener.onComplete(values);
                } else if (error.equals("access_denied") || error.equals("OAuthAccessDeniedException")) {
                    FbDialog.this.mListener.onCancel();
                } else {
                    FbDialog.this.mListener.onFacebookError(new FacebookError(error));
                }
                FbDialog.this.dismiss();
                return true;
            } else if (url.startsWith(Facebook.CANCEL_URI)) {
                FbDialog.this.mListener.onCancel();
                FbDialog.this.dismiss();
                return true;
            } else if (url.contains(FbDialog.DISPLAY_STRING)) {
                return false;
            } else {
                FbDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            FbDialog.this.mListener.onError(new DialogError(description, errorCode, failingUrl));
            FbDialog.this.dismiss();
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("Facebook-WebView", "Webview loading URL: " + url);
            super.onPageStarted(view, url, favicon);
            FbDialog.this.mSpinner.show();
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = FbDialog.this.mWebView.getTitle();
            if (title != null && title.length() > 0) {
                FbDialog.this.mTitle.setText(title);
            }
            FbDialog.this.mSpinner.dismiss();
        }
    }
}
