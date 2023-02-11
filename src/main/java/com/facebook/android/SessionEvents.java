package com.facebook.android;

import java.util.Iterator;
import java.util.LinkedList;

public class SessionEvents {
    private static LinkedList<AuthListener> mAuthListeners = new LinkedList<>();
    private static LinkedList<LogoutListener> mLogoutListeners = new LinkedList<>();

    public interface AuthListener {
        void onAuthFail(String str);

        void onAuthSucceed();
    }

    public interface LogoutListener {
        void onLogoutBegin();

        void onLogoutFinish();
    }

    public static void addAuthListener(AuthListener listener) {
        mAuthListeners.add(listener);
    }

    public static void removeAuthListener(AuthListener listener) {
        mAuthListeners.remove(listener);
    }

    public static void addLogoutListener(LogoutListener listener) {
        mLogoutListeners.add(listener);
    }

    public static void removeLogoutListener(LogoutListener listener) {
        mLogoutListeners.remove(listener);
    }

    public static void onLoginSuccess() {
        Iterator<AuthListener> it = mAuthListeners.iterator();
        while (it.hasNext()) {
            it.next().onAuthSucceed();
        }
    }

    public static void onLoginError(String error) {
        Iterator<AuthListener> it = mAuthListeners.iterator();
        while (it.hasNext()) {
            it.next().onAuthFail(error);
        }
    }

    public static void onLogoutBegin() {
        Iterator<LogoutListener> it = mLogoutListeners.iterator();
        while (it.hasNext()) {
            it.next().onLogoutBegin();
        }
    }

    public static void onLogoutFinish() {
        Iterator<LogoutListener> it = mLogoutListeners.iterator();
        while (it.hasNext()) {
            it.next().onLogoutFinish();
        }
    }
}
