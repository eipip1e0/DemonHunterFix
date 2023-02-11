package com.facebook.android;

import android.content.Context;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class AsyncFacebookRunner {
    Facebook fb;

    public interface RequestListener {
        void onComplete(String str);

        void onFacebookError(FacebookError facebookError);

        void onFileNotFoundException(FileNotFoundException fileNotFoundException);

        void onIOException(IOException iOException);

        void onMalformedURLException(MalformedURLException malformedURLException);
    }

    public AsyncFacebookRunner(Facebook fb2) {
        this.fb = fb2;
    }

    public void logout(final Context context, final RequestListener listener) {
        new Thread() {
            /* class com.facebook.android.AsyncFacebookRunner.AnonymousClass1 */

            public void run() {
                try {
                    String response = AsyncFacebookRunner.this.fb.logout(context);
                    if (response.length() == 0 || response.equals("false")) {
                        listener.onFacebookError(new FacebookError("auth.expireSession failed"));
                    } else {
                        listener.onComplete(response);
                    }
                } catch (FileNotFoundException e) {
                    listener.onFileNotFoundException(e);
                } catch (MalformedURLException e2) {
                    listener.onMalformedURLException(e2);
                } catch (IOException e3) {
                    listener.onIOException(e3);
                }
            }
        }.start();
    }

    public void request(Bundle parameters, RequestListener listener) {
        request(null, parameters, "GET", listener);
    }

    public void request(String graphPath, RequestListener listener) {
        request(graphPath, new Bundle(), "GET", listener);
    }

    public void request(String graphPath, Bundle parameters, RequestListener listener) {
        request(graphPath, parameters, "GET", listener);
    }

    public void request(final String graphPath, final Bundle parameters, final String httpMethod, final RequestListener listener) {
        new Thread() {
            /* class com.facebook.android.AsyncFacebookRunner.AnonymousClass2 */

            public void run() {
                try {
                    listener.onComplete(AsyncFacebookRunner.this.fb.request(graphPath, parameters, httpMethod));
                } catch (FileNotFoundException e) {
                    listener.onFileNotFoundException(e);
                } catch (MalformedURLException e2) {
                    listener.onMalformedURLException(e2);
                } catch (IOException e3) {
                    listener.onIOException(e3);
                }
            }
        }.start();
    }
}
