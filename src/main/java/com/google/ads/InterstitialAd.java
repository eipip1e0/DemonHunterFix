package com.google.ads;

import android.app.Activity;

public class InterstitialAd implements Ad {
    private d a;

    public InterstitialAd(Activity activity, String adUnitId) {
        this.a = new d(activity, this, null, adUnitId);
    }

    @Override // com.google.ads.Ad
    public boolean isReady() {
        return this.a.l();
    }

    @Override // com.google.ads.Ad
    public void loadAd(AdRequest adRequest) {
        this.a.a(adRequest);
    }

    @Override // com.google.ads.Ad
    public void setAdListener(AdListener adListener) {
        this.a.a(adListener);
    }

    public void show() {
    }

    @Override // com.google.ads.Ad
    public void stopLoading() {
        this.a.t();
    }
}
