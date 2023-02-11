package defpackage;

import android.util.Log;
import com.google.ads.AdRequest;

/* renamed from: t  reason: default package */
public final class t {
    private t() {
    }

    public static void a(String str) {
        if (a(AdRequest.LOGTAG, 3)) {
            Log.d(AdRequest.LOGTAG, str);
        }
    }

    public static void a(String str, Throwable th) {
        if (a(AdRequest.LOGTAG, 6)) {
            Log.e(AdRequest.LOGTAG, str, th);
        }
    }

    private static boolean a(String str, int i) {
        return (i >= 5) || Log.isLoggable(str, i);
    }

    public static void b(String str) {
        if (a(AdRequest.LOGTAG, 6)) {
            Log.e(AdRequest.LOGTAG, str);
        }
    }

    public static void b(String str, Throwable th) {
        if (a(AdRequest.LOGTAG, 5)) {
            Log.w(AdRequest.LOGTAG, str, th);
        }
    }

    public static void c(String str) {
        if (a(AdRequest.LOGTAG, 4)) {
            Log.i(AdRequest.LOGTAG, str);
        }
    }

    public static void d(String str) {
        if (a(AdRequest.LOGTAG, 2)) {
            Log.v(AdRequest.LOGTAG, str);
        }
    }

    public static void e(String str) {
        if (a(AdRequest.LOGTAG, 5)) {
            Log.w(AdRequest.LOGTAG, str);
        }
    }
}
