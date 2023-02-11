package com.bicore.dhunter;

import android.os.Bundle;
import android.util.Log;

import com.bicore.KTMarket;

public class dhunter extends KTMarket {
    static {
        System.loadLibrary("dhunter");
        iContentsWidth = -1;
        iContentsHeight = -1;
    }

    /* access modifiers changed from: protected */
    @Override // com.bicore.BicoreActivity
    public void onCreate(Bundle savedInstanceState) {
        Log.w("dhunter", "*Frame - onCreate");
        super.onCreate(savedInstanceState);
    }
}
