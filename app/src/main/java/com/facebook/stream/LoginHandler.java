package com.facebook.stream;

import android.os.Bundle;
import android.util.Log;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import org.json.JSONObject;

public class LoginHandler extends Handler {
    private static String[] PERMISSIONS = {"offline_access", "read_stream", "publish_stream"};

    @Override // com.facebook.stream.Handler
    public void go() {
        this.dispatcher.getWebView().addJavascriptInterface(new JsHandler(this, null), "app");
        this.dispatcher.loadFile("login.html");
    }

    /* access modifiers changed from: private */
    public class JsHandler {
        private JsHandler() {
        }

        /* synthetic */ JsHandler(LoginHandler loginHandler, JsHandler jsHandler) {
            this();
        }

        public void login() {
            LoginHandler.this.getActivity().runOnUiThread(new Runnable() {
                /* class com.facebook.stream.LoginHandler.JsHandler.AnonymousClass1 */

                public void run() {
                    LoginHandler.this.dispatcher.hideWebView();
                    Facebook fb = new Facebook(App.FB_APP_ID);
                    Session.waitForAuthCallback(fb);
                    fb.authorize(LoginHandler.this.getActivity(), LoginHandler.PERMISSIONS, new AppLoginListener(fb));
                }
            });
        }

        /* access modifiers changed from: private */
        public class AppLoginListener implements Facebook.DialogListener {
            private Facebook fb;

            public AppLoginListener(Facebook fb2) {
                this.fb = fb2;
            }

            @Override // com.facebook.android.Facebook.DialogListener
            public void onCancel() {
                Log.d("app", "login canceled");
            }

            @Override // com.facebook.android.Facebook.DialogListener
            public void onComplete(Bundle values) {
                new AsyncFacebookRunner(this.fb).request("/me", new AsyncRequestListener() {
                    /* class com.facebook.stream.LoginHandler.JsHandler.AppLoginListener.AnonymousClass1 */

                    @Override // com.facebook.stream.AsyncRequestListener
                    public void onComplete(JSONObject obj) {
                        new Session(AppLoginListener.this.fb, obj.optString("id"), obj.optString("name")).save(LoginHandler.this.getActivity());
                        LoginHandler.this.getActivity().runOnUiThread(new Runnable() {
                            /* class com.facebook.stream.LoginHandler.JsHandler.AppLoginListener.AnonymousClass1.AnonymousClass1 */

                            public void run() {
                                LoginHandler.this.dispatcher.showWebView();
                                LoginHandler.this.dispatcher.runHandler("stream");
                            }
                        });
                    }
                });
            }

            @Override // com.facebook.android.Facebook.DialogListener
            public void onError(DialogError e) {
                Log.d("app", "dialog error: " + e);
            }

            @Override // com.facebook.android.Facebook.DialogListener
            public void onFacebookError(FacebookError e) {
                Log.d("app", "facebook error: " + e);
            }
        }
    }
}
