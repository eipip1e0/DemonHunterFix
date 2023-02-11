package com.feelingk.iap.net;

import com.feelingk.iap.util.CommonF;

public class InitConfirm extends MsgConfirm {
    static final String TAG = "InitConfirm";
    private byte[] ExpireDate;
    private byte[] eToken;
    private int nCount = 0;
    private int nTokenLength = 0;

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.net.Confirm, com.feelingk.iap.net.MsgConfirm, com.feelingk.iap.net.Header
    public void parse(byte[] v) {
        super.parse(v);
        int offset = getMsgLength() + 14;
        try {
            CommonF.LOGGER.i(TAG, "InitConfirm result code : " + ((int) getResultCode()));
            int offset2 = offset + 1;
            try {
                this.nCount = v[offset];
                this.ExpireDate = new byte[14];
                System.arraycopy(v, offset2, this.ExpireDate, 0, 14);
                int offset3 = offset2 + 14;
                int offset4 = offset3 + 1;
                this.nTokenLength = v[offset3];
                if (this.nTokenLength > 4) {
                    this.eToken = new byte[this.nTokenLength];
                    System.arraycopy(v, offset4, this.eToken, 0, this.nTokenLength);
                }
            } catch (Exception e) {
                setResultCode((byte) -10);
            }
        } catch (Exception e2) {
            setResultCode((byte) -10);
        }
    }

    public byte[] getExpireDate() {
        return this.ExpireDate;
    }

    public int getCount() {
        return this.nCount;
    }

    public int getTokenLength() {
        return this.nTokenLength;
    }

    public byte[] getToken() {
        return this.eToken;
    }
}
