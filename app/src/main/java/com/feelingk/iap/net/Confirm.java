package com.feelingk.iap.net;

public class Confirm extends Header {
    private byte resultCode;

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.net.Header
    public void parse(byte[] v) {
        super.parse(v);
        this.resultCode = v[12];
    }

    public byte getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(byte code) {
        this.resultCode = code;
    }
}
