package com.google.ads;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.webkit.WebView;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;

public class InstallReceiver extends BroadcastReceiver {
    private static String a(String str, String str2, String str3) {
        if (str != null) {
            try {
                String[] split = str.split("&");
                StringBuilder sb = null;
                for (String str4 : split) {
                    if (str4.startsWith("admob_")) {
                        String[] split2 = str4.substring("admob_".length()).split("=");
                        String encode = URLEncoder.encode(split2[0], "UTF-8");
                        String encode2 = URLEncoder.encode(split2[1], "UTF-8");
                        if (sb == null) {
                            sb = new StringBuilder(128);
                        } else {
                            sb.append("&");
                        }
                        sb.append(encode).append("=").append(encode2);
                    }
                }
                if (sb != null) {
                    sb.append("&").append("isu").append("=").append(URLEncoder.encode(str2, "UTF-8"));
                    sb.append("&").append("app_id").append("=").append(URLEncoder.encode(str3, "UTF-8"));
                    return "http://a.admob.com/f0?" + sb.toString();
                }
            } catch (UnsupportedEncodingException e) {
                t.a("Could not create install URL.  Install not tracked.", e);
            }
        }
        return null;
    }

    private static void a(Context context, Intent intent) {
        ActivityInfo receiverInfo;
        Set<String> keySet;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (!(packageManager == null || (receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, InstallReceiver.class), 128)) == null || ((PackageItemInfo) receiverInfo).metaData == null || (keySet = ((PackageItemInfo) receiverInfo).metaData.keySet()) == null)) {
                for (String str : keySet) {
                    try {
                        String string = ((PackageItemInfo) receiverInfo).metaData.getString(str);
                        if (!string.equals("com.google.android.apps.analytics.AnalyticsReceiver")) {
                            ((BroadcastReceiver) Class.forName(string).newInstance()).onReceive(context, intent);
                            t.a("Successfully forwarded install notification to " + string);
                        }
                    } catch (Exception e) {
                        t.b("Could not forward Market's INSTALL_REFERRER intent to " + ((String) null), e);
                    }
                }
            }
            try {
                ((BroadcastReceiver) Class.forName("com.google.android.apps.analytics.AnalyticsReceiver").newInstance()).onReceive(context, intent);
                t.a("Successfully forwarded install notification to com.google.android.apps.analytics.AnalyticsReceiver");
            } catch (ClassNotFoundException e2) {
                t.d("Google Analytics not installed.");
            } catch (Exception e3) {
                t.b("Exception from the Google Analytics install receiver.", e3);
            }
        } catch (Exception e4) {
            t.b("Unhandled exception while forwarding install intents. Possibly lost some install information.", e4);
        }
    }

    public void onReceive(Context context, Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("referrer");
            String a = u.a(context);
            String a2 = a(stringExtra, a, context.getPackageName());
            if (a2 != null) {
                t.d("Processing install from an AdMob ad (" + a2 + ").");
            }
            String userAgentString = new WebView(context).getSettings().getUserAgentString();
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(a2).openConnection();
            httpURLConnection.setRequestProperty("User-Agent", userAgentString);
            httpURLConnection.setRequestProperty("X-Admob-Isu", a);
            httpURLConnection.getResponseCode();
            a(context, intent);
        } catch (Exception e) {
            t.a("Unhandled exception processing Market install.", e);
        }
    }
}
