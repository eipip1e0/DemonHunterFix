package com.feelingk.iap.net;

public class Header {
    public final int HEADER_SIZE = 12;
    private int mHeaderDataLength;
    private byte[] mHeaderSpecifier = new byte[2];

    /* access modifiers changed from: protected */
    public void parse(byte[] v) {
        System.arraycopy(v, 0, this.mHeaderSpecifier, 0, 2);
        byte[] buf = new byte[10];
        System.arraycopy(v, 2, buf, 0, 10);
        this.mHeaderDataLength = Integer.parseInt(new String(buf).trim()) - 1;
    }

    public byte[] getSpecifier() {
        return this.mHeaderSpecifier;
    }

    public int getDataLength() {
        return this.mHeaderDataLength;
    }
}
