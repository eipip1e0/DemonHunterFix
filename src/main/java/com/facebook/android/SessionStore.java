package com.facebook.android;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionStore {
    private static final String EXPIRES = "expires_in";
    private static final String KEY = "facebook-session";
    private static final String TOKEN = "access_token";

    public static boolean save(Facebook session, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, 0).edit();
        editor.putString("access_token", session.getAccessToken());
        editor.putLong("expires_in", session.getAccessExpires());
        return editor.commit();
    }

    public static boolean restore(Facebook session, Context context) {
        SharedPreferences savedSession = context.getSharedPreferences(KEY, 0);
        session.setAccessToken(savedSession.getString("access_token", null));
        session.setAccessExpires(savedSession.getLong("expires_in", 0));
        return session.isSessionValid();
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, 0).edit();
        editor.clear();
        editor.commit();
    }
}
