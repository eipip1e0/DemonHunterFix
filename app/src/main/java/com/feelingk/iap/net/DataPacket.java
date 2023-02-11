package com.feelingk.iap.net;

public class DataPacket extends Confirm {
    private byte[] data;

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.net.Confirm, com.feelingk.iap.net.Header
    public void parse(byte[] v) {
        super.parse(v);
        int len = v.length - 12;
        this.data = new byte[len];
        System.arraycopy(v, 12, this.data, 0, len);
    }

    public void setHeader(byte[] v) {
        super.parse(v);
    }

    public byte[] getData() {
        return this.data;
    }
}
