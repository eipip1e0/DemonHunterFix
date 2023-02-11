package com.facebook.android;

import com.facebook.android.Facebook;

public abstract class BaseDialogListener implements Facebook.DialogListener {
    @Override // com.facebook.android.Facebook.DialogListener
    public void onFacebookError(FacebookError e) {
        e.printStackTrace();
    }

    @Override // com.facebook.android.Facebook.DialogListener
    public void onError(DialogError e) {
        e.printStackTrace();
    }

    @Override // com.facebook.android.Facebook.DialogListener
    public void onCancel() {
    }
}
