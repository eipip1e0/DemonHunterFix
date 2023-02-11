package com.google.ads;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdView extends RelativeLayout implements Ad {
    private d a;

    public AdView(Activity activity, AdSize adSize, String adUnitId) {
        super(activity.getApplicationContext());
        a(activity, adSize, adUnitId);
    }

    public AdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        AdSize adSize;
        String str = "http://schemas.android.com/apk/res/" + context.getPackageName();
        int attributeIntValue = attrs.getAttributeIntValue(str, "adSize", -1);
        switch (attributeIntValue) {
            case 1:
                adSize = AdSize.BANNER;
                break;
            case 2:
                adSize = AdSize.IAB_MRECT;
                break;
            case 3:
                adSize = AdSize.IAB_BANNER;
                break;
            case 4:
                adSize = AdSize.IAB_LEADERBOARD;
                break;
            default:
                t.e("Invalid adSize parameter in XML layout: " + attributeIntValue + ". Defaulting to BANNER.");
                adSize = AdSize.BANNER;
                break;
        }
        String attributeValue = attrs.getAttributeValue(str, "adUnitId");
        if (attributeValue == null) {
            t.b("AdView missing required XML attribute adUnitId.");
        }
        if (isInEditMode()) {
            TextView textView = new TextView(context, attrs);
            textView.setGravity(17);
            textView.setText("Ads by Google");
            setGravity(17);
            addView(textView, (int) TypedValue.applyDimension(1, (float) adSize.getWidth(), context.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(1, (float) adSize.getHeight(), context.getResources().getDisplayMetrics()));
        } else if (context instanceof Activity) {
            a((Activity) context, adSize, attributeValue);
        } else {
            t.b("AdView was initialized with a Context that wasn't an Activity.");
        }
    }

    public AdView(Context context, AttributeSet attrs, int i) {
        this(context, attrs);
    }

    private void a(Activity activity, AdSize adSize, String str) {
        this.a = new d(activity, this, adSize, str);
        setGravity(17);
        setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        addView(this.a.g(), (int) TypedValue.applyDimension(1, (float) adSize.getWidth(), activity.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(1, (float) adSize.getHeight(), activity.getResources().getDisplayMetrics()));
    }

    @Override // com.google.ads.Ad
    public boolean isReady() {
        if (this.a == null) {
            return false;
        }
        return this.a.l();
    }

    public boolean isRefreshing() {
        return this.a.m();
    }

    @Override // com.google.ads.Ad
    public void loadAd(AdRequest adRequest) {
        Activity c = this.a.c();
        if (c == null) {
            t.e("activity was null while checking permissions.");
            return;
        }
        Context applicationContext = c.getApplicationContext();
        PackageManager packageManager = applicationContext.getPackageManager();
        String packageName = applicationContext.getPackageName();
        if (packageManager.checkPermission("android.permission.INTERNET", packageName) == -1 || packageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", packageName) == -1) {
            TextView textView = new TextView(applicationContext);
            textView.setGravity(17);
            textView.setText("You must have INTERNET and ACCESS_NETWORK_STATE permissions to serve ads.");
            textView.setTextColor(-65536);
            setGravity(17);
            addView(textView);
            return;
        }
        if (isRefreshing()) {
            this.a.a();
        }
        this.a.a(adRequest);
    }

    @Override // com.google.ads.Ad
    public void setAdListener(AdListener adListener) {
        this.a.a(adListener);
    }

    @Override // com.google.ads.Ad
    public void stopLoading() {
        this.a.t();
    }
}
