package com.facebook.stream;

import android.util.Log;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.JSONException;
import org.json.JSONObject;

/* access modifiers changed from: package-private */
public abstract class AsyncRequestListener implements AsyncFacebookRunner.RequestListener {
    public abstract void onComplete(JSONObject jSONObject);

    AsyncRequestListener() {
    }

    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onComplete(String response) {
        try {
            onComplete(Util.parseJson(response));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("facebook-stream", "JSON Error:" + e.getMessage());
        } catch (FacebookError e2) {
            Log.e("facebook-stream", "Facebook Error:" + e2.getMessage());
        }
    }

    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onFacebookError(FacebookError e) {
        Log.e("stream", "Facebook Error:" + e.getMessage());
    }

    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onFileNotFoundException(FileNotFoundException e) {
        Log.e("stream", "Resource not found:" + e.getMessage());
    }

    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onIOException(IOException e) {
        Log.e("stream", "Network Error:" + e.getMessage());
    }

    @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
    public void onMalformedURLException(MalformedURLException e) {
        Log.e("stream", "Invalid URL:" + e.getMessage());
    }
}
