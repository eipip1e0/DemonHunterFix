package com.facebook.android;

import android.util.Log;
import com.facebook.android.AsyncFacebookRunner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public abstract class BaseRequestListener implements AsyncFacebookRunner.RequestListener {
    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onFacebookError(FacebookError e) {
        Log.e("Facebook", e.getMessage());
        e.printStackTrace();
    }

    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onFileNotFoundException(FileNotFoundException e) {
        Log.e("Facebook", e.getMessage());
        e.printStackTrace();
    }

    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onIOException(IOException e) {
        Log.e("Facebook", e.getMessage());
        e.printStackTrace();
    }

    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onMalformedURLException(MalformedURLException e) {
        Log.e("Facebook", e.getMessage());
        e.printStackTrace();
    }
}
