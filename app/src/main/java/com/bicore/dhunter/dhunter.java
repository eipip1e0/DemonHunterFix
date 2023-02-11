package com.bicore.dhunter;

import android.os.Bundle;
import android.util.Log;
import com.bicore.SKTMarket;

public class dhunter extends SKTMarket {
    static {
        System.loadLibrary("dhunter");
        iContentsWidth = -1;
        iContentsHeight = -1;
        AppID = "OA00136241";
        AdmobID = "a14d196dcc91a5a";
    }

    /* access modifiers changed from: protected */
    @Override // com.bicore.BicoreActivity, com.feelingk.iap.IAPActivity, com.bicore.SKTMarket
    public void onCreate(Bundle savedInstanceState) {
        Log.w("dhunter", "*Frame - onCreate");
        super.onCreate(savedInstanceState);
    }
}
