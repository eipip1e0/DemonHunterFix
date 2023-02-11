package com.feelingk.iap.net;

import java.io.UnsupportedEncodingException;

public class ItemWholeAuthConfirm extends MsgConfirm {
    private int itemAuthCount = 0;
    private ItemAuth[] items = null;

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.net.Confirm, com.feelingk.iap.net.MsgConfirm, com.feelingk.iap.net.Header
    public void parse(byte[] v) {
        super.parse(v);
        int offset = getMsgLength() + 14;
        this.itemAuthCount = v[offset];
        int offset2 = offset + 1;
        this.items = new ItemAuth[this.itemAuthCount];
        for (int i = 0; i < this.itemAuthCount; i++) {
            this.items[i] = new ItemAuth();
            byte[] _tempID = new byte[10];
            System.arraycopy(v, offset2, _tempID, 0, 10);
            this.items[i].pId = new String(_tempID);
            int offset3 = offset2 + 10;
            byte[] _tempName = new byte[30];
            System.arraycopy(v, offset3, _tempName, 0, 30);
            offset2 = offset3 + 30;
            try {
                this.items[i].pName = new String(_tempName, "MS949").trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public int getItemAuthCount() {
        return this.itemAuthCount;
    }

    public ItemAuth[] getItems() {
        return this.items;
    }
}
