package com.feelingk.iap.net;

import java.io.UnsupportedEncodingException;

public class ItemInfoConfirm extends MsgConfirm {
    private String mItemPrice;
    private String mItemTitle;
    private String mPeriod;
    private String mTCash;

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.net.Confirm, com.feelingk.iap.net.MsgConfirm, com.feelingk.iap.net.Header
    public void parse(byte[] v) {
        super.parse(v);
        int offset = getMsgLength() + 14;
        try {
            byte[] _byte = new byte[30];
            System.arraycopy(v, offset, _byte, 0, 30);
            this.mItemTitle = new String(_byte, "MS949").trim();
            int offset2 = offset + 30;
            byte[] _byte2 = new byte[10];
            System.arraycopy(v, offset2, _byte2, 0, 10);
            this.mItemPrice = new String(_byte2, "MS949").trim();
            int offset3 = offset2 + 10;
            byte[] _byte3 = new byte[10];
            System.arraycopy(v, offset3, _byte3, 0, 10);
            this.mPeriod = new String(_byte3, "MS949").trim();
            byte[] _byte4 = new byte[10];
            System.arraycopy(v, offset3 + 10, _byte4, 0, 10);
            this.mTCash = new String(_byte4, "MS949").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getItemTitle() {
        return this.mItemTitle;
    }

    public String getItemPrice() {
        return this.mItemPrice;
    }

    public String getItemPeriod() {
        return this.mPeriod;
    }

    public String getItemTCash() {
        return this.mTCash;
    }
}
