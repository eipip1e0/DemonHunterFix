package com.bicore;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.bicore.NativeFuntion;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseDialogListener;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionEvents;
import com.facebook.android.SessionStore;
import com.facebook.android.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class FacebookManager implements NativeFuntion.FacebookEventListner {
    private String APP_ID;
    private String APP_KEY;
    private String APP_SECRET;
    private Activity mActivity;
    private AsyncFacebookRunner mAsyncRunner;
    private Facebook mFb = null;
    private Handler mHandler;
    private String[] mPermissions;
    private SessionListener mSessionListener = new SessionListener(this, null);

    public native void nativeLoginResult(boolean z);

    public native void nativeLogoutResult(boolean z);

    public native void nativeQueryResult(int i, int i2, String str);

    public FacebookManager(Activity activity, String[] permissions) {
        this.mActivity = activity;
        this.mPermissions = permissions;
        NativeFuntion.setFacebookEventListener(this);
    }

    @Override // com.bicore.NativeFuntion.FacebookEventListner
    public boolean isVaild() {
        Log.d("Facebook", "*isVaild");
        if (this.mFb == null) {
            return false;
        }
        Log.d("Facebook", "*isVaild return " + this.mFb.isSessionValid());
        return this.mFb.isSessionValid();
    }

    /* access modifiers changed from: package-private */
    public void Post(String publishName, String publishNameLink, String publishCaption, String publishContents, String publishUrl, String publishHref, String publishPrompt, String publishLabel, String publishText, String publishLink, String publishSignText, String publishSignLink, String publishTarget) {
        Bundle parmeters = new Bundle();
        JSONObject json_action_links_object = new JSONObject();
        try {
            json_action_links_object.put("text", publishSignText);
            json_action_links_object.put("href", publishSignLink);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        String action_links = json_action_links_object.toString();
        Log.i("Publish", "action_links tag = " + action_links);
        JSONObject json_attachment_object = new JSONObject();
        try {
            json_attachment_object.put("name", publishName);
            json_attachment_object.put("caption", publishCaption);
            json_attachment_object.put("href", publishNameLink);
            if (publishContents != null) {
                json_attachment_object.put("description", publishContents);
            }
            if (!(publishUrl == null || publishHref == null)) {
                JSONObject json_media_object = new JSONObject();
                JSONArray json_media_array_object = new JSONArray();
                json_media_object.put("type", "image");
                json_media_object.put("src", publishUrl);
                json_media_object.put("href", publishHref);
                json_media_array_object.put(json_media_object);
                json_attachment_object.put("media", json_media_array_object);
            }
            if (!(publishLabel == null || publishText == null || publishLink == null)) {
                JSONObject json_properties_object = new JSONObject();
                JSONObject json_properties_label_object = new JSONObject();
                json_properties_object.put("text", publishText);
                json_properties_object.put("href", publishLink);
                json_properties_label_object.put(publishLabel, json_properties_object);
                json_attachment_object.put("properties", json_properties_label_object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String attachment = json_attachment_object.toString();
        Log.i("Publish", "attachment tag = " + attachment);
        parmeters.putString("api_key", this.mFb.getAppId());
        parmeters.putString("user_message_prompt", publishPrompt);
        parmeters.putString("action_links", action_links);
        parmeters.putString("attachment", attachment);
        this.mFb.dialog(this.mActivity, "stream.publish", parmeters, new SampleDialogListener());
    }

    private final class LoginDialogListener implements Facebook.DialogListener {
        private LoginDialogListener() {
        }

        /* synthetic */ LoginDialogListener(FacebookManager facebookManager, LoginDialogListener loginDialogListener) {
            this();
        }

        @Override // com.facebook.android.Facebook.DialogListener
        public void onComplete(Bundle values) {
            SessionEvents.onLoginSuccess();
            FacebookManager.this.nativeLoginResult(true);
        }

        @Override // com.facebook.android.Facebook.DialogListener
        public void onFacebookError(FacebookError error) {
            SessionEvents.onLoginError(error.getMessage());
            FacebookManager.this.nativeLoginResult(false);
        }

        @Override // com.facebook.android.Facebook.DialogListener
        public void onError(DialogError error) {
            SessionEvents.onLoginError(error.getMessage());
            FacebookManager.this.nativeLoginResult(false);
        }

        @Override // com.facebook.android.Facebook.DialogListener
        public void onCancel() {
            SessionEvents.onLoginError("Action Canceled");
            FacebookManager.this.nativeLoginResult(false);
        }
    }

    private class LogoutRequestListener extends BaseRequestListener {
        private LogoutRequestListener() {
        }

        /* synthetic */ LogoutRequestListener(FacebookManager facebookManager, LogoutRequestListener logoutRequestListener) {
            this();
        }

        @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
        public void onComplete(String response) {
            FacebookManager.this.mHandler.post(new Runnable() {
                /* class com.bicore.FacebookManager.LogoutRequestListener.AnonymousClass1 */

                public void run() {
                    SessionEvents.onLogoutFinish();
                    FacebookManager.this.nativeLogoutResult(true);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public class SessionListener implements SessionEvents.AuthListener, SessionEvents.LogoutListener {
        private SessionListener() {
        }

        /* synthetic */ SessionListener(FacebookManager facebookManager, SessionListener sessionListener) {
            this();
        }

        @Override // com.facebook.android.SessionEvents.AuthListener
        public void onAuthSucceed() {
            SessionStore.save(FacebookManager.this.mFb, FacebookManager.this.mActivity.getApplicationContext());
        }

        @Override // com.facebook.android.SessionEvents.AuthListener
        public void onAuthFail(String error) {
        }

        @Override // com.facebook.android.SessionEvents.LogoutListener
        public void onLogoutBegin() {
        }

        @Override // com.facebook.android.SessionEvents.LogoutListener
        public void onLogoutFinish() {
            SessionStore.clear(FacebookManager.this.mActivity.getApplicationContext());
        }
    }

    private class QueryRequestListener implements AsyncFacebookRunner.RequestListener {
        private QueryRequestListener() {
        }

        /* synthetic */ QueryRequestListener(FacebookManager facebookManager, QueryRequestListener queryRequestListener) {
            this();
        }

        @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
        public void onComplete(String response) {
            String result = new String();
            try {
                JSONArray arrayObj = (JSONArray) new JSONTokener(response).nextValue();
                int resultCount = arrayObj.length();
                int dataCount = 0;
                for (int i = 0; i < resultCount; i++) {
                    JSONObject object = arrayObj.getJSONObject(i);
                    JSONArray dataArrayObj = object.names();
                    dataCount = object.length();
                    for (int j = 0; j < dataCount; j++) {
                        result = String.valueOf(result) + object.getString(dataArrayObj.getString(j));
                        Log.i("QueryResult", result);
                        if (j + 1 < dataCount) {
                            result = String.valueOf(result) + ",";
                        }
                    }
                    if (i + 1 < resultCount) {
                        result = String.valueOf(result) + ",";
                    }
                }
                FacebookManager.this.nativeQueryResult(resultCount, dataCount, result);
            } catch (JSONException e) {
                e.printStackTrace();
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

    public class SampleRequestListener extends BaseRequestListener {
        public SampleRequestListener() {
        }

        @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
        public void onComplete(String response) {
            try {
                Log.d("Facebook-Example", "Response: " + response.toString());
                Util.parseJson(response).getString("name");
            } catch (JSONException e) {
                Log.w("Facebook-Example", "JSON Error in response");
            } catch (FacebookError e2) {
                Log.w("Facebook-Example", "Facebook Error: " + e2.getMessage());
            }
        }
    }

    public class SampleUploadListener extends BaseRequestListener {
        public SampleUploadListener() {
        }

        @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
        public void onComplete(String response) {
            try {
                Log.d("Facebook-Example", "Response: " + response.toString());
                Util.parseJson(response).getString("src");
            } catch (JSONException e) {
                Log.w("Facebook-Example", "JSON Error in response");
            } catch (FacebookError e2) {
                Log.w("Facebook-Example", "Facebook Error: " + e2.getMessage());
            }
        }
    }

    public class WallPostRequestListener extends BaseRequestListener {
        public WallPostRequestListener() {
        }

        @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
        public void onComplete(String response) {
            Log.d("Facebook-Example", "Got response: " + response);
            String message = "<empty>";
            try {
                message = Util.parseJson(response).getString("message");
            } catch (JSONException e) {
                Log.w("Facebook-Example", "JSON Error in response");
            } catch (FacebookError e2) {
                Log.w("Facebook-Example", "Facebook Error: " + e2.getMessage());
            }
            String str = "Your Wall Post: " + message;
        }
    }

    public class WallPostDeleteListener extends BaseRequestListener {
        public WallPostDeleteListener() {
        }

        @Override // com.facebook.android.AsyncFacebookRunner.RequestListener
        public void onComplete(String response) {
            if (response.equals("true")) {
                Log.d("Facebook-Example", "Successfully deleted wall post");
            } else {
                Log.d("Facebook-Example", "Could not delete wall post");
            }
        }
    }

    public class SampleDialogListener extends BaseDialogListener {
        public SampleDialogListener() {
        }

        @Override // com.facebook.android.Facebook.DialogListener
        public void onComplete(Bundle values) {
            String postId = values.getString("post_id");
            if (postId != null) {
                Log.d("Facebook-Example", "Dialog Success! post_id=" + postId);
                FacebookManager.this.mAsyncRunner.request(postId, new WallPostRequestListener());
                return;
            }
            Log.d("Facebook-Example", "No wall post made");
        }
    }

    @Override // com.bicore.NativeFuntion.FacebookEventListner
    public void onFacebookInit(String appId, String appKey, String appSecret) {
        Log.d("Facebook", "*onFacebookInit");
        this.APP_ID = appId;
        this.APP_KEY = appKey;
        this.APP_SECRET = appSecret;
        this.mActivity.runOnUiThread(new Runnable() {
            /* class com.bicore.FacebookManager.AnonymousClass1 */

            public void run() {
                FacebookManager.this.mFb = new Facebook(FacebookManager.this.APP_ID);
                FacebookManager.this.mHandler = new Handler();
                FacebookManager.this.mAsyncRunner = new AsyncFacebookRunner(FacebookManager.this.mFb);
                SessionStore.restore(FacebookManager.this.mFb, FacebookManager.this.mActivity.getApplicationContext());
                SessionEvents.addAuthListener(FacebookManager.this.mSessionListener);
                SessionEvents.addLogoutListener(FacebookManager.this.mSessionListener);
            }
        });
    }

    @Override // com.bicore.NativeFuntion.FacebookEventListner
    public void onLogin() {
        Log.d("Facebook", "*onLogin");
        this.mActivity.runOnUiThread(new Runnable() {
            /* class com.bicore.FacebookManager.AnonymousClass2 */

            public void run() {
                FacebookManager.this.mFb.authorize(FacebookManager.this.mActivity, FacebookManager.this.mPermissions, new LoginDialogListener(FacebookManager.this, null));
            }
        });
    }

    @Override // com.bicore.NativeFuntion.FacebookEventListner
    public void onLogout() {
        Log.d("Facebook", "*onLogout");
        if (this.mFb.isSessionValid()) {
            Log.d("Facebook", "*onLogout start");
            SessionEvents.onLogoutBegin();
            new AsyncFacebookRunner(this.mFb).logout(this.mActivity.getApplicationContext(), new LogoutRequestListener(this, null));
        }
    }

    @Override // com.bicore.NativeFuntion.FacebookEventListner
    public void onPublish(String publishName, String publishNameLink, String publishCaption, String publishContents, String publishUrl, String publishHref, String publishLabel, String publishText, String publishLink, String publishSignText, String publishSignLink, String publishPrompt, String publishTarget) {
        Log.d("Facebook", "*onPublish");
        final Bundle parmeters = new Bundle();
        JSONObject json_action_links_object = new JSONObject();
        try {
            json_action_links_object.put("text", publishSignText);
            json_action_links_object.put("href", publishSignLink);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        String action_links = json_action_links_object.toString();
        Log.i("Publish", "action_links tag = " + action_links);
        JSONObject json_attachment_object = new JSONObject();
        try {
            json_attachment_object.put("name", publishName);
            json_attachment_object.put("caption", publishCaption);
            json_attachment_object.put("href", publishNameLink);
            if (publishContents != null) {
                json_attachment_object.put("description", publishContents);
            }
            if (!(publishUrl == null || publishHref == null)) {
                JSONObject json_media_object = new JSONObject();
                JSONArray json_media_array_object = new JSONArray();
                json_media_object.put("type", "image");
                json_media_object.put("src", publishUrl);
                json_media_object.put("href", publishHref);
                json_media_array_object.put(json_media_object);
                json_attachment_object.put("media", json_media_array_object);
            }
            if (!(publishLabel == null || publishText == null || publishLink == null)) {
                JSONObject json_properties_object = new JSONObject();
                JSONObject json_properties_label_object = new JSONObject();
                json_properties_object.put("text", publishText);
                json_properties_object.put("href", publishLink);
                json_properties_label_object.put(publishLabel, json_properties_object);
                json_attachment_object.put("properties", json_properties_label_object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String attachment = json_attachment_object.toString();
        Log.i("Publish", "attachment tag = " + attachment);
        parmeters.putString("api_key", this.mFb.getAppId());
        parmeters.putString("user_message_prompt", publishPrompt);
        parmeters.putString("action_links", action_links);
        parmeters.putString("attachment", attachment);
        this.mActivity.runOnUiThread(new Runnable() {
            /* class com.bicore.FacebookManager.AnonymousClass3 */

            public void run() {
                FacebookManager.this.mFb.dialog(FacebookManager.this.mActivity, "stream.publish", parmeters, new SampleDialogListener());
            }
        });
    }

    @Override // com.bicore.NativeFuntion.FacebookEventListner
    public void onQuery(String sqlQuery) {
        Log.d("Facebook", "*onQuery");
        final Bundle parameters = new Bundle();
        parameters.putString("method", "fql.query");
        parameters.putString("query", sqlQuery);
        this.mActivity.runOnUiThread(new Runnable() {
            /* class com.bicore.FacebookManager.AnonymousClass4 */

            public void run() {
                new AsyncFacebookRunner(FacebookManager.this.mFb).request(parameters, new QueryRequestListener(FacebookManager.this, null));
            }
        });
    }
}
