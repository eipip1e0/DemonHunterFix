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

public class StreamHandler extends Handler {
    private static final String CACHE_FILE = "cache.txt";

    @Override // com.facebook.stream.Handler
    public void go() {
        this.dispatcher.getWebView().addJavascriptInterface(new StreamJsHandler(this), "app");
        try {
            String cached = FileIO.read(getActivity(), CACHE_FILE);
            if (cached != null) {
                this.dispatcher.loadData(StreamRenderer.render(new JSONObject(cached)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        new AsyncFacebookRunner(Session.restore(getActivity()).getFb()).request("me/home", new StreamRequestListener());
    }

    public class StreamRequestListener implements AsyncFacebookRunner.RequestListener {
        public StreamRequestListener() {
        }

        @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
        public void onComplete(String response) {
            try {
                JSONObject obj = Util.parseJson(response);
                try {
                    FileIO.write(StreamHandler.this.getActivity(), response, StreamHandler.CACHE_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final String html = StreamRenderer.render(obj);
                StreamHandler.this.getActivity().runOnUiThread(new Runnable() {
                    /* class com.facebook.stream.StreamHandler.StreamRequestListener.AnonymousClass1 */

                    public void run() {
                        StreamHandler.this.dispatcher.loadData(html);
                    }
                });
            } catch (JSONException e2) {
                Log.e("stream", "JSON Error:" + e2.getMessage());
            } catch (FacebookError e3) {
                Log.e("stream", "Facebook Error:" + e3.getMessage());
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
}
