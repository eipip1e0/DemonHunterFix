package com.facebook.android;

public class FacebookError extends Throwable {
    private static final long serialVersionUID = 1;
    private int mErrorCode = 0;
    private String mErrorType;

    public FacebookError(String message) {
        super(message);
    }

    public FacebookError(String message, String type, int code) {
        super(message);
        this.mErrorType = type;
        this.mErrorCode = code;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorType() {
        return this.mErrorType;
    }
}
