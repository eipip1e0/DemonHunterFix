package com.facebook.stream;

import android.util.Log;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class LogoutHandler extends Handler {
    @Override // com.facebook.stream.Handler
    public void go() {
        Facebook fb = Session.restore(getActivity()).getFb();
        Session.clearSavedSession(getActivity());
        new AsyncFacebookRunner(fb).logout(getActivity(), new AsyncFacebookRunner.RequestListener() {
            /* class com.facebook.stream.LogoutHandler.AnonymousClass1 */

            @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
            public void onComplete(String response) {
                LogoutHandler.this.dispatcher.runHandler("login");
            }

            @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
            public void onFileNotFoundException(FileNotFoundException error) {
                Log.e("app", error.toString());
                LogoutHandler.this.dispatcher.runHandler("login");
            }

            @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
            public void onIOException(IOException error) {
                Log.e("app", error.toString());
                LogoutHandler.this.dispatcher.runHandler("login");
            }

            @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
            public void onMalformedURLException(MalformedURLException error) {
                Log.e("app", error.toString());
                LogoutHandler.this.dispatcher.runHandler("login");
            }

            @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
            public void onFacebookError(FacebookError error) {
                Log.e("app", error.toString());
                LogoutHandler.this.dispatcher.runHandler("login");
            }
        });
    }
}
