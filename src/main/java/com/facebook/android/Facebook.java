package com.facebook.android;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieSyncManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class Facebook {
    public static final String CANCEL_URI = "fbconnect://cancel";
    private static final int DEFAULT_AUTH_ACTIVITY_CODE = 32665;
    public static final String EXPIRES = "expires_in";
    public static final String FB_APP_SIGNATURE = "30820268308201d102044a9c4610300d06092a864886f70d0101040500307a310b3009060355040613025553310b3009060355040813024341311230100603550407130950616c6f20416c746f31183016060355040a130f46616365626f6f6b204d6f62696c653111300f060355040b130846616365626f6f6b311d301b0603550403131446616365626f6f6b20436f72706f726174696f6e3020170d3039303833313231353231365a180f32303530303932353231353231365a307a310b3009060355040613025553310b3009060355040813024341311230100603550407130950616c6f20416c746f31183016060355040a130f46616365626f6f6b204d6f62696c653111300f060355040b130846616365626f6f6b311d301b0603550403131446616365626f6f6b20436f72706f726174696f6e30819f300d06092a864886f70d010101050003818d0030818902818100c207d51df8eb8c97d93ba0c8c1002c928fab00dc1b42fca5e66e99cc3023ed2d214d822bc59e8e35ddcf5f44c7ae8ade50d7e0c434f500e6c131f4a2834f987fc46406115de2018ebbb0d5a3c261bd97581ccfef76afc7135a6d59e8855ecd7eacc8f8737e794c60a761c536b72b11fac8e603f5da1a2d54aa103b8a13c0dbc10203010001300d06092a864886f70d0101040500038181005ee9be8bcbb250648d3b741290a82a1c9dc2e76a0af2f2228f1d9f9c4007529c446a70175c5a900d5141812866db46be6559e2141616483998211f4a673149fb2232a10d247663b26a9031e15f84bc1c74d141ff98a02d76f85b2c8ab2571b6469b232d8e768a7f7ca04f7abe4a775615916c07940656b58717457b42bd928a2";
    public static final int FORCE_DIALOG_AUTH = -1;
    protected static String GRAPH_BASE_URL = "https://graph.facebook.com/";
    private static final String LOGIN = "login";
    protected static String OAUTH_ENDPOINT = "https://www.facebook.com/dialog/oauth";
    public static final String REDIRECT_URI = "fbconnect://success";
    protected static String RESTSERVER_URL = "https://api.facebook.com/restserver.php";
    public static final String SINGLE_SIGN_ON_DISABLED = "service_disabled";
    public static final String TOKEN = "access_token";
    protected static String UI_SERVER = "https://www.facebook.com/connect/uiserver.php";
    private long mAccessExpires = 0;
    private String mAccessToken = null;
    private String mAppId;
    private Activity mAuthActivity;
    private int mAuthActivityCode;
    private DialogListener mAuthDialogListener;
    private String[] mAuthPermissions;

    public interface DialogListener {
        void onCancel();

        void onComplete(Bundle bundle);

        void onError(DialogError dialogError);

        void onFacebookError(FacebookError facebookError);
    }

    public Facebook(String applicationId) {
        if (applicationId == null) {
            throw new IllegalArgumentException("You must specify your application ID when instantiating a Facebook object. See README for details.");
        }
        this.mAppId = applicationId;
    }

    public void authorize(Activity activity, DialogListener listener) {
        authorize(activity, new String[0], DEFAULT_AUTH_ACTIVITY_CODE, listener);
    }

    public void authorize(Activity activity, String[] permissions, DialogListener listener) {
        authorize(activity, permissions, DEFAULT_AUTH_ACTIVITY_CODE, listener);
    }

    public void authorize(Activity activity, String[] permissions, int activityCode, DialogListener listener) {
        boolean singleSignOnStarted = false;
        this.mAuthDialogListener = listener;
        if (activityCode >= 0) {
            singleSignOnStarted = startSingleSignOn(activity, this.mAppId, permissions, activityCode);
        }
        if (!singleSignOnStarted) {
            startDialogAuth(activity, this.mAppId, permissions);
        }
    }

    private boolean startSingleSignOn(Activity activity, String applicationId, String[] permissions, int activityCode) {
        boolean didSucceed = true;
        Intent intent = new Intent();
        intent.setClassName("com.facebook.katana", "com.facebook.katana.ProxyAuth");
        intent.putExtra("client_id", applicationId);
        if (permissions.length > 0) {
            intent.putExtra("scope", TextUtils.join(",", permissions));
        }
        if (!validateAppSignatureForIntent(activity, intent)) {
            return false;
        }
        this.mAuthActivity = activity;
        this.mAuthPermissions = permissions;
        this.mAuthActivityCode = activityCode;
        try {
            activity.startActivityForResult(intent, activityCode);
        } catch (ActivityNotFoundException e) {
            didSucceed = false;
        }
        return didSucceed;
    }

    private boolean validateAppSignatureForIntent(Activity activity, Intent intent) {
        ResolveInfo resolveInfo = activity.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfo == null) {
            return false;
        }
        try {
            for (Signature signature : activity.getPackageManager().getPackageInfo(resolveInfo.activityInfo.packageName, 64).signatures) {
                if (signature.toCharsString().equals(FB_APP_SIGNATURE)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void startDialogAuth(Activity activity, String applicationId, String[] permissions) {
        Bundle params = new Bundle();
        params.putString("client_id", applicationId);
        if (permissions.length > 0) {
            params.putString("scope", TextUtils.join(",", permissions));
        }
        CookieSyncManager.createInstance(activity);
        dialog(activity, LOGIN, params, new DialogListener() {
            /* class com.facebook.android.Facebook.AnonymousClass1 */

            @Override // com.facebook.android.Facebook.DialogListener
            public void onComplete(Bundle values) {
                CookieSyncManager.getInstance().sync();
                Facebook.this.setAccessToken(values.getString(Facebook.TOKEN));
                Facebook.this.setAccessExpiresIn(values.getString(Facebook.EXPIRES));
                if (Facebook.this.isSessionValid()) {
                    Log.d("Facebook-authorize", "Login Success! access_token=" + Facebook.this.getAccessToken() + " expires=" + Facebook.this.getAccessExpires());
                    Facebook.this.mAuthDialogListener.onComplete(values);
                    return;
                }
                Facebook.this.mAuthDialogListener.onFacebookError(new FacebookError("Failed to receive access token."));
            }

            @Override // com.facebook.android.Facebook.DialogListener
            public void onError(DialogError error) {
                Log.d("Facebook-authorize", "Login failed: " + error);
                Facebook.this.mAuthDialogListener.onError(error);
            }

            @Override // com.facebook.android.Facebook.DialogListener
            public void onFacebookError(FacebookError error) {
                Log.d("Facebook-authorize", "Login failed: " + error);
                Facebook.this.mAuthDialogListener.onFacebookError(error);
            }

            @Override // com.facebook.android.Facebook.DialogListener
            public void onCancel() {
                Log.d("Facebook-authorize", "Login canceled");
                Facebook.this.mAuthDialogListener.onCancel();
            }
        });
    }

    public void authorizeCallback(int requestCode, int resultCode, Intent data) {
        if (requestCode != this.mAuthActivityCode) {
            return;
        }
        if (resultCode == -1) {
            String error = data.getStringExtra("error");
            if (error == null) {
                error = data.getStringExtra("error_type");
            }
            if (error == null) {
                setAccessToken(data.getStringExtra(TOKEN));
                setAccessExpiresIn(data.getStringExtra(EXPIRES));
                if (isSessionValid()) {
                    Log.d("Facebook-authorize", "Login Success! access_token=" + getAccessToken() + " expires=" + getAccessExpires());
                    this.mAuthDialogListener.onComplete(data.getExtras());
                    return;
                }
                this.mAuthDialogListener.onFacebookError(new FacebookError("Failed to receive access token."));
            } else if (error.equals(SINGLE_SIGN_ON_DISABLED) || error.equals("AndroidAuthKillSwitchException")) {
                Log.d("Facebook-authorize", "Hosted auth currently disabled. Retrying dialog auth...");
                startDialogAuth(this.mAuthActivity, this.mAppId, this.mAuthPermissions);
            } else if (error.equals("access_denied") || error.equals("OAuthAccessDeniedException")) {
                Log.d("Facebook-authorize", "Login canceled by user.");
                this.mAuthDialogListener.onCancel();
            } else {
                Log.d("Facebook-authorize", "Login failed: " + error);
                this.mAuthDialogListener.onFacebookError(new FacebookError(error));
            }
        } else if (resultCode != 0) {
        } else {
            if (data != null) {
                Log.d("Facebook-authorize", "Login failed: " + data.getStringExtra("error"));
                this.mAuthDialogListener.onError(new DialogError(data.getStringExtra("error"), data.getIntExtra("error_code", -1), data.getStringExtra("failing_url")));
                return;
            }
            Log.d("Facebook-authorize", "Login canceled by user.");
            this.mAuthDialogListener.onCancel();
        }
    }

    public String logout(Context context) throws MalformedURLException, IOException {
        Util.clearCookies(context);
        Bundle b = new Bundle();
        b.putString("method", "auth.expireSession");
        String response = request(b);
        setAccessToken(null);
        setAccessExpires(0);
        return response;
    }

    public String request(Bundle parameters) throws MalformedURLException, IOException {
        if (parameters.containsKey("method")) {
            return request(null, parameters, "GET");
        }
        throw new IllegalArgumentException("API method must be specified. (parameters must contain key \"method\" and value). See http://developers.facebook.com/docs/reference/rest/");
    }

    public String request(String graphPath) throws MalformedURLException, IOException {
        return request(graphPath, new Bundle(), "GET");
    }

    public String request(String graphPath, Bundle parameters) throws MalformedURLException, IOException {
        return request(graphPath, parameters, "GET");
    }

    public String request(String graphPath, Bundle parameters, String httpMethod) throws FileNotFoundException, MalformedURLException, IOException {
        String url;
        parameters.putString("format", "json");
        if (isSessionValid()) {
            parameters.putString(TOKEN, getAccessToken());
        }
        if (graphPath != null) {
            url = String.valueOf(GRAPH_BASE_URL) + graphPath;
        } else {
            url = RESTSERVER_URL;
        }
        return Util.openUrl(url, httpMethod, parameters);
    }

    public void dialog(Context context, String action, DialogListener listener) {
        dialog(context, action, new Bundle(), listener);
    }

    public void dialog(Context context, String action, Bundle parameters, DialogListener listener) {
        String endpoint;
        if (action.equals(LOGIN)) {
            endpoint = OAUTH_ENDPOINT;
            parameters.putString("type", "user_agent");
            parameters.putString("redirect_uri", REDIRECT_URI);
        } else {
            endpoint = UI_SERVER;
            parameters.putString("method", action);
            parameters.putString("next", REDIRECT_URI);
        }
        parameters.putString("display", "touch");
        if (isSessionValid()) {
            parameters.putString(TOKEN, getAccessToken());
        }
        String url = String.valueOf(endpoint) + "?" + Util.encodeUrl(parameters);
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            Util.showAlert(context, "Error", "Application requires permission to access the Internet");
        } else {
            new FbDialog(context, url, listener).show();
        }
    }

    public boolean isSessionValid() {
        return getAccessToken() != null && (getAccessExpires() == 0 || System.currentTimeMillis() < getAccessExpires());
    }

    public String getAccessToken() {
        return this.mAccessToken;
    }

    public long getAccessExpires() {
        return this.mAccessExpires;
    }

    public void setAccessToken(String token) {
        this.mAccessToken = token;
    }

    public void setAccessExpires(long time) {
        this.mAccessExpires = time;
    }

    public void setAccessExpiresIn(String expiresIn) {
        if (expiresIn != null && !expiresIn.equals("0")) {
            setAccessExpires(System.currentTimeMillis() + ((long) (Integer.parseInt(expiresIn) * 1000)));
        }
    }

    public String getAppId() {
        return this.mAppId;
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
    }
}
