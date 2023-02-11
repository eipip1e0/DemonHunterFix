package com.facebook.stream;

import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.android.Facebook;

public class Session {
    private static final String APP_ID = "app_id";
    private static final String EXPIRES = "expires_in";
    private static final String KEY = "facebook-session";
    private static final String NAME = "name";
    private static final String TOKEN = "access_token";
    private static final String UID = "uid";
    private static Facebook fbLoggingIn;
    private static Session singleton;
    private Facebook fb;
    private String name;
    private String uid;

    public Session(Facebook fb2, String uid2, String name2) {
        this.fb = fb2;
        this.uid = uid2;
        this.name = name2;
    }

    public Facebook getFb() {
        return this.fb;
    }

    public String getUid() {
        return this.uid;
    }

    public String getName() {
        return this.name;
    }

    public boolean save(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, 0).edit();
        editor.putString("access_token", this.fb.getAccessToken());
        editor.putLong("expires_in", this.fb.getAccessExpires());
        editor.putString(UID, this.uid);
        editor.putString(NAME, this.name);
        editor.putString(APP_ID, this.fb.getAppId());
        if (!editor.commit()) {
            return false;
        }
        singleton = this;
        return true;
    }

    public static Session restore(Context context) {
        if (singleton == null) {
            SharedPreferences prefs = context.getSharedPreferences(KEY, 0);
            String appId = prefs.getString(APP_ID, null);
            if (appId == null) {
                return null;
            }
            Facebook fb2 = new Facebook(appId);
            fb2.setAccessToken(prefs.getString("access_token", null));
            fb2.setAccessExpires(prefs.getLong("expires_in", 0));
            String uid2 = prefs.getString(UID, null);
            String name2 = prefs.getString(NAME, null);
            if (!fb2.isSessionValid() || uid2 == null || name2 == null) {
                return null;
            }
            Session session = new Session(fb2, uid2, name2);
            singleton = session;
            return session;
        } else if (singleton.getFb().isSessionValid()) {
            return singleton;
        } else {
            return null;
        }
    }

    public static void clearSavedSession(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, 0).edit();
        editor.clear();
        editor.commit();
        singleton = null;
    }

    public static void waitForAuthCallback(Facebook fb2) {
        fbLoggingIn = fb2;
    }

    public static Facebook wakeupForAuthCallback() {
        Facebook fb2 = fbLoggingIn;
        fbLoggingIn = null;
        return fb2;
    }
}
