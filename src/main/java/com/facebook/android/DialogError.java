package com.facebook.android;

public class DialogError extends Throwable {
    private static final long serialVersionUID = 1;
    private int mErrorCode;
    private String mFailingUrl;

    public DialogError(String message, int errorCode, String failingUrl) {
        super(message);
        this.mErrorCode = errorCode;
        this.mFailingUrl = failingUrl;
    }

    /* access modifiers changed from: package-private */
    public int getErrorCode() {
        return this.mErrorCode;
    }

    /* access modifiers changed from: package-private */
    public String getFailingUrl() {
        return this.mFailingUrl;
    }
}
